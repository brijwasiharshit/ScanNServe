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

                const adminRes = await superAdminApi.getAdmins();
                if (adminRes.data?.data) {
                    setAdminList(adminRes.data.data);
                }
            } catch (err) {
                console.error("Failed to fetch super admin data", err);
            }
        };

        fetchGlobalData();
    }, []);

    const admins = useMemo(() => {
        return adminList.map(admin => ({
            id: admin.userId,
            property: admin.restaurantName || "No Property",
            email: admin.emailAddress,
            status: "Active"
        })).filter((admin) =>
            `${admin.property} ${admin.email}`
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

    const handleFoodCreated = async () => {
        const res = await superAdminApi.getGlobalFoodItems();
        if (res.data?.data) {
            setFoodList(res.data.data);
        }
        closeModal();
    };

    const handleCategoryCreated = async () => {
        const catRes = await superAdminApi.getFoodCategories();
        if (catRes.data?.data) {
            setCategories(catRes.data.data);
        }
        closeModal();
    };

    const handleCsvUpload = async (file) => {
        try {
            await superAdminApi.uploadGlobalFoodItemsCsv(file);
            alert("CSV uploaded successfully!");
            handleFoodCreated(); // refresh list
        } catch (error) {
            alert(error.response?.data?.message || "Failed to upload CSV. Please ensure categories exist and data is valid.");
        }
    };

    const handleAdminCreated = (newAdmin) => {
        setAdminList((prev) => [...prev, newAdmin]);
        closeModal();
    };

    const dynamicStats = [
        {
            id: "admins",
            label: "Total Admins",
            value: adminList.length,
        },
        {
            id: "properties",
            label: "Properties",
            value: adminList.filter((v,i,a)=>a.findIndex(t=>(t.restaurantId === v.restaurantId))===i).length || 0, // Approx properties from admins if we don't fetch all properties
        },
        {
            id: "food",
            label: "Food Items",
            value: foodList.length,
        },
        {
            id: "categories",
            label: "Total Categories",
            value: categories.length,
        }
    ];

    return {
        activeTab,
        setActiveTab,
        searchTerm,
        setSearchTerm,
        activeModal,
        openModal,
        closeModal,
        stats: dynamicStats,
        admins,
        foods,
        categories,
        createdRestaurantId,
        handlePropertyCreated,
        handleFoodCreated,
        handleAdminCreated,
        handleCategoryCreated,
        handleCsvUpload
    };
}
