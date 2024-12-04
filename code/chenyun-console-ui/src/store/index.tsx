import {configureStore} from "@reduxjs/toolkit";
import populationOfProvinceReducer from "./modules/populationOfProvinceSlice.tsx";
import popularRegionLevelReducer from "./modules/popularRegionLevelSlice.tsx";
import historicalPathReducer from "./modules/historicalPathSlice.tsx";
import pageForAListOfUsersReducer from "./modules/pageForAListOfUsersSlice.tsx";
import userInfoAuditListReducer from "./modules/userInfoAuditListSlice.tsx";
import userInfoReducer from "./modules/userInfoSlice.tsx";
import searchUserListReducer from "./modules/seacherUserListSlice.tsx";
import searchUserInfoAuditListReducer from "./modules/searchUserInfoAuditListSlice.tsx";
import tagListReducer from "./modules/tagListSlice.tsx";
import searchTagListReducer from "./modules/searchTagListSlice.tsx";
import blogListReducer from "./modules/blogListSlice.tsx";
import blogInfoReducer from "./modules/blogInfoSlice.tsx";

const store = configureStore({
    reducer: {
        populationOfProvince: populationOfProvinceReducer,
        popularRegionLevel: popularRegionLevelReducer,
        historicalPath: historicalPathReducer,
        pageForAListOfUsers: pageForAListOfUsersReducer,
        userInfoAuditList: userInfoAuditListReducer,
        userInfo: userInfoReducer,
        searchUserList: searchUserListReducer,
        searchUserInfoAuditList: searchUserInfoAuditListReducer,
        tagList: tagListReducer,
        searchTagList: searchTagListReducer,
        blogList: blogListReducer,
        blogInfo: blogInfoReducer
    },
})

export default store;
// 从 store 本身推断出 `RootState` 和 `AppDispatch` 类型
export type RootState = ReturnType<typeof store.getState>
// 推断出类型: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch
