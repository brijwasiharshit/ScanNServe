import { useState, useEffect, useMemo } from "react";
import * as userService from "../services/userService";

export default function useCustomerMenu(tableToken) {
    const [menuData, setMenuData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!tableToken) return;

        const fetchMenu = async () => {
            setLoading(true);
            try {
                const response = await userService.getMenuByTableToken(tableToken);
                setMenuData(response.data);
                setError(null);
            } catch (err) {
                console.error("Failed to fetch menu:", err);
                setError("Failed to load menu. Please try again or check the QR code.");
            } finally {
                setLoading(false);
            }
        };

        fetchMenu();
    }, [tableToken]);

    const categories = useMemo(() => {
        if (!menuData || !menuData.menu) return [];
        const cats = [...new Set(menuData.menu.map(item => item.categoryName))];
        return cats;
    }, [menuData]);

    return {
        menuData,
        categories,
        loading,
        error
    };
}
