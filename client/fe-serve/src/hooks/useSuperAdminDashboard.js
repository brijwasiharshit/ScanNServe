import { useMemo, useState, useEffect } from "react";
import * as superAdminApi from "../api/superAdminApi";
import { dashboardStats } from "../data/superAdminDashboard"; // Keep stats mock for now if no API

export default function useSuperAdminDashboard() {
    const [activeTab, setActiveTab] = useState("admins");
    const [searchTerm, setSearchTerm] = useState("");
    const [activeModal, setActiveModal] = useState(null);
    
    // Real State
    const [adminList, setAdminList] = useState([]);
    const [foodList, setFoodList] = useState([]);
    const [categories, setCategories] = useState([]);
    const [createdRestaurantId, setCreatedRestaurantId] = useState(null);

    // Initial Fetch
    useEffect(() => {
        const fetchGlobalData = async () => {
            try {
                const foodRes = await superAdminApi.getGlobalFoodItems();
                if (foodRes.data?.data) {
                    setFoodList(foodRes.data.data);
                }

                const catRes = await superAdminApi.getFoodCategories();
                if (catRes.data?.data) {
                    setCategories(catRes.data.data);
                }
            } catch (err) {
                console.error("Failed to fetch super admin data", err);
            }
        };

        fetchGlobalData();
    }, []);

    const admins = useMemo(() => {
        // Fallback to empty array if no real admin list fetching API is available yet
        return adminList.filter((admin) =>
            `${admin.username} ${admin.emailAddress}`
                .toLowerCase()
                .includes(searchTerm.toLowerCase())
        );
    }, [searchTerm, adminList]);

    const foods = useMemo(() => {
        return foodList.filter((food) =>
            `${food.name} ${food.categoryName}`
                .toLowerCase()
                .includes(searchTerm.toLowerCase())
        );
    }, [searchTerm, foodList]);

    const openModal = (modalName) => {
        setActiveModal(modalName);
        if (modalName === "property") {
            setCreatedRestaurantId(null); // Reset on new property flow
        }
    };
    
    const closeModal = () => {
        setActiveModal(null);
        setCreatedRestaurantId(null);
    };

    const handlePropertyCreated = (restaurantId) => {
        setCreatedRestaurantId(restaurantId);
        setActiveModal("admin");
    };

    const handleFoodCreated = (newFood) => {
        setFoodList((prev) => [...prev, newFood]);
        closeModal();
    };

    const handleAdminCreated = (newAdmin) => {
        setAdminList((prev) => [...prev, newAdmin]);
        closeModal();
    };

    return {
        activeTab,
        setActiveTab,
        searchTerm,
        setSearchTerm,
        activeModal,
        openModal,
        closeModal,
        stats: dashboardStats,
        admins,
        foods,
        categories,
        createdRestaurantId,
        handlePropertyCreated,
        handleFoodCreated,
        handleAdminCreated
    };
}
