import {http} from "../../utils";

/**
 * 禁用标签
 * @param key
 */
export function deactivateTagAPI(key: string) {
    return http.put(
        `/blog/tag/deactivateTag`,
        {
            key: key
        }
    )
}

/**
 * 启用标签
 * @param key
 */
export function enableTagAPI(key: string) {
    return http.put(
        `/blog/tag/enableTag`,
        {
            key: key
        }
    )
}

/**
 * 删除标签
 * @param key
 */
export function deleteTagAPI(key: string) {
    return http.delete(
        `/blog/tag/deleteTag/${key}`
    )
}

/**
 * 新建标签
 * @param tagName
 * @param tagStatus
 */
export function createTagAPI(tagName: string, tagStatus: number|undefined) {
    return http.post(
        `/blog/tag/createTag`,
        {
            tagName: tagName,
            tagStatus: tagStatus
        }
    )
}

/**
 * 批量停用
 * @param tagKeyList
 */
export function batchDeactivateAPI(tagKeyList: string[]) {
    return http.put(
        `/blog/tag/batchDeactivate`,
        {
            tagIds: tagKeyList
        }
    )
}


/**
 * 批量启用
 * @param tagIds
 */
export function batchEnableAPI(tagIds: string[]) {
    return http.put(
        `/blog/tag/batchEnable`,
        {
            tagIds: tagIds
        }
    )
}

/**
 * 查询标签
 * @param tagName
 * @param tagStatus
 */
export function pagingQuerySearchTagListAPI(tagName: string|null, tagStatus: string|null, currentPage: number, pageSize: number) {
    return http.post(
        `/blog/tag/pagingQuerySearchTagList`,
        {
            tagName: tagName,
            tagStatus: tagStatus,
            currentPage: currentPage,
            pageSize: pageSize
        }
    )
}
