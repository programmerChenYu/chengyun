import React from "react";
import {http} from "../../utils";

export function refuseUserInfoAuditAPI(key: React.Key, reasonValue: string[], reviewInfo: string) {
    return http.post(
        `/user/userProfile/refuseUserInfoAudit`,
        {
            key: key,
            reason: reasonValue,
            reviewInfo: reviewInfo
        }
    )
}
