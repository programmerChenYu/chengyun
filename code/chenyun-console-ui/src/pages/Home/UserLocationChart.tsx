import * as echarts from 'echarts';
import {useEffect, useRef, useState} from "react";
import chinaGeoData from '../../assets/json/province.json';
import {useAppDispatch} from "../../store/hooks.ts";
import {setLevel, setName} from "../../store/modules/popularRegionLevelSlice.tsx";
import {distributionOfPopularCitiesAPI} from "../../apis/Home/distributionOfPopularCitiesAPI.tsx";

interface DataType {
    coordinate: number[][],
    city: string[],
    number: number[]
}

const UserLocationChart = () => {
    const locationChartRef = useRef(null);

    const dispatch = useAppDispatch();

    // 最热门城市（前 5 名）
    const [hotCity, setHotCity] = useState<DataType>();
    // 活跃城市（6-15 名）
    const [activeCity, setActiveCity] = useState<DataType>();
    // 将前 5 名放入 hotCity
    const tmpHotCity: DataType = {
        coordinate: [],
        city: [],
        number: []
    };
    const tmpActiveCity: DataType = {
        coordinate: [],
        city: [],
        number: []
    };

    useEffect(() => {
        const distributionOfPopularCities = async () => {
            const res = await distributionOfPopularCitiesAPI();
            for (let i = 0; i < res.data.data.coordinate.length; i++) {
                if (i < 5) {
                    tmpHotCity.coordinate.push(res.data.data.coordinate[i]);
                    tmpHotCity.city.push(res.data.data.city[i]);
                    tmpHotCity.number.push(res.data.data.number[i]);
                } else {
                    tmpActiveCity.coordinate.push(res.data.data.coordinate[i]);
                    tmpActiveCity.city.push(res.data.data.city[i]);
                    tmpActiveCity.number.push(res.data.data.number[i]);
                }
            }
            setHotCity(tmpHotCity);
            setActiveCity(tmpActiveCity);
        }

        distributionOfPopularCities();

    }, [])

    useEffect(() => {
        if (hotCity != undefined) {
            let locationChart = echarts.init(locationChartRef.current);
            locationChart.showLoading();
            locationChart.hideLoading();

            // @ts-ignore
            echarts.registerMap('china',chinaGeoData)
            let option = {
                geo: {
                    map: 'china',//必须写
                    roam:true,// 拖拽功能；自选关闭开启
                    zoom: 5,//地图缩放比例
                    center: [100, 30.52],//地图位置
                    //地图省份的样式；包括板块颜色和边框颜色
                    itemStyle: {
                        areaColor: '#f5f2f2',
                        borderColor: "#835f5f",
                    },
                    //省份字体样式；包括是否展示，字体大小和颜色
                    label: {
                        normal: {
                            show:true,
                            fontSize: "11.5",
                            color: "rgb(107,102,102)"
                        }
                    },
                    //鼠标划过的高亮设置；包括省份板块颜色和字体等
                    emphasis: {
                        itemStyle: {
                            areaColor: 'rgba(255, 165, 0, 0.4)',
                        },
                        label: {
                            show: true,
                            color:"black"
                        },
                        focus: 'self'
                    }
                },
                tooltip: {
                    trigger: 'item'
                },
                series: [
                    //涟漪特效
                    {
                        name: "UserDistribution",
                        type: "effectScatter",
                        coordinateSystem: "geo",
                        data:hotCity?.coordinate,//传入的地图点数据
                        symbolSize: 6,//涟漪大小
                        showEffectOn: "render",
                        symbolKeepAspect: true,
                        //涟漪效应
                        rippleEffect: {
                            brushType: "stroke",
                            color: "#f13434",
                            period: 6,//周期
                            scale: 6//规模
                        },
                        hoverAnimation: true,//悬停动画
                        //地图点样式
                        label: {
                            formatter: "{b}",
                            position: "top",
                            show: true,
                            fontSize: "10",
                        },
                        itemStyle: {
                            color: "#F13434FF",
                            shadowBlur: 2,
                            shadowColor: "#333"
                        },
                        //鼠标点击散点的下弹框
                        tooltip: {
                            show: true,
                            position: ['30%', '30%'],
                            triggerOn:"click",
                            backgroundColor: "rgba(169,169,169,0.3)",
                            borderColor: "rgba(169,169,169,0.3)",
                            formatter: function(point: object) {
                                // @ts-ignore
                                const tmp: number[] = point.value;
                                let index: number = -1;
                                // @ts-ignore
                                for (let i: number = 0; i < hotCity.coordinate.length; i++) {
                                    const flag = tmp.every(value => hotCity?.coordinate[i].some(v => v === value));
                                    if (flag) {
                                        index = i;
                                        break;
                                    }
                                }
                                if (index === -1) {
                                    return `
                                    <div style="width: 150px; height: 80px">
                                        <h1>数据加载出错</h1>
                                    </div>`;
                                } else {
                                    const city = hotCity?.city[index];
                                    const count = hotCity?.number[index];
                                    return `
                                    <div style="width: 150px; height: 80px">
                                        <h1>${city}</h1>
                                        <hr />
                                        <div>注册用户数：<strong>${count}</strong></div>
                                    </div>`
                                }
                            }
                        },
                        zlevel: 1
                    },
                    {
                        name: "UserDistribution",
                        type: "effectScatter",
                        coordinateSystem: "geo",
                        data:activeCity?.coordinate,//传入的地图点数据
                        symbolSize: 4,//涟漪大小
                        showEffectOn: "render",
                        //涟漪效应
                        rippleEffect: {
                            brushType: "stroke",
                            color: "#3480f1",
                            period: 10,//周期
                            scale: 10//规模
                        },
                        hoverAnimation: true,//悬停动画
                        //地图点样式
                        label: {
                            formatter: "{b}",
                            position: "top",
                            show: true,
                            fontSize: "10",
                        },
                        itemStyle: {
                            color: "#3480f1",
                            shadowBlur: 2,
                            shadowColor: "#333"
                        },
                        //鼠标点击散点的下弹框
                        tooltip: {
                            show: true,
                            position: ['30%', '30%'],
                            triggerOn:"click",
                            backgroundColor: "rgba(169,169,169,0.3)",
                            borderColor: "rgba(169,169,169,0.3)",
                            formatter: function(point: Array<number>) {
                                // @ts-ignore
                                const tmp: number[] = point.value;
                                let index: number = -1;
                                // @ts-ignore
                                for (let i: number = 0; i < activeCity.coordinate.length; i++) {
                                    const flag = tmp.every(value => activeCity?.coordinate[i].some(v => v === value));
                                    if (flag) {
                                        index = i;
                                        break;
                                    }
                                }
                                if (index === -1) {
                                    return `
                                    <div style="width: 150px; height: 80px">
                                        <h1>数据加载出错</h1>
                                    </div>`;
                                } else {
                                    const city = activeCity?.city[index];
                                    const count = activeCity?.number[index];
                                    return `
                                    <div style="width: 150px; height: 80px">
                                        <h1>${city}</h1>
                                        <hr />
                                        <div>注册用户数：<strong>${count}</strong></div>
                                    </div>`
                                }
                            }
                        },
                        zlevel: 1
                    }
                ]
            }

            locationChart.on('click', (params) => {
                if (params.componentType == 'geo') {
                    dispatch(setLevel(2));
                    dispatch(setName(params.name));
                }
            })

            locationChart.setOption(option);
        }
    }, [hotCity])


    return (
        <>
            <div ref={locationChartRef} style={{
                width: '70vw',
                height: '325px',
                // border: '1px solid',
            }}></div>
        </>
    )
}

export default UserLocationChart;
