import {http} from "../../utils";

export function pagingQuerySearchArticleListAPI(searchValue: string|null, statusValue: string|null, currentPage: number, pageSize: number) {
    return http.post(
        `/blog/article/pagingQuerySearchArticleList`,
        {
            title: searchValue,
            status: statusValue,
            currentPage: currentPage,
            pageSize: pageSize
        }
    )
}
