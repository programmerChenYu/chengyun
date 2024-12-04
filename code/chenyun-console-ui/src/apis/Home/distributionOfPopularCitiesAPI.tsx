import {http} from "../../utils";

/**
 * 热门城市分布
 */
export function distributionOfPopularCitiesAPI() {
    return http.get(`/user/userGeographic/distributionOfPopularCities`);
}
