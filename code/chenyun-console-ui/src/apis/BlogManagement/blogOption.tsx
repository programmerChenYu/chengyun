import {http} from "../../utils";

/**
 * 下架博文
 * @param key
 */
export function articleRemovalAPI(key: string) {
    return http.delete(
        `/blog/article/articleRemoval/${key}`
    )
}
