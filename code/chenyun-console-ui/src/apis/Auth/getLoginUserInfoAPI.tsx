import {http} from "../../utils";

export function getLoginUserInfoAPI(token: string|null) {
    return http.post(
        `/user/auth/getUserInfoByToken`,
        {
            token: token
        }
    )
}
