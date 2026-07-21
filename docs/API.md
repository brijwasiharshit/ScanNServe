# API Documentation

This document outlines the exposed RESTful API endpoints for ScanNServe. The API is divided into logical controllers handling Authentication, Food Management, Super Admin operations, and Restaurant Admin operations.

All API responses are wrapped in a `StandardResponse` standard format:
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... }
}
```

---

## 1. Authentication API

### Generate Token
- **Method**: `POST`
- **Route**: `/api/v1/auth/token`
- **Purpose**: Authenticate a user and generate JWT tokens.
- **Request Body**: `AuthRequest` (username, password)
- **Success Response**: `AuthResponseDTO` (Access Token, Refresh Token, User Details)

### Refresh Token
- **Method**: `POST`
- **Route**: `/api/v1/auth/refresh`
- **Purpose**: Obtain a new access token using a valid refresh token.
- **Headers/Cookies**: Expects `refreshToken` as an HTTP-only Cookie.
- **Success Response**: `AuthResponseDTO`

### Logout
- **Method**: `POST`
- **Route**: `/api/v1/auth/logout`
- **Purpose**: Invalidate the current session and clear cookies.
- **Headers/Cookies**: Expects `refreshToken` cookie.
- **Success Response**: `200 OK`

---

## 2. Super Admin API

### Create Restaurant
- **Method**: `POST`
- **Route**: `/api/v1/super-admin/restaurants`
- **Purpose**: Register a new restaurant tenant.
- **Authentication**: Requires `ROLE_SUPERADMIN`
- **Request Body**: `RestaurantRequestDTO` (name, address, phoneNumber, themeColor)
- **Success Response**: `RestaurantResponseDTO`

### Create Admin
- **Method**: `POST`
- **Route**: `/api/v1/super-admin/admins`
- **Purpose**: Create a Restaurant Admin for a specific restaurant.
- **Authentication**: Requires `ROLE_SUPERADMIN`
- **Request Body**: `AdminRequestDTO` (username, emailAddress, password, restaurantId)
- **Success Response**: `AdminResponseDTO`

---

## 3. Food Item API

### Create Category
- **Method**: `POST`
- **Route**: `/api/v1/food/categories`
- **Purpose**: Add a new global food category.
- **Request Body**: `FoodCategoryRequestDTO` (name)
- **Success Response**: `FoodCategoryResponseDTO`

### Get All Categories
- **Method**: `GET`
- **Route**: `/api/v1/food/categories`
- **Purpose**: Fetch all active food categories.

### Create Food Item
- **Method**: `POST`
- **Route**: `/api/v1/food/items`
- **Purpose**: Add a new master food item.
- **Request Body**: `FoodItemRequestDTO` (categoryId, name, foodType, defaultImage)
- **Success Response**: `FoodItemResponseDTO`

---

## 4. Restaurant Admin API

### Get Restaurant Details
- **Method**: `GET`
- **Route**: `/api/v1/restaurant-admin/restaurant`
- **Purpose**: Get details of the logged-in admin's restaurant.
- **Authentication**: Requires `ROLE_RESTAURANT_ADMIN`

### Search Master Items
- **Method**: `GET`
- **Route**: `/api/v1/restaurant-admin/items/search?keyword={keyword}`
- **Purpose**: Search for master food items to add to the restaurant menu.

### Subscribe Item (Add to Menu)
- **Method**: `POST`
- **Route**: `/api/v1/restaurant-admin/menu`
- **Purpose**: Add a master food item to the restaurant's menu with a specific price.
- **Request Body**: `RestaurantMenuItemRequestDTO` (itemId, price, customImage, available)
- **Success Response**: `RestaurantMenuItemResponseDTO`

### Create Table
- **Method**: `POST`
- **Route**: `/api/v1/restaurant-admin/tables`
- **Purpose**: Generate a new QR table for the restaurant.
- **Request Body**: `RestaurantTableRequestDTO` (tableNumber)
- **Success Response**: `RestaurantTableResponseDTO` (Includes generated tableToken)
