import {http} from "../../utils";

export function getSearchUserInfoAuditListAPI(searchValue: string, currentPage: number, pageSize: number) {
    return http.post(
        `/user/userProfile/getSearchUserInfoAuditList`,
        {
            searchValue: searchValue,
            currentPage: currentPage,
            pageSize: pageSize
        }
    )
}
