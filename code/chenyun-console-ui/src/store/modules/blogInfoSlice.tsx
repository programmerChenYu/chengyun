import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../index.tsx";

interface DataType {
    content?: string,
    flush: boolean
}

const initialState: DataType = {
    flush: false
}

const blogInfoSlice = createSlice({
    name: 'blogInfo',
    initialState,
    reducers: {
        setBlogInfoContent: (state, action: PayloadAction<string>) => {
            state.content = action.payload;
        },
        setBlogInfoFlush: (state) => {
            state.flush = !state.flush;
        }
    }
})

export const {setBlogInfoContent, setBlogInfoFlush} = blogInfoSlice.actions;
const blogInfoReducer = blogInfoSlice.reducer;
export default blogInfoReducer;
export const blogInfo = (state: RootState) => state.blogInfo;
