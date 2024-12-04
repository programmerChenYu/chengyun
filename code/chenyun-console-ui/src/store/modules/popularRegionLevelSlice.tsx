import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../index.tsx";

interface DataType {
    level: number,
    name: string
}

const initialState: DataType = {
    level: 1,
    name: ''
}

const popularRegionLevelSlice = createSlice({
    name: 'popularRegionLevel',
    initialState,
    reducers: {
        setLevel: (state, action: PayloadAction<number>) => {
            state.level = action.payload;
        },
        setName: (state, action: PayloadAction<string>) => {
            state.name = action.payload;
        }
    }
})

export const {setLevel, setName} = popularRegionLevelSlice.actions;
export const popularRegionLevel = (state: RootState) => state.popularRegionLevel;
const popularRegionLevelReducer = popularRegionLevelSlice.reducer;
export default popularRegionLevelReducer;
