import axiosClient from "./axiosClient";

export const getRestaurant = () =>
    axiosClient.get("/admin/restaurant");

export const searchItems = (keyword) =>
    axiosClient.get(`/admin/items/search?keyword=${encodeURIComponent(keyword)}`);

export const getMenu = () =>
    axiosClient.get("/admin/menu/subscribe/items");

export const subscribeItem = (payload) =>
    axiosClient.post("/admin/menu/subscribe/items", payload);

export const updateMenuItem = (itemId, payload) =>
    axiosClient.patch(`/admin/menu/subscribe/items/${itemId}`, payload);

export const removeMenuItem = (itemId) =>
    axiosClient.delete(`/admin/menu/subscribe/items/${itemId}`);

export const getTables = () =>
    axiosClient.get("/admin/tables");

export const createTable = (payload) =>
    axiosClient.post("/admin/tables", payload);

export const getSalesReport = () =>
    axiosClient.get("/admin/sales-report");
