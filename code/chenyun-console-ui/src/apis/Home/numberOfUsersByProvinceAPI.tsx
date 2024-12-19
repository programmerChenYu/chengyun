import {http} from "../../utils";

export function numberOfUsersByProvinceAPI(level: number, name?: string) {
    if (name === '') {
        return http.get(`/user/userGeographic/${level}`);
    } else {
        return http.get(`/user/userGeographic/${level}?name=${name}`);
    }
}
