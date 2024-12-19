import {
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    BookOutlined,
    UserOutlined,
    TagOutlined,
    DashboardOutlined,
    GithubOutlined, LogoutOutlined,
} from '@ant-design/icons';
import {Layout, Menu, Avatar, Flex, ConfigProvider, Tooltip, MenuProps, Dropdown} from 'antd';
import React, {useEffect, useState} from 'react';
const { Header, Sider, } = Layout;
import './App.css';
import {Link, Outlet, useLocation, useNavigate} from "react-router-dom";
import zhCN from 'antd/locale/zh_CN';
import {UserInterface} from "./interface";
import {getLoginUserInfoAPI} from "./apis/Auth/getLoginUserInfoAPI.tsx";
import {checkToken} from "./utils";
import {logoutAPI} from "./apis/Auth/logoutAPI.tsx";

function App() {
    const [collapsed, setCollapsed] = useState<boolean>(false);

    const [selectedKey, setSelectedKey] = useState<string[]>();
    const location = useLocation();

    const navigate = useNavigate();

    // 登录的用户信息
    const [userInfo, setUserInfo] = useState<UserInterface|null>(null)

    const items: MenuProps['items'] = [
        {
            key: '1',
            label: '退出登录',
            icon: <LogoutOutlined />
        },
    ];

    // 退出登录按钮点击事件
    const onClick: MenuProps['onClick'] = () => {
        const logout = async () => {
            const res = await logoutAPI();
            if (res.data.data === true) {
                localStorage.removeItem('cyToken');
                navigate("/login");
            }
        }
        logout();
    };

    useEffect(() => {
        const token = localStorage.getItem('cyToken');
        if (token == null) {
            navigate("/login")
        }
        // 获取用户信息
        const getLoginUserInfo = async () => {
            try {
                const res = await getLoginUserInfoAPI(token);
                if (!checkToken.check(res.data.msg)) {
                    navigate("/login");
                }
                setUserInfo(res.data.data);
            } catch (error) {
            }
        }
        getLoginUserInfo();
        // 根据当前路由设置 selectedKeys
        const path = '/' + location.pathname.split('/')[1]
        if (path === '/') {
            setSelectedKey(['1']);
        } else if (path === '/user') {
            setSelectedKey(['2']);
        } else if (path === '/tag') {
            setSelectedKey(['3']);
        } else if (path === '/blog') {
            setSelectedKey(['4']);
        } else if (path === '/blog-info') {
            setSelectedKey(['4']);
        } else {
            setSelectedKey(['1']); // 或者设置为默认的选中项
        }
    }, [collapsed, location])

    return (
    <>
        <ConfigProvider theme={{
            components: {
                Input: {
                    hoverBorderColor: '#FFA500FF',
                    activeBorderColor: '#FFA500FF',
                    activeShadow: '0 0 0 1px rgba(5, 145, 255, 0.1)'
                },
                Button: {
                    defaultHoverBorderColor: 'rgba(169,169,169)',
                    defaultHoverColor: '#000000FF'
                },
                Select: {
                    activeBorderColor: '#FFA500FF',
                    hoverBorderColor: '#FFA500FF',
                    optionSelectedBg: 'rgba(255,165,0,0.2)'
                },
                Layout: {
                    headerHeight: '65px'
                }
            },
            token: {
                colorPrimary: 'rgba(234,157,59,0.4)'
            }
        }} locale={zhCN}>
            <Layout className={"chenyun-layout"}>
                <Sider className={"chenyun-left-sider"} trigger={null} collapsible collapsed={collapsed}>
                    <div className="chenyun-left-sider-logo">
                        <Flex align={"center"} style={{height:'64px'}} justify={"center"} gap={"small"}>
                            <svg aria-hidden={"true"} width={'20px'} height={'20px'}>
                                <use xlinkHref={"#icon-chengyun"} />
                            </svg>
                            <div className={`chenyun-left-sider-logo-text ${!collapsed && 'visible'}`}>橙云控制中心</div>
                        </Flex>
                    </div>
                    <Menu
                        theme="dark"
                        mode="inline"
                        selectedKeys={selectedKey}
                        items={[
                            {
                                key: '1',
                                icon: <DashboardOutlined />,
                                label: <Link to={'/'}>首页</Link>
                            },
                            {
                                key: '2',
                                icon: <UserOutlined />,
                                label: <Link to={"/user"}>用户管理</Link>
                            },
                            {
                                key: '3',
                                icon: <TagOutlined />,
                                label: <Link to={"/tag"}>标签管理</Link>
                            },
                            {
                                key: '4',
                                icon: <BookOutlined />,
                                label: <Link to={"/blog"}>博客管理</Link>
                            },
                        ]}
                    >
                    </Menu>
                </Sider>
                <Layout className="chenyun-right-layout">
                    <Header className="chenyun-right-layout-header">
                        <Flex className={"chenyun-right-layout-header-right-flex"} justify={"space-between"} align={"center"}>
                            {React.createElement(collapsed ? MenuUnfoldOutlined : MenuFoldOutlined, {
                                className: 'chenyun-right-layout-header-trigger',
                                onClick: () => setCollapsed(!collapsed),
                            })}
                            <Flex align={"center"} gap={"middle"} style={{height: '65px'}}>
                                <Tooltip placement="bottom" title={"橙云 github 链接"} mouseEnterDelay={0.5}
                                         color={`rgba(153, 153, 153, 0.4)`}>
                                    <Flex className={"chenyun-github"} onClick={() => window.location.href = 'https://github.com/programmerChenYu/chengyun'}>
                                            <GithubOutlined style={{fontSize: "20px", cursor: 'pointer'}} />
                                        <div style={{marginLeft: '5px'}}>GitHub</div>
                                    </Flex>
                                </Tooltip>

                                <Flex align={"center"} gap={"small"}>
                                    <Avatar className="chenyun-right-layout-header-avater" src={userInfo?.avatar} shape={"circle"} icon={<UserOutlined />} />
                                    <Dropdown menu={{items, onClick}}>
                                        <span className={"chenyun-right-layout-header-username"}>{userInfo?.nickname}</span>
                                    </Dropdown>
                                </Flex>
                            </Flex>
                        </Flex>
                    </Header>
                    <Outlet />
                </Layout>
            </Layout>
        </ConfigProvider>
    </>
  )
}

export default App
