import {http} from "../../utils";

/**
 * 获取待审核的文章总数
 */
export function reviewedBlogPostCountAPI() {
    return http.get(`/blog/article/reviewedBlogPostCount`);
}
