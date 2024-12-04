import {http} from "../../utils";


// 分页获取用户列表
export function pageForAListOfUsersAPI(currentPage: number, pageSize: number) {
    return http.post(
        `/user/userProfile/pageForAListOfUsers`,
        {
            currentPage: currentPage,
            pageSize: pageSize
        }
    )
}
