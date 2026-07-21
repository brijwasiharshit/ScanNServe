package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.OrderEntity;
import com.app.ScanNServe.domain.entity.OrderItemEntity;
import com.app.ScanNServe.domain.entity.RestaurantEntity;
import com.app.ScanNServe.domain.entity.RestaurantMenuItemEntity;
import com.app.ScanNServe.domain.entity.RestaurantTableEntity;
import com.app.ScanNServe.domain.repository.OrderItemRepository;
import com.app.ScanNServe.domain.repository.OrderRepository;
import com.app.ScanNServe.domain.repository.IRestaurantMenuRepository;
import com.app.ScanNServe.domain.repository.IRestaurantTableRepository;
import com.app.ScanNServe.dto.request.OrderItemRequestDTO;
import com.app.ScanNServe.dto.response.OrderResponseDTO;
import com.app.ScanNServe.exception.ResourceNotFoundException;
import com.app.ScanNServe.service.IOrderService;
import com.app.ScanNServe.transformer.OrderTransformer;
import com.app.ScanNServe.dto.response.DailySalesDTO;
import com.app.ScanNServe.dto.response.HourlySalesDTO;
import com.app.ScanNServe.dto.response.SalesReportResponseDTO;
import com.app.ScanNServe.utils.jwt.UserPrincipal;
import com.app.ScanNServe.utils.security.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final IRestaurantTableRepository tableRepository;
    private final IRestaurantMenuRepository menuRepository;
    private final SecurityContextUtil securityContextUtil;

    @Override
    @Transactional
    public OrderResponseDTO placeOrder(String tableToken, List<OrderItemRequestDTO> items) {
        RestaurantTableEntity table = tableRepository.findByTableTokenAndIsDeletedFalse(tableToken)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid or missing table token"));

        RestaurantEntity restaurant = table.getRestaurant();

        OrderEntity order = OrderEntity.builder()
                .restaurant(restaurant)
                .table(table)
                .totalAmount(BigDecimal.ZERO)
                .build();

        // Save order first to get ID for items
        order = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItemEntity> orderItems = new ArrayList<>();

        for (var itemRequest : items) {
            RestaurantMenuItemEntity menuItem = menuRepository.findById(itemRequest.getRestaurantMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found: " + itemRequest.getRestaurantMenuItemId()));

            if (!menuItem.getRestaurant().getRestaurantId().equals(restaurant.getRestaurantId())) {
                throw new IllegalArgumentException("Menu item does not belong to this restaurant");
            }

            if (!menuItem.getAvailable() || menuItem.getIsDeleted()) {
                throw new IllegalArgumentException("Menu item is currently unavailable: " + menuItem.getFoodItem().getName());
            }

            BigDecimal itemTotal = menuItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            OrderItemEntity orderItem = OrderItemEntity.builder()
                    .order(order)
                    .menuItem(menuItem)
                    .quantity(itemRequest.getQuantity())
                    .price(menuItem.getPrice())
                    .build();

            orderItems.add(orderItem);
        }

        orderItemRepository.saveAll(orderItems);

        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);
        orderRepository.save(order);

        return OrderTransformer.toOrderResponseDTO(order);
    }

    @Override
    public SalesReportResponseDTO getSalesReport() {
        UserPrincipal activeUser = securityContextUtil.fetchActiveUserDetails();
        Long restaurantId = activeUser.getRestaurantId();

        ZoneId istZone = ZoneId.of("Asia/Kolkata");
        LocalDateTime now = LocalDateTime.now(istZone);
        
        // 1. All Time
        BigDecimal totalSalesAllTime = orderRepository.getTotalSalesAllTime(restaurantId);
        if (totalSalesAllTime == null) {
            totalSalesAllTime = BigDecimal.ZERO;
        }

        // 2. This Month
        LocalDateTime startOfMonth = now.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);
        LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);
        
        Long ordersThisMonth = orderRepository.countOrdersBetween(restaurantId, startOfMonth, endOfMonth);
        BigDecimal totalSalesThisMonth = orderRepository.sumSalesBetween(restaurantId, startOfMonth, endOfMonth);
        if (totalSalesThisMonth == null) {
            totalSalesThisMonth = BigDecimal.ZERO;
        }

        // 3. This Week (Assuming week starts on Monday)
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDateTime startOfWeek = now.with(weekFields.dayOfWeek(), 1).truncatedTo(ChronoUnit.DAYS);
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);
        
        Long ordersThisWeek = orderRepository.countOrdersBetween(restaurantId, startOfWeek, endOfWeek);

        // 4. Last 7 Days for Charts
        LocalDateTime startOfLast7Days = now.minusDays(6).truncatedTo(ChronoUnit.DAYS);
        List<OrderEntity> recentOrders = orderRepository.findOrdersBetween(restaurantId, startOfLast7Days, now);

        List<DailySalesDTO> salesLast7Days = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = now.minusDays(i).toLocalDate();
            
            List<OrderEntity> ordersOnDate = recentOrders.stream()
                    .filter(o -> o.getCreatedAt().toLocalDate().equals(date))
                    .collect(Collectors.toList());
                    
            Long count = (long) ordersOnDate.size();
            BigDecimal sum = ordersOnDate.stream()
                    .map(OrderEntity::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                    
            salesLast7Days.add(new DailySalesDTO(date, count, sum));
        }

        // 5. Peak Hours
        List<HourlySalesDTO> peakSalesHours = new ArrayList<>();
        Map<Integer, List<OrderEntity>> ordersByHour = recentOrders.stream()
                .collect(Collectors.groupingBy(o -> o.getCreatedAt().getHour()));
                
        for (int hour = 0; hour < 24; hour++) {
            List<OrderEntity> ordersInHour = ordersByHour.getOrDefault(hour, new ArrayList<>());
            Long count = (long) ordersInHour.size();
            BigDecimal sum = ordersInHour.stream()
                    .map(OrderEntity::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            peakSalesHours.add(new HourlySalesDTO(hour, count, sum));
        }

        return SalesReportResponseDTO.builder()
                .ordersThisMonth(ordersThisMonth)
                .totalSalesAllTime(totalSalesAllTime)
                .totalSalesThisMonth(totalSalesThisMonth)
                .ordersThisWeek(ordersThisWeek)
                .salesLast7Days(salesLast7Days)
                .peakSalesHours(peakSalesHours)
                .build();
    }
}
