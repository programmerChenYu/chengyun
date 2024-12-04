import {http} from "../../utils";

export function numberOfUsersByProvinceAPI(level: number, name?: string) {
    if (name === '') {
        return http.get(`/user/userDistribution/${level}`);
    } else {
        return http.get(`/user/userDistribution/${level}?name=${name}`);
    }
}