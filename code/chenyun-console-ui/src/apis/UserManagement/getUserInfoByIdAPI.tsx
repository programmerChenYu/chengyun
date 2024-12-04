import {http} from "../../utils";

export function getUserInfoByIdAPI(userId: string | null) {
    return http.post(
        `/user/userProfile/getUserInfoById`,
        {
            key: userId
        }
    );
}
