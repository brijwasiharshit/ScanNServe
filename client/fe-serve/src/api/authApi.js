import axiosClient from "./axiosClient";

export const login = (payload) =>
    axiosClient.post("/super/authenticate", payload);

export const refreshToken = () =>
    axiosClient.post("/super/refresh-token");

export const logout = () =>
    axiosClient.post("/super/logout");