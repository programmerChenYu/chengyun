import {http} from "../../utils";

export function numberOfUsersAPI() {
    return http.get(`/user/userProfile/numberOfUsers`);
}
