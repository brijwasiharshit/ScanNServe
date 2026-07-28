import useRestaurantAdminDashboard from "../hooks/useRestaurantAdminDashboard";
import RestaurantStats from "../components/restaurant-admin/RestaurantStats";
import RestaurantQuickActions from "../components/restaurant-admin/RestaurantQuickActions";
import RestaurantDashboardTabs from "../components/restaurant-admin/RestaurantDashboardTabs";
import SearchBar from "../components/super-admin/SearchBar";
import MenuList from "../components/restaurant-admin/MenuList";
import TableList from "../components/restaurant-admin/TableList";
import RestaurantModals from "../components/restaurant-admin/RestaurantModals";
import SalesAnalysisModal from "../components/restaurant-admin/SalesAnalysisModal";
import * as restaurantService from "../services/restaurantService";

export default function RestaurantAdminPage() {
    const dashboard = useRestaurantAdminDashboard();

    const handleTableSubmit = async (data) => {
        try {
            await restaurantService.createTable(data);
            dashboard.closeModal();
            dashboard.refreshData();
        } catch (err) {
            console.error("Error creating table", err);
            alert("Failed to create table");
        }
    };

    const handleMenuItemSubmit = async (data) => {
        try {
            if (dashboard.activeModal === "menuItem" && dashboard.currentEditingItem) {
                // Update existing
                await restaurantService.updateMenuItem(dashboard.currentEditingItem.itemId, data);
            } else {
                // Subscribe new
                await restaurantService.subscribeItem({ itemId: data.itemId, price: data.price, customImage: data.customImage, available: data.available, tag: data.tag });
            }
            dashboard.closeModal();
            dashboard.refreshData();
        } catch (err) {
            console.error("Error saving menu item", err);
            alert("Failed to save menu item");
        }
    };

    const handleEditItem = (item) => {
        dashboard.setCurrentEditingItem(item);
        dashboard.openModal("menuItem");
    };

    const handleRemoveItem = async (itemId) => {
        if (!window.confirm("Are you sure you want to remove this item?")) return;
        try {
            await restaurantService.removeMenuItem(itemId);
            dashboard.refreshData();
        } catch (err) {
            console.error("Error removing item", err);
            alert("Failed to remove menu item");
        }
    };

    const handleAddMenuItem = () => {
        dashboard.setCurrentEditingItem(null);
        dashboard.openModal("menuItem");
    };

    if (dashboard.loading) {
        return <div className="min-h-screen bg-[#F8FAFC] flex justify-center items-center">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-[#2563EB]"></div>
        </div>;
    }

    return (
        <div className="min-h-screen bg-[#F8FAFC]">
            <div className="mx-auto max-w-7xl p-4">
                <div className="mb-6">
                    <h1 className="text-2xl font-bold text-slate-800">
                        {dashboard.restaurant?.name || "Restaurant Dashboard"}
                    </h1>
                </div>

                <RestaurantStats stats={dashboard.stats} />

                <RestaurantQuickActions
                    onAddTable={() => dashboard.openModal("table")}
                    onAddMenuItem={handleAddMenuItem}
                    onViewSalesAnalysis={() => dashboard.openModal("salesAnalysis")}
                />

                <RestaurantDashboardTabs
                    activeTab={dashboard.activeTab}
                    onTabChange={dashboard.setActiveTab}
                />

                <SearchBar
                    value={dashboard.searchTerm}
                    onChange={dashboard.setSearchTerm}
                />

                {dashboard.activeTab === "menu" && (
                    <MenuList
                        menu={dashboard.menu}
                        onEdit={handleEditItem}
                        onRemove={handleRemoveItem}
                    />
                )}

                {dashboard.activeTab === "tables" && (
                    <TableList tables={dashboard.tables} />
                )}
            </div>

            <RestaurantModals
                activeModal={dashboard.activeModal}
                onClose={dashboard.closeModal}
                onTableSubmit={handleTableSubmit}
                onMenuItemSubmit={handleMenuItemSubmit}
                initialMenuData={dashboard.currentEditingItem}
                existingMenu={dashboard.menu}
            />

            {dashboard.activeModal === "salesAnalysis" && (
                <SalesAnalysisModal onClose={dashboard.closeModal} />
            )}
        </div>
    );
}
