import './index.css'
import {Button, ConfigProvider, Flex, Input, Menu, MenuProps, message, Popconfirm, Select} from "antd";
import {Content} from "antd/es/layout/layout";
import React, {useEffect, useState} from "react";
import {AuditOutlined, TeamOutlined} from "@ant-design/icons";
import {Link, Outlet, useLocation, useNavigate} from "react-router-dom";
import UserList from "./UserList.tsx";
import {
    batchIllegalAccountAPI,
    batchSealedAccountAPI,
    batchUnblockTheAccountAPI
} from "../../apis/UserManagement/userManagementOptionAPI.tsx";
import {
    setPageForAListOfUsersFlush
} from "../../store/modules/pageForAListOfUsersSlice.tsx";
import {useAppDispatch} from "../../store/hooks.ts";
import SearchUserList from "./SearchUserList.tsx";
import {
    setSearchUserListCurrentPage,
    setSearchUserListFlush
} from "../../store/modules/seacherUserListSlice.tsx";
import {
    setSearchUserInfoAuditListCurrentPage,
    setSearchUserInfoAuditListFlush
} from "../../store/modules/searchUserInfoAuditListSlice.tsx";
import SearchAuditUserList from "./SearchAuditUserList.tsx";
import {rsaEncrypt} from "../../utils";

