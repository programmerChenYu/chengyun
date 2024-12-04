import {http} from "../../utils";

export function pagingQueryAuditedArticleListAPI(currentPage: number, pageSize: number) {
    return http.post(
        `/blog/article/pagingQueryAuditedArticleList`,
        {
            currentPage: currentPage,
            pageSize: pageSize
        }
    )
}
