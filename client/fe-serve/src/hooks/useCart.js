import { useState, useEffect } from "react";

export default function useCart(tableToken) {
    const [cart, setCart] = useState(() => {
        if (!tableToken) return [];
        const saved = localStorage.getItem(`cart_${tableToken}`);
        if (saved) {
            try {
                return JSON.parse(saved);
            } catch (e) {
                return [];
            }
        }
        return [];
    });

    useEffect(() => {
        if (tableToken) {
            localStorage.setItem(`cart_${tableToken}`, JSON.stringify(cart));
        }
    }, [cart, tableToken]);

    const addToCart = (item) => {
        setCart((prev) => {
            const existing = prev.find((i) => i.itemId === item.itemId);
            if (existing) {
                return prev.map((i) =>
                    i.itemId === item.itemId ? { ...i, quantity: i.quantity + 1 } : i
                );
            }
            return [...prev, { 
                itemId: item.itemId, 
                name: item.name || item.itemName, 
                price: item.price, 
                foodType: item.foodType,
                image: item.image,
                quantity: 1 
            }];
        });
    };

    const removeFromCart = (itemId) => {
        setCart((prev) => prev.filter((i) => i.itemId !== itemId));
    };

    const updateQuantity = (itemId, delta) => {
        setCart((prev) => {
            return prev.map((i) => {
                if (i.itemId === itemId) {
                    const newQty = i.quantity + delta;
                    return { ...i, quantity: newQty };
                }
                return i;
            }).filter((i) => i.quantity > 0);
        });
    };

    const clearCart = () => setCart([]);

    const getCartTotal = () => {
        return cart.reduce((total, item) => total + (item.price * item.quantity), 0);
    };

    const getItemQuantity = (itemId) => {
        const item = cart.find((i) => i.itemId === itemId);
        return item ? item.quantity : 0;
    };

    return {
        cart,
        addToCart,
        removeFromCart,
        updateQuantity,
        clearCart,
        getCartTotal,
        getItemQuantity
    };
}
