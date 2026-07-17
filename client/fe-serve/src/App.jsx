import { Routes, Route } from "react-router-dom";

import LoginPage from "./pages/LoginPage";
import SuperAdminPage from "./pages/SuperAdminPage";
import RestaurantAdminPage from "./pages/RestaurantAdminPage";
import CustomerMenuPage from "./pages/CustomerMenuPage";
import MainLayout from "./layouts/MainLayout";
import ProtectedRoute from "./components/ProtectedRoute";

function App() {
    return (
        <Routes>
            {/* Public route for customer menu */}
            <Route path="/menu/:tableToken" element={<CustomerMenuPage />} />

            <Route element={<MainLayout />}>
                <Route path="/" element={<LoginPage />} />
                
                <Route element={<ProtectedRoute allowedRoles={["SUPER_ADMIN"]} />}>
                    <Route path="/super-admin" element={<SuperAdminPage />} />
                </Route>

                <Route element={<ProtectedRoute allowedRoles={["ADMIN", "RESTAURANT_ADMIN"]} />}>
                    <Route path="/admin" element={<RestaurantAdminPage />} />
                </Route>
            </Route>
        </Routes>
    );
}

export default App;