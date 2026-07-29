import AdminList from "../components/super-admin/AdminList";
import DashboardTabs from "../components/super-admin/DashboardTabs";
import FoodList from "../components/super-admin/FoodList";
import QuickActions from "../components/super-admin/QuickActions";
import SearchBar from "../components/super-admin/SearchBar";
import StatsGrid from "../components/super-admin/StatsGrid";
import SuperAdminModals from "../components/super-admin/SuperAdminModals";
import useSuperAdminDashboard from "../hooks/useSuperAdminDashboard";

export default function SuperAdminPage() {
    const dashboard = useSuperAdminDashboard();

    return (
        <div className="min-h-screen bg-[#F8FAFC]">
            <div className="mx-auto max-w-7xl p-4">
                <StatsGrid stats={dashboard.stats} />

                <QuickActions
                    onCreateProperty={() => dashboard.openModal("property")}
                    onAddFood={() => dashboard.openModal("food")}
                    onAddCategory={() => dashboard.openModal("category")}
                    onUploadCsv={dashboard.handleCsvUpload}
                />

                <DashboardTabs
                    activeTab={dashboard.activeTab}
                    onTabChange={dashboard.setActiveTab}
                />

                <SearchBar
                    value={dashboard.searchTerm}
                    onChange={dashboard.setSearchTerm}
                />

                {dashboard.activeTab === "admins" && (
                    <AdminList admins={dashboard.admins} />
                )}

                {dashboard.activeTab === "food" && (
                    <FoodList foods={dashboard.foods} />
                )}
            </div>

            <SuperAdminModals
                activeModal={dashboard.activeModal}
                onClose={dashboard.closeModal}
                onPropertyNext={dashboard.handlePropertyCreated}
                createdRestaurantId={dashboard.createdRestaurantId}
                onAdminCreated={dashboard.handleAdminCreated}
                categories={dashboard.categories}
                onFoodCreated={dashboard.handleFoodCreated}
                onCategoryCreated={dashboard.handleCategoryCreated}
            />
        </div>
    );
}
