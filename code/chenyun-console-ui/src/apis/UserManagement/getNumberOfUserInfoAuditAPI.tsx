import {http} from "../../utils";

export function getNumberOfUserInfoAuditAPI() {
    return http.get(`/user/userProfile/getNumberOfUserInfoAudit`);
}
