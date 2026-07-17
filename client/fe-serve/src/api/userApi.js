import axiosClient from "./axiosClient";

export const getMenuByTableToken = (tableToken) =>
    axiosClient.get(`/user/menu/${tableToken}`);
