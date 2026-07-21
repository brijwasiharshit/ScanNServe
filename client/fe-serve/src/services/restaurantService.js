import * as restaurantApi from "../api/restaurantApi";

export async function getRestaurant() {
    const response = await restaurantApi.getRestaurant();
    return response.data;
}

export async function searchItems(keyword) {
    const response = await restaurantApi.searchItems(keyword);
    return response.data;
}

export async function getMenu() {
    const response = await restaurantApi.getMenu();
    return response.data;
}

export async function subscribeItem(payload) {
    const response = await restaurantApi.subscribeItem(payload);
    return response.data;
}

export async function updateMenuItem(itemId, payload) {
    const response = await restaurantApi.updateMenuItem(itemId, payload);
    return response.data;
}

export async function removeMenuItem(itemId) {
    const response = await restaurantApi.removeMenuItem(itemId);
    return response.data;
}

export async function getTables() {
    const response = await restaurantApi.getTables();
    return response.data;
}

export async function createTable(payload) {
    const response = await restaurantApi.createTable(payload);
    return response.data;
}

export async function getSalesReport() {
    const response = await restaurantApi.getSalesReport();
    return response.data;
}
