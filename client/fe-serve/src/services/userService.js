import * as userApi from "../api/userApi";

export async function getMenuByTableToken(tableToken) {
    const response = await userApi.getMenuByTableToken(tableToken);
    return response.data;
}

export async function placeOrder(tableToken, items) {
    const response = await userApi.placeOrder(tableToken, items);
    return response.data;
}
