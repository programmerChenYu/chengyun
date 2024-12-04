import {BlogPostInterface} from "../../interface";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../index.tsx";

interface DataType {
    data?: BlogPostInterface[],
    currentPage: number,
    countOfPage?: number,
    flush: boolean
}

const initialState: DataType = {
    currentPage: 1,
    flush: false
}

const blogListSlice = createSlice({
    name: "blogList",
    initialState,
    reducers: {
        setBlogListData: (state, action: PayloadAction<BlogPostInterface[]>) => {
            state.data = action.payload;
        },
        setBlogListCurrentPage: (state, action: PayloadAction<number>) => {
            state.currentPage = action.payload;
        },
        setBlogListCountOfPage: (state, action: PayloadAction<number>) => {
            state.countOfPage = action.payload;
        },
        setBlogListFlush: (state) => {
            state.flush = !state.flush;
        }
    }
})

export const {setBlogListData, setBlogListCurrentPage, setBlogListCountOfPage, setBlogListFlush} = blogListSlice.actions;
const blogListReducer = blogListSlice.reducer;
export default blogListReducer;
export const blogList = (state: RootState) => state.blogList;
