import * as userApi from "../api/userApi";

export async function getMenuByTableToken(tableToken) {
    const response = await userApi.getMenuByTableToken(tableToken);
    return response.data;
}
