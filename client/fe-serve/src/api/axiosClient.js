import axios from "axios";
import { getAccessToken } from "../utils/tokenStorage";

//returns an axios instance with methods such as get(),post(),put(),delete()
const axiosClient = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || "https://scannserve-3eqs.onrender.com/api/v1",
    withCredentials: true
});

axiosClient.interceptors.request.use((config) => {
    const token = getAccessToken();

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

export default axiosClient;
