import {http} from "../../utils";

export function getHotSearchAPI() {
    return http.get(`/user/option/getHotSearch`)
}
