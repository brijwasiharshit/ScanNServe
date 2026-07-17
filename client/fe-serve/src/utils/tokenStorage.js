export const saveAuth = ({ accessToken, role, userId }) => {

    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("role", role);
    localStorage.setItem("userId", userId);
};

export const clearAuth = () => {

    localStorage.removeItem("accessToken");
    localStorage.removeItem("role");
    localStorage.removeItem("userId");
};

export const getAccessToken = () =>
    localStorage.getItem("accessToken");

export const getRole = () =>
    localStorage.getItem("role");

export const getUserId = () =>
    localStorage.getItem("userId");