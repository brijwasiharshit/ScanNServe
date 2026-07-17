import axiosClient from "./axiosClient";

export const createRestaurant = (restaurantData) => {
    return axiosClient.post("/super/restaurants", restaurantData);
};

export const createAdmin = (adminData) => {
    return axiosClient.post("/super/admins", adminData);
};

export const getGlobalFoodItems = () => {
    return axiosClient.get("/items");
};

export const createGlobalFoodItem = (foodData) => {
    return axiosClient.post("/items", foodData);
};

export const getFoodCategories = () => {
    return axiosClient.get("/categories");
};

// Also we need to get restaurants/admins to list them, but we'll use existing ones if they exist.
// Assuming backend doesn't have a get admins route for super admin yet, we'll keep the UI mocked or wait.
