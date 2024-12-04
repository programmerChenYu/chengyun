import {Empty, Flex, Layout} from "antd";
import './index.css'
import HotCategoryDashboardChart from "./HotCategoryDashboardChart.tsx";
import UserAccessLineChart from "./UserAccessLineChart.tsx";
import UserLocationChart from "./UserLocationChart.tsx";
import TopUserList from "./TopUserList.tsx";
import HotSearch from "./HotSearch.tsx";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {getNumberOfUserInfoAuditAPI} from "../../apis/UserManagement/getNumberOfUserInfoAuditAPI.tsx";
import {reviewedBlogPostCountAPI} from "../../apis/Home/reviewedBlogPostCountAPI.tsx";
import {TagInterface} from "../../interface";
import {hotTagRankAPI} from "../../apis/Home/hotTagRankAPI.tsx";


const Home = () => {

    const navigate = useNavigate();

    const {Content} = Layout;

    // 待审核文章跳转路由
    const reviewedBlogOnClick = () => {
        navigate("/blog/reviewed");
    }

    // 待审核用户信息跳转路由
    const userAuditOnClick = () => {
        navigate("/user/audit")
    }

    // 待审核文章条数
    const [reviewedArticleCount, setReviewedArticleCount] = useState<number>(0);
    // 用户信息待审核个数
    const [userInfoAuditCount, setUserInfoAuditCount] = useState<number>(0);
    // 热门分类
    const [hotTagOne, setHotTagOne] = useState<TagInterface>({key: 0});
    const [hotTagTwo, setHotTagTwo] = useState<TagInterface>({key: 0});
    const [hotTagThree, setHotTagThree] = useState<TagInterface>({key: 0});
    const hotTagOneData = [
        {
            value: hotTagOne.frequencyOfUse,
            name: hotTagOne.tagName,
            title: {
                offsetCenter: ['0%', '-20%']
            },
            detail: {
                valueAnimation: true,
                offsetCenter: ['0%', '20%']
            }
        }
    ]
    const hotTagTwoData = [
        {
            value: hotTagTwo.frequencyOfUse,
            name: hotTagTwo.tagName,
            title: {
                offsetCenter: ['0%', '-20%']
            },
            detail: {
                valueAnimation: true,
                offsetCenter: ['0%', '20%']
            }
        }
    ]
    const hotTagThreeData = [
        {
            value: hotTagThree.frequencyOfUse,
            name: hotTagThree.tagName,
            title: {
                offsetCenter: ['0%', '-20%']
            },
            detail: {
                valueAnimation: true,
                offsetCenter: ['0%', '20%']
            }
        }
    ]
    useEffect(() => {
        // 获取有多少条待审核文章
        const reviewedBlogPostCount = async () => {
            const res = await reviewedBlogPostCountAPI();
            setReviewedArticleCount(res.data.data);
        }
        // 获取有多少用户信息待审核
        const getNumberOfUserInfoAudit = async () => {
            const res = await getNumberOfUserInfoAuditAPI();
            setUserInfoAuditCount(res.data.data);
        }
        // 获取热门分类
        const hotTagRank = async () => {
            const res = await hotTagRankAPI();
            setHotTagOne(res.data.data[0]);
            setHotTagTwo(res.data.data[1]);
            setHotTagThree(res.data.data[2]);
        }
        reviewedBlogPostCount();
        getNumberOfUserInfoAudit();
        hotTagRank();
    }, [])

    return (
        <>
            <div className={"chenyun-home"}>
                <Content className="chenyun-home-backlog">
                    <div className={"chenyun-home-title"}>
                        待办事项
                    </div>
                    <div className={"chenyun-home-backlog-content"}>
                        {(reviewedArticleCount === 0 && userInfoAuditCount === 0) ? <Empty /> :
                            <Flex gap={"small"}>
                                {reviewedArticleCount !== 0 &&
                                <>
                                    <svg className={"chenyun-home-backlog-content-svg"} aria-hidden="true" width={"80px"} height={"65px"}>
                                        <use xlinkHref={"#icon-daibanshixiang"}></use>
                                    </svg>
                                    <div className={"chenyun-home-backlog-content-num"} onClick={reviewedBlogOnClick}>
                                        待审核文章
                                        <br />
                                        {reviewedArticleCount} 篇
                                    </div>
                                </>}
                                {userInfoAuditCount !== 0 &&
                                <>
                                    <svg className={"chenyun-home-backlog-content-svg"} aria-hidden="true" width={"80px"} height={"65px"}>
                                        <use xlinkHref={"#icon-daibanshixiang"}></use>
                                    </svg>
                                    <div className={"chenyun-home-backlog-content-num"} onClick={userAuditOnClick}>
                                        待审核用户信息
                                        <br />
                                        {userInfoAuditCount} 位
                                    </div>
                                </>}
                            </Flex>}
                    </div>
                </Content>
                <Content className={"chenyun-home-region"}>
                    <div className={"chenyun-home-title"}>
                        用户所在地区分布
                    </div>
                    <div className={"chenyun-home-region-content"}>
                        <Flex justify={"space-between"}>
                            <TopUserList />
                            <UserLocationChart />
                        </Flex>
                    </div>
                </Content>
                <Flex style={{height: "30%",}}>
                    <Content className={"chenyun-home-hot-category"}>
                        <div className={"chenyun-home-title"}>
                            热门分类
                        </div>
                        <div className={"chenyun-home-hot-category-content"}>
                            <Flex gap={"small"} justify={"space-evenly"}>
                                <HotCategoryDashboardChart data={hotTagOneData} />
                                <HotCategoryDashboardChart data={hotTagTwoData} color={"blue"}/>
                                <HotCategoryDashboardChart data={hotTagThreeData} color={"green"}/>
                            </Flex>
                        </div>
                    </Content>
                    <Content className={"chenyun-home-hot-search"}>
                        <div className={"chenyun-home-title"}>
                            热门搜索
                        </div>
                        <div className={"chenyun-home-hot-search-content"}>
                            <Flex justify={"center"}>
                                <HotSearch />
                            </Flex>
                        </div>
                    </Content>
                    <Content className={"chenyun-home-user-access"}>
                        <div className={"chenyun-home-title"}>
                            用户访问量
                        </div>
                        <div className={"chenyun-home-user-access-chart"}>
                            <Flex justify={"center"}>
                                <UserAccessLineChart />
                            </Flex>
                        </div>
                    </Content>
                </Flex>
            </div>
        </>
    )
}

export default Home;
