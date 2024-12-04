import {http} from "../../../utils";

export function getBlogInfoAPI(blogPostId: string|null) {
    return http.post(
        `/blog/article/getBlogInfo`,
        {
            key: blogPostId
        }
    )
}
