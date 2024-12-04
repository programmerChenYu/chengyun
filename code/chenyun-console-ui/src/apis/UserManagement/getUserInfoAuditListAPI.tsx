import {http} from "../../utils";

export function getUserInfoAuditListAPI(currentPage: number, pageSize: number) {
    return http.post(
        `/user/userProfile/getUserInfoAuditList`,
        {
            currentPage: currentPage,
            pageSize: pageSize
        }
    );
}
