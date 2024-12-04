import {UserInterface} from "../../interface";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../index.tsx";

interface DataType {
    data?: UserInterface[];
    currentPage?: number;
    flush: boolean;
}

const initialState: DataType = {
    currentPage: 1,
    flush: false
}

const userInfoAuditListSlice = createSlice({
    name: "userInfoAuditList",
    initialState,
    reducers: {
        setUserInfoAuditListData: (state, action: PayloadAction<UserInterface[]>) => {
            state.data = action.payload;
        },
        setUserInfoAuditListCurrentPage: (state, action: PayloadAction<number>) => {
            state.currentPage = action.payload;
        },
        setUserInfoAuditListFlush: (state) => {
            state.flush = !state.flush
        }
    }
})

export const {setUserInfoAuditListData, setUserInfoAuditListCurrentPage, setUserInfoAuditListFlush} = userInfoAuditListSlice.actions;
const userInfoAuditListReducer = userInfoAuditListSlice.reducer;
export default userInfoAuditListReducer;
export const userInfoAuditList = (state: RootState) => state.userInfoAuditList;
