import * as echarts from 'echarts';
import 'echarts-wordcloud';
import {useEffect, useRef, useState} from "react";
import {getHotSearchAPI} from "../../apis/Home/getHotSearchAPI.tsx";

interface DataType {
    name: string;
    value: number;
}

const HotSearch = () => {

    const hotSearchRef = useRef(null);
    const [hotSearchList, setHotSearchList] = useState<DataType[]>([]);
    useEffect(() => {

        const getHotSearch = async () => {
            try {
                const res = await getHotSearchAPI();
                setHotSearchList(res.data.data)
                console.log(res.data.data)
            } catch (error) {}
        }

        getHotSearch();
    }, [])

    useEffect(() => {
        const hotSearchChart = echarts.init(hotSearchRef.current);
        const option = {
            series: [
                {
                    type: 'wordCloud',
                    shape: 'circle',
                    keepAspect: false,
                    // maskImage: maskImage,
                    left: 'center',
                    top: 'center',
                    width: '100%',
                    height: '100%',
                    right: null,
                    bottom: null,
                    sizeRange: [20, 60],
                    rotationRange: [-90, 90],
                    rotationStep: 25,
                    gridSize: 8,
                    drawOutOfBound: false,
                    layoutAnimation: true,
                    textStyle: {
                        fontFamily: 'sans-serif',
                        fontWeight: 'bold',
                        color: function () {
                            var colors = [
                                'rgb(15,118,234)',   // 蓝色
                                'rgb(140,199,236)', // 浅蓝色
                                'rgb(141,245,173)'  // 淡绿色
                            ];
                            var randomColor = colors[Math.floor(Math.random() * colors.length)];
                            return randomColor;
                        }
                    },
                    emphasis: {
                        // focus: 'self',
                        textStyle: {
                            textShadowBlur: 1,
                            textShadowColor: '#333'
                        }
                    },
                    //data属性中的value值却大，权重就却大，展示字体就却大
                    data: hotSearchList
                }
            ]
        };

        hotSearchChart.setOption(option);
    }, [hotSearchList])

    return (
        <>
            <div ref={hotSearchRef} style={{
                height: '160px',
                width: '400px',
                // border: '1px solid'
            }}></div>
        </>
    )
}

export default HotSearch;
