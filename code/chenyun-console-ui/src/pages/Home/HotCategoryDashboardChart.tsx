import * as echarts from 'echarts';
import React, {useEffect, useRef} from "react";

interface DashboardProps {
    data: object,
    color?: string
}

const HotCategoryDashboardChart: React.FC<DashboardProps> = (props) => {
    const testData = props.data;

    const dashboardRef = useRef(null);
    useEffect(() => {
        let dashboardCharts = echarts.init(dashboardRef.current);
        const option = {
            series: [
                {
                    type: 'gauge',
                    startAngle: 90,
                    endAngle: -270,
                    pointer: {
                        show: false
                    },
                    progress: {
                        show: true,
                        overlap: false,
                        roundCap: false,
                        clip: false,
                        itemStyle: {
                            borderWidth: 1,
                            borderColor: '#a9a9a9',
                            color: `${props.color ? props.color : 'orange'}`
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            width: 15,
                            color: [
                                [1, 'rgba(169,169,169,0.3)']
                            ]
                        }
                    },
                    splitLine: {
                        show: false,
                        distance: 0,
                        length: 10
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        show: false,
                        distance: 50
                    },
                    data: testData,
                    title: {
                        fontSize: 12
                    },
                    detail: {
                        width: 20,
                        height: 14,
                        fontSize: 14,
                        color: `${props.color ? props.color : 'orange'}`,
                        borderColor: `${props.color ? props.color : 'orange'}`,
                        borderRadius: 20,
                        borderWidth: 1,
                        formatter: '{value}%'
                    }
                }
            ]
        }
        dashboardCharts.setOption(option);
    }, [testData])

    return (
        <>
            <div className={"chenyun-dashboard-category"} style={{
                height: "150px",
                width: "150px",
                paddingTop: '20px'
            }} ref={dashboardRef}></div>
        </>
    )
}

export default HotCategoryDashboardChart;
