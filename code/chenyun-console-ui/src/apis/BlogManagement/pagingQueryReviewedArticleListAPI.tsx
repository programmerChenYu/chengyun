import {http} from "../../utils";

export function pagingQueryReviewedArticleListAPI(currentPage: number, pageSize: number) {
    return http.post(
        `/blog/article/pagingQueryReviewedArticleList`,
        {
            currentPage: currentPage,
            pageSize: pageSize
        }
    )
}
