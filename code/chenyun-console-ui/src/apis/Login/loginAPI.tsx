import {http} from "../../utils";

export function loginAPI(username:string, password:string) {
    return http.post(
        `/user/auth/login`,
        {
            username: username,
            password: password,
            clientType: 0,
        }
    )
}
