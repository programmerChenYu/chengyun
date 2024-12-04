import {http} from "../../utils";

export function pagingQueryArticleListAPI(currentPage: number, pageSize: number) {
    return http.post(
        `/blog/article/pagingQueryArticleList`,
        {
            currentPage: currentPage,
            pageSize: pageSize
        }
    )
}
