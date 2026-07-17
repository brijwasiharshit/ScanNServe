import { Navigate, Outlet } from "react-router-dom";
import { getAccessToken, getRole } from "../utils/tokenStorage";

export default function ProtectedRoute({ allowedRoles }) {
    const token = getAccessToken();
    const role = getRole();

    if (!token) {
        return <Navigate to="/" replace />;
    }

    if (allowedRoles && !allowedRoles.includes(role)) {
        return <Navigate to="/" replace />;
    }

    return <Outlet />;
}
