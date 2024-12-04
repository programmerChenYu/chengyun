import {UserInterface} from "../../interface";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../index.tsx";

interface DataType {
    data?: UserInterface[];
    currentPage?: number;
    totalPage?: number;
    flush?: boolean;
}

const initialState: DataType = {
    currentPage: 1,
    flush: false
}

// 分页返回的用户列表数据
const searchUserListSlice = createSlice({
    name: "searchUserList",
    initialState,
    reducers: {
        setSearchUserListData: (state, action: PayloadAction<UserInterface[]>) => {
            state.data = action.payload;
        },
        setSearchUserListFlush: (state) => {
            state.flush = !state.flush;
        },
        setSearchUserListCurrentPage: (state, action: PayloadAction<number>) => {
            state.currentPage = action.payload;
        },
        setSearchUserListTotalPage: (state, action: PayloadAction<number>) => {
            state.totalPage = action.payload;
        }
    }
})

export const {setSearchUserListData, setSearchUserListFlush,
    setSearchUserListCurrentPage, setSearchUserListTotalPage} = searchUserListSlice.actions;
const searchUserListReducer = searchUserListSlice.reducer;
export default searchUserListReducer;
export const searchUserList = (state: RootState) => state.searchUserList;
