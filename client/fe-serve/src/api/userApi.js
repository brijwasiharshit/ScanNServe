import axiosClient from "./axiosClient";

export const getMenuByTableToken = (tableToken) =>
    axiosClient.get(`/user/menu/${tableToken}`);

export const placeOrder = (tableToken, payload) =>
    axiosClient.post(`/user/order/${tableToken}`, payload);
