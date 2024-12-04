import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import React from "react";
import {RootState} from "../index.tsx";

export interface PopulationOfProvinceType {
    key?: React.Key;
    rank?: number;
    name?: string;
    users?: number;
}

interface DataType {
    value: PopulationOfProvinceType[]
}

const data: PopulationOfProvinceType[] = []

const initialState: DataType = {
    value: data,
}

const populationOfProvinceSlice = createSlice({
    name: 'populationOfProvince',
    initialState,
    reducers: {
        setState: (state, action: PayloadAction<PopulationOfProvinceType[]>) => {
            state.value = action.payload;
        }
    }
})

export const {setState} = populationOfProvinceSlice.actions;
// 选择器等其他代码可以使用导入的 `RootState` 类型
export const populationOfProvince = (state: RootState) => state.populationOfProvince.value
const populationOfProvinceReducer = populationOfProvinceSlice.reducer;
export default populationOfProvinceReducer;
