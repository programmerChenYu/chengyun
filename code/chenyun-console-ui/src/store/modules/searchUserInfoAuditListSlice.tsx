import {UserInterface} from "../../interface";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../index.tsx";

interface DataType {
    data?: UserInterface[];
    currentPage?: number;
    totalPage?: number;
    flush: boolean;
}

const initialState: DataType = {
    currentPage: 1,
    flush: false
}

const searchUserInfoAuditListSlice = createSlice({
    name: "searchUserInfoAuditList",
    initialState,
    reducers: {
        setSearchUserInfoAuditListData: (state, action: PayloadAction<UserInterface[]>) => {
            state.data = action.payload;
        },
        setSearchUserInfoAuditListCurrentPage: (state, action: PayloadAction<number>) => {
            state.currentPage = action.payload;
        },
        setSearchUserInfoAuditListFlush: (state) => {
            state.flush = !state.flush;
        },
        setSearchUserInfoAuditListTotalPage: (state, action: PayloadAction<number>) => {
            state.totalPage = action.payload;
        }
    }
})

export const {setSearchUserInfoAuditListData, setSearchUserInfoAuditListCurrentPage,
    setSearchUserInfoAuditListFlush, setSearchUserInfoAuditListTotalPage} = searchUserInfoAuditListSlice.actions;
const searchUserInfoAuditListReducer = searchUserInfoAuditListSlice.reducer;
export default searchUserInfoAuditListReducer;
export const searchUserInfoAuditList = (state: RootState) => state.searchUserInfoAuditList;
