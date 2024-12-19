import {http} from "../../utils";

export function logoutAPI() {
    return http.post(
        `/user/auth/logout`
    )
}
