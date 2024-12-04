import * as echarts from 'echarts'
import {useEffect, useRef, useState} from "react";
import {userVisitsInTheLastWeekAPI} from "../../apis/Home/userVisitsInTheLastWeekAPI.tsx";

interface TooltipFormatterParams {
    componentType: 'series',
    // 系列类型
    seriesType: string,
    // 系列在传入的 option.series 中的 index
    seriesIndex: number,
    // 系列名称
    seriesName: string,
    // 数据名，类目名
    name: string,
    // 数据在传入的 data 数组中的 index
    dataIndex: number,
    // 传入的原始数据项
    data: Object,
    // 传入的数据值。在多数系列下它和 data 相同。在一些系列下是 data 中的分量（如 map、radar 中）
    value: number|Object,
    encode: Object,
    // 维度名列表
    dimensionNames: Array<String>,
    // 数据的维度 index，如 0 或 1 或 2 ...
    // 仅在雷达图中使用。
    dimensionIndex: number,
    // 数据图形的颜色
    color: string
}


const UserAccessLineChart = () => {
    const lineChartRef = useRef(null);

    const [userVisitsDay, setUserVisitsDay] = useState<string[]>([])
    const [userVisits, setUserVisits] = useState<number[]>([])
    const tmpUserVisitsDay: string[] = [];
    const tmpUserVisits: number[] = [];

    // 3: 优秀；2: 良好；1: 一般；0: 惨淡
    const [activeLevel, setActiveLevel] = useState<number>(0);
    const [activeColor, setActiveColor] = useState<string>('#E71010FF');

    useEffect(() => {

        const userVisitsInTheLastWeek = async () => {
            try {
                const res = await userVisitsInTheLastWeekAPI();
                for (let i = 0; i < res.data.data.length; i++) {
                    tmpUserVisitsDay.push(res.data.data[i].day);
                    tmpUserVisits.push(res.data.data[i].visits);
                }
                setUserVisitsDay(tmpUserVisitsDay);
                setUserVisits(tmpUserVisits);
            } catch (error) {
            }
        }

        userVisitsInTheLastWeek().then(() => {

        })

    }, [])

    useEffect(() => {
        const sum = userVisits.reduce((accumulator, currentValue) => accumulator + currentValue, 0);
        if (sum > 500) {
            setActiveLevel(3);
            setActiveColor('#099332');
        } else if (sum > 100) {
            setActiveLevel(2);
            setActiveColor('#5C9309FF');
        } else if (sum > 50) {
            setActiveLevel(1);
            setActiveColor('#C2BA0EFF')
        }
        let lineChart = echarts.init(lineChartRef.current);
        const option = {
            tooltip: {
                trigger: 'axis',    // 触发类型
                axisPointer: {  // 坐标轴指示器，坐标轴触发有效
                    type: 'cross'
                },
                formatter: function (params: Array<TooltipFormatterParams>) {
                    let result = '';
                    params.forEach(function (item) {
                        result += item.name + ' 访问量 : ' + item.data + '<br/>';
                    });
                    return result;
                },
                backgroundColor: 'rgba(169, 169, 169, 0.6)',
                borderColor: 'rgba(169, 169, 169, 0.4)',
                textStyle: {
                    color: 'dark'
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: userVisitsDay
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    data: userVisits,
                    type: 'line',
                    areaStyle: {},
                    smooth: true
                }
            ],
            grid: {
                top: 55,
                bottom: 22
            }
        };

        lineChart.setOption(option);
    }, [userVisitsDay])

    return (
        <>
            <div>
                <div style={{paddingTop: '10px'}}>用户活跃度：<br /><span style={{
                    fontSize:'15px',
                    fontWeight: 'bold',
                    paddingTop: '15px',
                    color: `${activeColor}`
                }}>{activeLevel === 3 ? '优秀' : activeLevel === 2 ? '良好' : activeLevel === 1 ? '一般' : '惨淡'}</span></div>
                <div className={"chenyun-user-access-line-chart"} style={{
                    width: '380px',
                    height: '150px',
                    marginTop: '-40px',
                    // border: '1px solid'
                }} ref={lineChartRef}></div>
            </div>
        </>
    )
}

export default UserAccessLineChart;
