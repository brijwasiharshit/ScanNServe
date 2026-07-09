import { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as authService from "../services/authService";

const initialLoginForm = {
    userName: "",
    password: "",
    rememberMe: false,
};

export default function useLogin() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState(initialLoginForm);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleChange = (event) => {
        const { name, value, type, checked } = event.target;

        setFormData((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    const handleLogin = async (event) => {
        event?.preventDefault();

        try {
            setLoading(true);
            setError("");

            const { role } = await authService.login(formData);

            if (role === "SUPER_ADMIN") {
                navigate("/super-admin");
                return;
            }

            if (role === "ADMIN") {
                navigate("/admin");
                return;
            }

            setError("Unauthorized role");
        } catch (err) {
            setError(err.response?.data?.message || "Invalid username or password");
        } finally {
            setLoading(false);
        }
    };

    return {
        formData,
        loading,
        error,
        handleChange,
        handleLogin,
    };
}
