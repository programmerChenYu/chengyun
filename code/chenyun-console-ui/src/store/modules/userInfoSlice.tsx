import {UserInterface} from "../../interface";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../index.tsx";

interface DataType {
    data?: UserInterface,
    flush?: boolean,
}

const testData: UserInterface = {
    key: 1,
    nickname: '管理员001',
    location: '中国/湖北省/武汉市',
    email: '658942185@123.com',
    information: '这是测试数据' +
        '<br/> '+
        '这是测试数据'
}

const initialState: DataType = {
    data: testData,
    flush: false,
}

const userInfoSlice = createSlice({
    name: "userInfo",
    initialState,
    reducers: {
        setUserInfoData: (state, action: PayloadAction<UserInterface>) => {
            state.data = action.payload;
        },
        setUserInfoFlush: (state) => {
            state.flush = !state.flush;
        }
    }
})

export const {setUserInfoData, setUserInfoFlush} = userInfoSlice.actions;
const userInfoReducer = userInfoSlice.reducer;
export default userInfoReducer;
export const userInfo = (state: RootState) => state.userInfo;
