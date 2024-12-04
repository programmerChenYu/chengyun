import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../index.tsx";

interface DataType {
    path?: string
}

const initialState: DataType = {
}

const historicalPathSlice = createSlice({
    name: 'historicalPath',
    initialState,
    reducers: {
        setPath: (state, action: PayloadAction<string>) => {
            state.path = action.payload;
        },
    }
})

export const {setPath} = historicalPathSlice.actions;
export const historicalPath = (state: RootState) => state.historicalPath;
const historicalPathReducer = historicalPathSlice.reducer;
export default historicalPathReducer;
