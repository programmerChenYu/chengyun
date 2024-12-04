import {http} from "../../utils";

export function pagingQueryTagListAPI(currentPage: number, pageSize: number) {
    return http.post(
        `/blog/tag/pagingQueryTagList`,
        {
            currentPage: currentPage,
            pageSize: pageSize
        }
    )
}