const UserManagement = () => {

    // 全局消息弹框
    const [messageApi, contextHolder] = message.useMessage();

    const navigate = useNavigate()
    const location = useLocation();
    const [currentRoute, setCurrentRoute] = useState<string>('');

    const dispatch = useAppDispatch();

    useEffect(() => {
        // 根据当前路由设置 selectedKeys
        const path = location.pathname;
        if (path === '/user') {
            setCurrentRoute('1');
        } else if (path === '/user/audit') {
            setCurrentRoute('2');
        } else if (path === '/user/audit/info') {
            setCurrentRoute('2');
        }  else {
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
    }
    // 点击查询按钮触发的回调函数
    const searchButtonOnClick = () => {
        if (currentRoute === '1') {
            if (searchValue === "" && statusValue == undefined) {
                navigate('/user')
            } else {
                dispatch(setSearchUserListCurrentPage(1))
                navigate(`/user?search=${encodeURIComponent(rsaEncrypt.encrypt(searchValue))}&status=${encodeURIComponent(rsaEncrypt.encrypt(String(statusValue)))}`)
                dispatch(setSearchUserListFlush())
                clearSelectedOnClick();
            }
        } else if (currentRoute === '2') {
            if (searchValue === "") {
                navigate('/user/audit')
            } else {
                dispatch(setSearchUserInfoAuditListCurrentPage(1));
                navigate(`/user/audit?search=${encodeURIComponent(rsaEncrypt.encrypt(searchValue))}`)
                dispatch(setSearchUserInfoAuditListFlush());
            }
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
                <Link to={"/user"}>管理</Link>
            ),
            icon: <TeamOutlined />
        },
        {
            key: '2',
            label: (
                <Link to={"/user/audit"}>信息审核</Link>
            ),
            icon: <AuditOutlined />,
        },
    ];
    const currentRouteOnClick: MenuProps['onClick'] = (e) => {
        setCurrentRoute(e.key);
    };

    const [isVisible, setIsVisible] = useState<boolean>(false);

    // 管理表格中的选中行的数据
    const [selectedUserRowKeys, setSelectedUserRowKeys] = useState<React.Key[]>([]);
    const rowSelectionProps = {
        selectedRowKeys: selectedUserRowKeys,
        onChange: (newSelectedRowKeys: React.Key[]) => {
            if (newSelectedRowKeys.length > 0) {
                setIsVisible(true);
            } else {
                setIsVisible(false);
            }
            setSelectedUserRowKeys(newSelectedRowKeys)
        }
    }
    // 清空管理列表中选中的
    const clearSelectedOnClick = () => {
        setIsVisible(false);
        // console.log(isVisible)
        setSelectedUserRowKeys([]);
    }

    // 管理页面的批量操作
    // 批量封号按钮回调
    const batchSealedAccountOnConfirm = () => {
        const batchSealedAccount = async () => {
            try {
                const res = await batchSealedAccountAPI(selectedUserRowKeys);
                if (res.data.data === true) {
                    dispatch(setPageForAListOfUsersFlush());
                    messageApi.open({
                        type: 'success',
                        content: `批量封号以完成`
                    })
                } else {
                    messageApi.open({
                        type: 'warning',
                        content: '批量操作失败，请选择能进行相应操作的用户'
                    })
                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: '出错了，请联系工作人员'
                })
            }
        }
        // 发请求批量封号
        batchSealedAccount();
    }
    // 批量违规按钮回调
    const batchIllegalAccountOnConfirm = () => {
        const batchIllegalAccount = async () => {
            try {
                const res = await batchIllegalAccountAPI(selectedUserRowKeys);
                if (res.data.data === true) {
                    dispatch(setPageForAListOfUsersFlush());
                    messageApi.open({
                        type: 'success',
                        content: `批量违规以完成`
                    })
                } else {
                    messageApi.open({
                        type: 'warning',
                        content: '批量操作失败，请选择能进行相应操作的用户'
                    })
                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: '出错了，请联系工作人员'
                })
            }
        }
        // 发请求批量违规
        batchIllegalAccount();
    }
    // 批量解封按钮回调
    const batchUnblockTheAccountOnConfirm = () => {
        const batchUnblockTheAccount = async () => {
            try {
                const res = await batchUnblockTheAccountAPI(selectedUserRowKeys);
                if (res.data.data === true) {
                    dispatch(setPageForAListOfUsersFlush());
                    messageApi.open({
                        type: 'success',
                        content: `批量解封以完成`
                    })
                } else {
                    messageApi.open({
                        type: 'warning',
                        content: '批量操作失败，请选择能进行相应操作的用户'
                    })
                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: '出错了，请联系工作人员'
                })
            }
        }
        // 发请求批量解封
        batchUnblockTheAccount();
    }
    // 批量解除违规按钮回调
    const batchCancelTheIllegalAccountOnConfirm = () => {
        const batchCancelTheIllegalAccount = async () => {
            try {
                const res = await batchIllegalAccountAPI(selectedUserRowKeys);
                if (res.data.data === true) {
                    dispatch(setPageForAListOfUsersFlush());
                    messageApi.open({
                        type: 'success',
                        content: `批量解除违规以完成`
                    })
                } else {
                    messageApi.open({
                        type: 'warning',
                        content: '批量操作失败，请选择能进行相应操作的用户'
                    })
                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: '出错了，请联系工作人员'
                })
            }
        }
        // 发请求批量解除违规
        batchCancelTheIllegalAccount();
    }

    return (
        <>
            {contextHolder}
            <div className={"chenyun-user"}>
                <Flex vertical={true} align={"center"} gap={"large"}>
                    <Content className={"chenyun-user-title"}>
                        <Flex align={"center"} style={{height: '50px'}}>
                            <div>用户管理</div>
                        </Flex>
                    </Content>
                    <Content className={"chenyun-user-content"}>
                        <Flex  vertical={true} style={{paddingTop:'24px', paddingLeft:'20px', paddingRight:'20px'}}>
                            <div className={"chenyun-user-content-search"}>
                                <Flex gap={"large"}>
                                    <Flex align={"center"}>
                                        <div className={"chenyun-user-content-search-username"}>用户昵称:</div>
                                        <Input placeholder={"请输入用户名或用户ID"} style={{width: '12vw'}}
                                               value={searchValue} onChange={searchValueOnChange}
                                               disabled={location.pathname === '/user/audit/info' ? true : false}
                                        />
                                    </Flex>
                                    <Flex align={"center"} style={{marginRight:'30px'}}>
                                        <div className={"chenyun-user-content-search-status"}>状态:</div>
                                        <Select size={"middle"} style={{width:'100px'}} placeholder={"请选择"} options={[
                                            { value: 0, label: '封号' },
                                            { value: 1, label: '违规' },
                                            { value: 2, label: '正常'},
                                        ]} value={statusValue} onChange={statusValueOnChange} disabled={currentRoute === '2' ? true : false}/>
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
                                        <Button onClick={searchButtonOnClick} disabled={location.pathname === '/user/audit/info' ? true : false}>查询</Button>
                                    </ConfigProvider>
                                    <Button onClick={clearSearchButtonOnClick} >清空</Button>
                                </Flex>
                            </div>
                            <div className={"chenyun-user-content-rout"}>
                                <Menu onClick={currentRouteOnClick} selectedKeys={[currentRoute]} mode="horizontal" items={items} />
                            </div>
                            {currentRoute == '1' && <div>
                                <div className={"chenyun-user-content-option"}>
                                    <Flex gap={"large"}>
                                        <Popconfirm title={"确定批量封号吗，这是一个风险操作"} onConfirm={batchSealedAccountOnConfirm}>
                                            <Button disabled={ isVisible ? false : true}>批量封号</Button>
                                        </Popconfirm>
                                        <Popconfirm title={"确定要批量违规吗，这是一个风险操作"} onConfirm={batchIllegalAccountOnConfirm}>
                                            <Button disabled={ isVisible ? false : true}>批量违规</Button>
                                        </Popconfirm>
                                        <Popconfirm title={"确定要批量解封吗？"} onConfirm={batchUnblockTheAccountOnConfirm}>
                                            <Button disabled={ isVisible ? false : true}>批量解封</Button>
                                        </Popconfirm>
                                        <Popconfirm title={"确定要批量解除违规吗？"} onConfirm={batchCancelTheIllegalAccountOnConfirm}>
                                            <Button disabled={ isVisible ? false : true}>批量解除违规</Button>
                                        </Popconfirm>
                                    </Flex>
                                </div>
                                <div className={`chenyun-user-content-tips ${isVisible ? 'visible' : ''}`}>
                                    <Flex align={"center"} style={{height:'4vh'}}>
                                        <svg className={"chenyun-user-content-tips-svg"} aria-hidden={"true"} width={'25px'} height={'25px'}>
                                            <use xlinkHref={'#icon-jinggao'}></use>
                                        </svg>
                                        <div style={{paddingLeft:'5px'}}>已选择 {selectedUserRowKeys.length} 项，点击<span style={{color:'rgba(255, 165, 0)', fontWeight:"bold", cursor:"pointer"}} onClick={clearSelectedOnClick}> 清空</span></div>
                                    </Flex>
                                </div>
                            </div>}
                            <div className={"chenyun-user-content-list"}>
                                {currentRoute == '1' && !location.search && <UserList rowSelection={rowSelectionProps} />}
                                {currentRoute == '1' && location.search && location.search.split('?')[1].includes("search") && <SearchUserList rowSelection={rowSelectionProps} />}
                                {currentRoute == '2' && location.search && location.search.split('?')[1].includes("search") && <SearchAuditUserList />}
                                {currentRoute == '2' && !location.search && <Outlet />}
                                {currentRoute == '2' && location.search && location.search.split('?')[1].includes("user") && <Outlet />}
                            </div>
                        </Flex>
                    </Content>
                    <Content className={"chenyun-user-author"}>
                        <Flex style={{height:'5vh'}} align={"center"}>
                            <div>Copyright © 2024 爱吃小鱼的橙子出品 <a href={"https://github.com/programmerChenYu/chengyun"} target={'_blank'}>chenyun.com</a></div>
                        </Flex>
                    </Content>
                </Flex>
            </div>
        </>
    )
}

export default UserManagement;
