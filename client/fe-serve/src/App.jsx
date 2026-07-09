import { Routes, Route } from "react-router-dom";

import LoginPage from "./pages/LoginPage";
import SuperAdminPage from "./pages/SuperAdminPage";
import MainLayout from "./layouts/MainLayout";

function App() {
    return (
        <Routes>
            <Route element={<MainLayout />}>
                <Route path="/" element={<LoginPage />} />
                <Route path="/super-admin" element={<SuperAdminPage />} />
            </Route>
        </Routes>
    );
}

export default App;