import {http} from "../../utils";

export function userVisitsInTheLastWeekAPI() {
    return http.get(`/user/access/userVisitsInTheLastWeek`)
}
