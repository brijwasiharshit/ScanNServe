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

export const uploadGlobalFoodItemsCsv = (file) => {
    const formData = new FormData();
    formData.append("file", file);
    return axiosClient.post("/items/bulk", formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
};

export const getFoodCategories = () => {
    return axiosClient.get("/categories");
};

export const createCategory = (categoryData) => {
    return axiosClient.post("/categories", categoryData);
};

export const getRestaurants = () => {
    return axiosClient.get("/super/restaurants");
};

export const getAdmins = () => {
    return axiosClient.get("/super/admins");
};
