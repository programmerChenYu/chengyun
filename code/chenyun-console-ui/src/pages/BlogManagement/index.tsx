import './index.css'
import {Button, ConfigProvider, Flex, Input, Menu, MenuProps, Select} from "antd";
import {Content} from "antd/es/layout/layout";
import React, {useEffect, useState} from "react";
import {Link, Outlet, useLocation, useNavigate} from "react-router-dom";
import {AuditOutlined, FileDoneOutlined} from "@ant-design/icons";
import BlogList from "./BlogList.tsx";
import {useAppDispatch} from "../../store/hooks.ts";
import {setBlogListCurrentPage, setBlogListFlush} from "../../store/modules/blogListSlice.tsx";
import SearchBlogList from "./SearchBlogList.tsx";
import {rsaEncrypt} from "../../utils";

const BlogManagement = () => {

    const location = useLocation();
    const navigate = useNavigate();
    const [currentRoute, setCurrentRoute] = useState<string>('');

    const dispatch = useAppDispatch();

    useEffect(() => {
        // 根据当前路由设置 selectedKeys
        const path = location.pathname;
        if (path === '/blog') {
            setCurrentRoute('1');
        } else if (path === '/blog/audited') {
            setCurrentRoute('2');
        } else if (path === '/blog/reviewed') {
            setCurrentRoute('3');
        } else {
            setCurrentRoute(''); // 或者设置为默认的选中项
        }
    }, [location])

    // 搜索框中的数据
    const [searchValue, setSearchValue] = useState<string>("");
    const searchValueOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchValue(e.target.value);
    }
    // 状态选择框中的数据
    const [statusValue, setStatusValue] = useState<number>();
    const statusValueOnChange = (value: number) => {
        setStatusValue(value);
        console.log(statusValue)
    }
    // 点击查询按钮触发的回调函数
    const searchButtonOnClick = () => {
        if ((searchValue == undefined || searchValue === "") && statusValue == undefined) {
            navigate("/blog");
        } else if (searchValue == undefined || searchValue === "") {
            navigate(`/blog?status=${encodeURIComponent(rsaEncrypt.encrypt(String(statusValue)))}`)
            dispatch(setBlogListCurrentPage(1));
            dispatch(setBlogListFlush());
        } else if (statusValue == undefined) {
            navigate(`/blog?search=${encodeURIComponent(rsaEncrypt.encrypt(searchValue))}`);
            dispatch(setBlogListCurrentPage(1));
            dispatch(setBlogListFlush());
        } else {
            navigate(`/blog?search=${encodeURIComponent(rsaEncrypt.encrypt(searchValue))}&status=${encodeURIComponent(rsaEncrypt.encrypt(String(statusValue)))}`);
            dispatch(setBlogListCurrentPage(1));
            dispatch(setBlogListFlush());
        }
    }
    // 点击清空按钮触发的回调函数
    const clearSearchButtonOnClick = () => {
        setSearchValue("");
        setStatusValue(undefined);
    }

    // 导航栏
    type MenuItem = Required<MenuProps>['items'][number];
    const items: MenuItem[] = [
        {
            key: '1',
            label: (
                <Link to={"/blog"}>全部博文</Link>
            ),
        },
        {
            key: '2',
            label: (
                <Link to={"/blog/audited"}>已公开文章</Link>
            ),
            icon: <FileDoneOutlined />,
        },
        {
            key: '3',
            label: (
                <Link to={"/blog/reviewed"}>待审核文章</Link>
            ),
            icon: <AuditOutlined />,
        },
    ];
    const currentRouteOnClick: MenuProps['onClick'] = (e) => {
        dispatch(setBlogListCurrentPage(1));
        setCurrentRoute(e.key);
    };

    return (
        <>
            <div className={"chenyun-blog"}>
                <Flex vertical={true} align={"center"} gap={"large"}>
                    <Content className={"chenyun-blog-title"}>
                        <Flex align={"center"} style={{height: '50px'}}>
                            <div>博客管理</div>
                        </Flex>
                    </Content>
                    <Content className={"chenyun-blog-content"}>
                        <Flex  vertical={true} style={{paddingTop:'24px', paddingLeft:'20px', paddingRight:'20px'}}>
                            <div className={"chenyun-blog-content-search"}>
                                <Flex gap={"large"}>
                                    <Flex align={"center"}>
                                        <div className={"chenyun-blog-content-search-blogTitle"}>文章题目:</div>
                                        <Input placeholder={"请输入文章题目"} style={{width: '12vw'}} value={searchValue} onChange={searchValueOnChange}/>
                                    </Flex>
                                    <Flex align={"center"} style={{marginRight:'30px'}}>
                                        <div className={"chenyun-blog-content-search-status"}>状态:</div>
                                        <Select size={"middle"} style={{width:'100px'}} placeholder={"请选择"} options={[
                                            { value: 1, label: '待审核' },
                                            { value: 2, label: '已公开' },
                                        ]} value={statusValue} onChange={statusValueOnChange}/>
                                    </Flex>
                                    <ConfigProvider theme={{
                                        components: {
                                            Button: {
                                                defaultBg: 'rgba(255,165,0,0.8)',
                                                defaultColor: 'rgb(255,255,255)',
                                                defaultBorderColor: 'rgba(255,165,0,0)',
                                                defaultHoverBg: 'rgba(255,165,0,0.9)',
                                                defaultHoverBorderColor: 'rgba(255,165,0,0)',
                                                defaultHoverColor: 'rgb(255,255,255)'
                                            }
                                        }
                                    }}>
                                        <Button onClick={searchButtonOnClick}>查询</Button>
                                    </ConfigProvider>
                                    <Button onClick={clearSearchButtonOnClick}>清空</Button>
                                </Flex>
                            </div>
                            <div className={"chenyun-blog-content-rout"}>
                                <Menu onClick={currentRouteOnClick} selectedKeys={[currentRoute]} mode="horizontal" items={items} />
                            </div>
                            <div className={"chenyun-blog-content-list"}>
                                {currentRoute == '1'&& !location.search && <BlogList />}
                                {currentRoute == '1'&& location.search && <SearchBlogList />}
                                <Outlet />
                            </div>
                        </Flex>
                    </Content>
                    <Content className={"chenyun-blog-author"}>
                        <Flex style={{height:'5vh'}} align={"center"}>
                            <div>Copyright © 2024 爱吃小鱼的橙子出品 <a href={"https://github.com/programmerChenYu/chengyun"} target={'_blank'}>chenyun.com</a></div>
                        </Flex>
                    </Content>
                </Flex>
            </div>
        </>
    )
}

export default BlogManagement;
