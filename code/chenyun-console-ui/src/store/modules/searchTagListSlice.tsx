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

const searchTagListSlice = createSlice({
    name: "searchTagList",
    initialState,
    reducers: {
        setSearchTagListData: (state, action: PayloadAction<TagInterface[]>) => {
            state.data = action.payload;
        },
        setSearchTagListCurrentPage: (state, action: PayloadAction<number>) => {
            state.currentPage = action.payload;
        },
        setSearchTagListCountOfPage: (state, action: PayloadAction<number>) => {
            state.countOfPage = action.payload;
        },
        setSearchTagListFlush: (state) => {
            state.flush = !state.flush;
        }
    }
})

export const {setSearchTagListData, setSearchTagListCurrentPage,
    setSearchTagListCountOfPage, setSearchTagListFlush} = searchTagListSlice.actions;
const searchTagListReducer = searchTagListSlice.reducer;
export default searchTagListReducer;
export const searchTagList = (state: RootState) => state.searchTagList;
