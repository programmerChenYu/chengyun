import {http} from "../../utils";

export function searchUserAPI(searchValue: string, statusValue: string|undefined, currentPage: number, pageSize: number) {
    return http.post(
        `/user/userProfile/searchUser`,
        {
            searchValue: searchValue,
            statusValue: statusValue,
            currentPage: currentPage,
            pageSize: pageSize
        }
    )
}
