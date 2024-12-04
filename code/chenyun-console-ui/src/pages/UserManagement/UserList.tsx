import {
    Badge,
    ConfigProvider,
    Flex, message,
    PaginationProps, Popconfirm,
    Table,
    TableColumnsType,
    TablePaginationConfig
} from "antd";
import React, {useEffect, useState} from "react";
import {UserInterface} from "../../interface";
import './UserList.css'
import {numberOfUsersAPI} from "../../apis/UserManagement/numberOfUsersAPI.tsx";
import {useAppDispatch, useAppSelector} from "../../store/hooks.ts";
import {pageForAListOfUsersAPI} from "../../apis/UserManagement/pageForAListOfUsersAPI.tsx";
import {
    setPageForAListOfUsersCurrentPage,
    setPageForAListOfUsersData, setPageForAListOfUsersFlush
} from "../../store/modules/pageForAListOfUsersSlice.tsx";
import {
    cancelTheIllegalAccountAPI,
    illegalAccountAPI,
    sealedAccountAPI,
    unblockTheAccountAPI
} from "../../apis/UserManagement/userManagementOptionAPI.tsx";
import {useNavigate} from "react-router-dom";


interface TableComponentProps {
    rowSelection: any; // 这里应该是一个更具体的类型，但 AntD 的 rowSelection 类型比较复杂，可能需要自定义一个类型来匹配
    // 其他 Table 相关的 props...
}

const itemRender: PaginationProps['itemRender'] = (_, type, originalElement) => {
    if (type === 'prev') {
        return <div>{'<<'}</div>;
    }
    if (type === 'next') {
        return <div>{'>>'}</div>;
    }
    return originalElement;
};

