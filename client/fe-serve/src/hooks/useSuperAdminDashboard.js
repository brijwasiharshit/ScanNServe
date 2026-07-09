import { useMemo, useState } from "react";
import { adminList, dashboardStats, foodList } from "../data/superAdminDashboard";

export default function useSuperAdminDashboard() {
    const [activeTab, setActiveTab] = useState("admins");
    const [searchTerm, setSearchTerm] = useState("");
    const [activeModal, setActiveModal] = useState(null);

    const admins = useMemo(
        () =>
            adminList.filter((admin) =>
                `${admin.property} ${admin.email} ${admin.status}`
                    .toLowerCase()
                    .includes(searchTerm.toLowerCase())
            ),
        [searchTerm]
    );

    const foods = useMemo(
        () =>
            foodList.filter((food) =>
                `${food.name} ${food.category}`
                    .toLowerCase()
                    .includes(searchTerm.toLowerCase())
            ),
        [searchTerm]
    );

    const openModal = (modalName) => setActiveModal(modalName);
    const closeModal = () => setActiveModal(null);
    const continueToAdmin = () => setActiveModal("admin");

    return {
        activeTab,
        setActiveTab,
        searchTerm,
        setSearchTerm,
        activeModal,
        openModal,
        closeModal,
        continueToAdmin,
        stats: dashboardStats,
        admins,
        foods,
    };
}
