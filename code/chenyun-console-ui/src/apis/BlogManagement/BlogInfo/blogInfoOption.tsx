import {http} from "../../../utils";

/**
 * 文章审核不通过
 * @param key
 */
export function blogInfoRefuseAPI(key: string|null) {
    return http.put(
        `/blog/article/blogInfoRefuse`,
        {
            key: key
        }
    )
}

/**
 * 文章审核通过
 * @param key
 */
export function blogInfoProcessAPI(key: string|null) {
    return http.put(
        `/blog/article/blogInfoProcess`,
        {
            key: key
        }
    )
}