const UserList: React.FC<TableComponentProps> = ({rowSelection}) => {

    // 全局消息弹框
    const [messageApi, contextHolder] = message.useMessage();

    const navigate = useNavigate();

    const pageForAListOfUsers = useAppSelector(state => state.pageForAListOfUsers)
    const dispatch = useAppDispatch();

    // 用户总数
    const [totalUsers, setTotalUsers] = useState<number>();

    // 列表加载中
    const [loading, setLoading] = useState<boolean>(true);

    const paginations: TablePaginationConfig = {
        showQuickJumper: true,
        current: pageForAListOfUsers.currentPage,
        total: totalUsers,
        onChange: (pageNumber) => {
            // 设置当前页面
            dispatch(setPageForAListOfUsersCurrentPage(pageNumber))
            dispatch(setPageForAListOfUsersFlush())
        },
        pageSize: 6,
        hideOnSinglePage: true,
        showSizeChanger: false,
        showTitle: true,
        itemRender: itemRender,
        showTotal: (total) => <div style={{paddingTop: '2px'}}>{`共 ${total} 个用户`}</div>,
    }

    // 封号按钮回调
    const sealedAccountOnConfirm = (key: React.Key, nickname: string) => {
        // console.log(key)
        const sealedAccount = async () => {
            try {
                const res = await sealedAccountAPI(key);
                if (res.data.data === true) {
                    dispatch(setPageForAListOfUsersFlush());
                    messageApi.open({
                        type: 'success',
                        content: `用户 ${nickname} 已被封号`
                    })
                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: `出错了，请联系工作人员`
                })
            }
        }
        sealedAccount();
    }
    // 解除封号按钮回调
    const unblockTheAccountOnConfirm = (key: React.Key, nickname: string) => {
        const unblockTheAccount = async () => {
            try {
                const res = await unblockTheAccountAPI(key);
                if (res.data.data === true) {
                    dispatch(setPageForAListOfUsersFlush());
                    messageApi.open({
                        type: 'success',
                        content: `用户 ${nickname} 解除封号`
                    })
                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: `出错了，请联系工作人员`
                })
            }
        }
        unblockTheAccount();
    }
    // 违规按钮回调
    const illegalAccountOnConfirm = (key: React.Key, nickname: string) => {
        const illegalAccount = async () => {
            try {
                const res = await illegalAccountAPI(key);
                if (res.data.data === true) {
                    dispatch(setPageForAListOfUsersFlush());
                    messageApi.open({
                        type: 'success',
                        content: `用户 ${nickname} 已被设置违规`
                    })
                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: `出错了，请联系工作人员`
                })
            }
        }
        illegalAccount();
    }
    // 解除违规按钮回调
    const cancelTheIllegalAccountOnConfirm = (key: React.Key, nickname: string) => {
        const cancelTheIllegalAccount = async () => {
            try {
                const res = await cancelTheIllegalAccountAPI(key);
                if (res.data.data === true) {
                    dispatch(setPageForAListOfUsersFlush());
                    messageApi.open({
                        type: 'success',
                        content: `用户 ${nickname} 已被解除违规`
                    })
                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: `出错了，请联系工作人员`
                })
            }
        }
        cancelTheIllegalAccount();
    }

    const columns: TableColumnsType<UserInterface> = [
        {
            title: '用户ID',
            dataIndex: 'key',
            align: "center",
            width: '4vw'
        },
        {
            title: '用户昵称',
            dataIndex: 'nickname',
            align: "center",
            width: '15vw',
        },
        {
            title: '用户所在地',
            dataIndex: 'location',
            align: "center",
            width: '10vw'
        },
        {
            title: '状态',
            dataIndex: 'userStatus',
            align: "center",
            width: '6vw',
            render: (value) => <div>
                {value == 0 ?
                    <Badge color={"#FF0000FF"} text={"封号"}/> :
                    value == 1 ? <Badge color={"#ffcc00"} text={"违规"} /> :
                        <Badge color={"#008000FF"} text={"正常"} />}
            </div>
        },
        {
            title: '账户创建时间',
            dataIndex: 'createdAt',
            align: "center",
            width: '15vw'
        },
        {
            title: '操作',
            align: "center",
            width: '20vw',
            render: (record: UserInterface) => <div>
                <Flex justify={"center"} gap={"middle"}>
                    {record.userStatus == 0 ? <Flex justify={"center"} gap={"middle"} style={{width: '10vw'}}>
                        <Popconfirm title={"确定解除该用户的封号状态吗"} onConfirm={() => unblockTheAccountOnConfirm(record.key, record.nickname)}>
                            <div className={"chenyun-user-content-list-option"}>解除封号</div>
                        </Popconfirm>
                    </Flex> : record.userStatus == 1 ? <Flex justify={"center"} gap={"middle"} style={{width: '10vw'}}>
                        <Popconfirm title={"确定要对该用户进行封号处理吗，这是一个风险操作"} onConfirm={() => sealedAccountOnConfirm(record.key, record.nickname)}>
                            <div className={"chenyun-user-content-list-option"}>封号</div>
                        </Popconfirm>
                        <Popconfirm title={"确定解除该用户的违规状态吗"} onConfirm={() => cancelTheIllegalAccountOnConfirm(record.key, record.nickname)}>
                            <div className={"chenyun-user-content-list-option"}>解除违规</div>
                        </Popconfirm>
                    </Flex> : <Flex justify={"center"} gap={"middle"} style={{width: '10vw'}}>
                        <Popconfirm title={"确定要对该用户进行封号处理吗，这是一个风险操作"} onConfirm={() => sealedAccountOnConfirm(record.key, record.nickname)}>
                            <div className={"chenyun-user-content-list-option"}>封号</div>
                        </Popconfirm>
                        <Popconfirm title={"确定要对该用户进行违规处理吗，这是一个风险操作"} onConfirm={() => illegalAccountOnConfirm(record.key, record.nickname)}>
                            <div className={"chenyun-user-content-list-option"}>违规</div>
                        </Popconfirm>
                    </Flex>}
                </Flex>
            </div>
        }
    ];

    useEffect(() => {
        // 获取页面总数
        const getNumberOfUsers = async () => {
            try {
                const res = await numberOfUsersAPI();
                // @ts-ignore
                setTotalUsers(res.data.data)
            } catch (error) {
                navigate("/error")
            }
        }
        // 获取分页的用户列表
        const getPageForAListOfUsers = async () => {
            try {
                setLoading(true)
                // @ts-ignore
                const res = await pageForAListOfUsersAPI(pageForAListOfUsers.currentPage, 6);
                dispatch(setPageForAListOfUsersData(res.data.data));
            } catch (error) {
                navigate("/error")
            }
        }
        getNumberOfUsers();
        getPageForAListOfUsers().then(() => {
            setLoading(false);
        });
    }, [pageForAListOfUsers.flush])

    return (
        <>
            {contextHolder}
            <div>
                <ConfigProvider theme={{
                    components: {
                        Table: {
                            rowSelectedBg: 'rgba(234,157,59,0.1)',
                            rowSelectedHoverBg: 'rgba(234,157,59,0.4)'
                        }
                    }
                }}>
                    <Table<UserInterface>
                        rowSelection={{ type: "checkbox", ...rowSelection }}
                        columns={columns}
                        dataSource={pageForAListOfUsers.data}
                        pagination={paginations}
                        loading={loading}
                    />
                </ConfigProvider>
            </div>
        </>
    )
}

export default UserList;
