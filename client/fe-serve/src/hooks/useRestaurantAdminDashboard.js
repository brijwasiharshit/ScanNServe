import { useState, useEffect, useMemo } from "react";
import * as restaurantService from "../services/restaurantService";

export default function useRestaurantAdminDashboard() {
    const [activeTab, setActiveTab] = useState("menu");
    const [searchTerm, setSearchTerm] = useState("");
    const [activeModal, setActiveModal] = useState(null);
    const [menu, setMenu] = useState([]);
    const [tables, setTables] = useState([]);
    const [restaurant, setRestaurant] = useState(null);
    const [loading, setLoading] = useState(true);

    const fetchData = async () => {
        setLoading(true);
        try {
            const [restaurantRes, menuRes, tablesRes] = await Promise.all([
                restaurantService.getRestaurant(),
                restaurantService.getMenu(),
                restaurantService.getTables()
            ]);
            setRestaurant(restaurantRes.data);
            setMenu(menuRes.data || []);
            setTables(tablesRes.data || []);
        } catch (error) {
            console.error("Failed to load restaurant dashboard data:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const filteredMenu = useMemo(
        () =>
            menu.filter((item) =>
                `${item.name} ${item.category}`
                    .toLowerCase()
                    .includes(searchTerm.toLowerCase())
            ),
        [menu, searchTerm]
    );

    const filteredTables = useMemo(
        () =>
            tables.filter((table) =>
                `${table.tableNumber} ${table.tableToken}`
                    .toLowerCase()
                    .includes(searchTerm.toLowerCase())
            ),
        [tables, searchTerm]
    );

    const stats = useMemo(() => [
        { id: "tables", label: "Total Tables", value: tables.length },
        { id: "menu", label: "Menu Items", value: menu.length }
    ], [tables, menu]);

    const [currentEditingItem, setCurrentEditingItem] = useState(null);

    const openModal = (modalName) => setActiveModal(modalName);
    const closeModal = () => {
        setActiveModal(null);
        setCurrentEditingItem(null);
    };

    return {
        activeTab,
        setActiveTab,
        searchTerm,
        setSearchTerm,
        activeModal,
        openModal,
        closeModal,
        stats,
        menu: filteredMenu,
        tables: filteredTables,
        restaurant,
        loading,
        refreshData: fetchData,
        currentEditingItem,
        setCurrentEditingItem
    };
}
