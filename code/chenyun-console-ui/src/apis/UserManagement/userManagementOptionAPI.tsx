import {http} from "../../utils";

// 封号
export function sealedAccountAPI(id: React.Key) {
    return http.post(
        `/user/userProfile/userManagementOption`,
        {
            userId: id,
            optionType: 0
        }
    )
}

// 解除封号
export function unblockTheAccountAPI(id: React.Key) {
    return http.post(
        `/user/userProfile/userManagementOption`,
        {
            userId: id,
            optionType: 1
        }
    )
}

// 违规
export function illegalAccountAPI(id: React.Key) {
    return http.post(
        `/user/userProfile/userManagementOption`,
        {
            userId: id,
            optionType: 2
        }
    )
}

// 解除违规
export function cancelTheIllegalAccountAPI(id: React.Key) {
    return http.post(
        `/user/userProfile/userManagementOption`,
        {
            userId: id,
            optionType: 3
        }
    )
}


// 批量封号
export function batchSealedAccountAPI(ids: React.Key[]) {
    return http.post(
        `/user/userProfile/userManagementOptionBatch`,
        {
            userIds: ids,
            optionType: 0
        }
    )
}

// 批量解除封号
export function batchUnblockTheAccountAPI(ids: React.Key[]) {
    return http.post(
        `/user/userProfile/userManagementOptionBatch`,
        {
            userIds: ids,
            optionType: 1
        }
    )
}

// 批量违规
export function batchIllegalAccountAPI(ids: React.Key[]) {
    return http.post(
        `/user/userProfile/userManagementOptionBatch`,
        {
            userIds: ids,
            optionType: 2
        }
    )
}

// 批量解除违规
export function batchCancelTheIllegalAccountAPI(ids: React.Key[]) {
    return http.post(
        `/user/userProfile/userManagementOptionBatch`,
        {
            userIds: ids,
            optionType: 3
        }
    )
}
