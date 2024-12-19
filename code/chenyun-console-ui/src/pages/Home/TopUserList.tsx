import './TopUserList.css';
import {useEffect} from 'react';
import {Flex, Table} from 'antd';
import {useAppDispatch, useAppSelector} from "../../store/hooks.ts";
import {PopulationOfProvinceType, setState} from "../../store/modules/populationOfProvinceSlice.tsx";
import {numberOfUsersByProvinceAPI} from "../../apis/Home/numberOfUsersByProvinceAPI.tsx";
import {setLevel, setName} from "../../store/modules/popularRegionLevelSlice.tsx";

const { Column } = Table;

const TopUserList = () => {

    const dispatch = useAppDispatch();
    // 热门地区排名数据
    const populationOfProvince = useAppSelector(state => state.populationOfProvince.value);
    // 要展示的级别，针对热门地区的
    const popularRegionLevel = useAppSelector(state => state.popularRegionLevel);

    useEffect(() => {
        const getTopUserList = async () => {
            try {
                // 1.获取各省份用户人数，前五名
                const res = await numberOfUsersByProvinceAPI(popularRegionLevel.level, popularRegionLevel.name);
                // setTopUserListData(res.data)
                dispatch(setState(res.data.data));
            } catch (error) {
                throw new Error('出错')
            }
        }
        getTopUserList();
    }, [popularRegionLevel])

    const flushListOnClick = () => {
        dispatch(setLevel(1));
        dispatch(setName(''));
    }
    return (
        <>
            <div className={"chenyun-home-region-content-list"}>
                <div className={"chenyun-home-region-content-list-title"}>
                    <Flex justify={"flex-start"} align={"center"}>
                        <span>{popularRegionLevel.level == 1 ? '各省份' : popularRegionLevel.name}注册用户数（前 5 名）</span>
                        <svg className={"chenyun-home-region-content-list-title-flush"} aria-hidden={"true"} width={"20px"} height={"18px"} onClick={flushListOnClick}>
                            <use xlinkHref={"#icon-shuaxin"}></use>
                        </svg>
                    </Flex>
                </div>
                <div className={"chenyun-home-region-content-list-content"}>
                    <Table<PopulationOfProvinceType> dataSource={populationOfProvince} pagination={false} size={"middle"}>
                        <Column title={""} dataIndex={"rank"} key={"rank"} align={"center"} render={(rank: number) => (
                            <>
                                <div>
                                    {rank === 1 ?
                                        <svg className={"chenyun-home-region-content-list-content-svg"} aria-hidden={"true"} width={"20px"} height={"18px"}>
                                            <use xlinkHref={"#icon-daochu1024-26"}></use>
                                        </svg> : rank === 2 ?
                                            <svg className={"chenyun-home-region-content-list-content-svg"} aria-hidden={"true"} width={"20px"} height={"18px"}>
                                                <use xlinkHref={"#icon-daochu1024-27"}></use>
                                            </svg> : rank === 3 ?
                                                <svg className={"chenyun-home-region-content-list-content-svg"} aria-hidden={"true"} width={"20px"} height={"18px"}>
                                                    <use xlinkHref={"#icon-daochu1024-28"}></use>
                                                </svg> :
                                                    <div>
                                                        {rank}
                                                    </div>}
                                </div>
                            </>
                        )}/>
                        <Column title="地区" dataIndex="name" key="name" align={"center"} />
                        <Column title="用户数量" dataIndex="users" key="users" align={"center"} />
                    </Table>
                </div>
            </div>
        </>
    )
}

export default TopUserList
