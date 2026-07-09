import * as authApi from "../api/authApi";
import { saveAuth, clearAuth } from "../utils/tokenStorage";

export async function login(data) {

    const response = await authApi.login(data);

    saveAuth(response.data);

    return response.data;
}

export async function logout() {

    await authApi.logout();

    clearAuth();
}