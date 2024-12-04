import {http} from "../../utils";

export function infoAuditConfirmAPI(key: React.Key) {
    return http.post(
        `/user/userProfile/infoAuditConfirm`,
        {
            key: key
        }
    )
}
