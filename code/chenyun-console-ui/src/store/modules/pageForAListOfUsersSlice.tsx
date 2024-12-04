import {UserInterface} from "../../interface";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../index.tsx";

interface DataType {
    data?: UserInterface[];
    currentPage?: number;
    flush?: boolean;
}

const initialState: DataType = {
    currentPage: 1,
    flush: false
}

// 分页返回的用户列表数据
const pageForAListOfUsersSlice = createSlice({
    name: "pageForAListOfUsers",
    initialState,
    reducers: {
        setPageForAListOfUsersData: (state, action: PayloadAction<UserInterface[]>) => {
            state.data = action.payload;
        },
        setPageForAListOfUsersCurrentPage: (state, action: PayloadAction<number>) => {
            state.currentPage = action.payload;
        },
        setPageForAListOfUsersFlush: (state) => {
            state.flush = !state.flush;
        }
    }
})

export const {setPageForAListOfUsersData, setPageForAListOfUsersCurrentPage, setPageForAListOfUsersFlush} = pageForAListOfUsersSlice.actions;
const pageForAListOfUsersReducer = pageForAListOfUsersSlice.reducer;
export default pageForAListOfUsersReducer;
export const pageForAListOfUsers = (state: RootState) => state.pageForAListOfUsers;
