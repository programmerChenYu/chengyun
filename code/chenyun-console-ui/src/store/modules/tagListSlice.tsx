import {TagInterface} from "../../interface";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../index.tsx";


interface DataType {
    data?: TagInterface[],
    currentPage: number,
    countOfPage?: number,
    flush: boolean,
}

const initialState: DataType = {
    currentPage: 1,
    flush: false,
}

const tagListSlice = createSlice({
    name: "tagList",
    initialState,
    reducers: {
        setTagListData: (state, action: PayloadAction<TagInterface[]>) => {
            state.data = action.payload;
        },
        setTagListCurrentPage: (state, action: PayloadAction<number>) => {
            state.currentPage = action.payload;
        },
        setTagListCountOfPage: (state, action: PayloadAction<number>) => {
            state.countOfPage = action.payload;
        },
        setTagListFlush: (state) => {
            state.flush = !state.flush;
        }
    }
})

export const {setTagListData, setTagListCurrentPage, setTagListCountOfPage, setTagListFlush} = tagListSlice.actions;
const tagListReducer = tagListSlice.reducer;
export default tagListReducer;
export const tagList = (state: RootState) => state.tagList;
