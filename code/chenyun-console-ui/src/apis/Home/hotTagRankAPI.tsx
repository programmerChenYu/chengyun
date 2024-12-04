import {http} from "../../utils";

/**
 * 热门分类
 */
export function hotTagRankAPI() {
    return http.get(`/blog/tag/hotTagRank`)
}
