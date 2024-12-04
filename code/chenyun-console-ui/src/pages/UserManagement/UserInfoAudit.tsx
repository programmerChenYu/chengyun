import {ConfigProvider, Flex, PaginationProps, Table, TableColumnsType, TablePaginationConfig} from "antd";
import {UserInterface} from "../../interface";
import React, {useEffect, useState} from "react";
import {Outlet, useLocation, useNavigate} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "../../store/hooks.ts";
import {
    setUserInfoAuditListCurrentPage,
    setUserInfoAuditListData, setUserInfoAuditListFlush
} from "../../store/modules/userInfoAuditListSlice.tsx";
import {getNumberOfUserInfoAuditAPI} from "../../apis/UserManagement/getNumberOfUserInfoAuditAPI.tsx";
import {getUserInfoAuditListAPI} from "../../apis/UserManagement/getUserInfoAuditListAPI.tsx";
import {setPath} from "../../store/modules/historicalPathSlice.tsx";
import {rsaEncrypt} from "../../utils";

const itemRender: PaginationProps['itemRender'] = (_, type, originalElement) => {
    if (type === 'prev') {
        return <div>{'<<'}</div>;
    }
    if (type === 'next') {
        return <div>{'>>'}</div>;
    }
    return originalElement;
};

const UserInfoAudit = () => {

    const navigate = useNavigate();
    // 当前的路由路径
    const path = useLocation().pathname;

    // 列表加载
    const [loading, setLoading] = useState<boolean>(true);

    const userInfoAuditList = useAppSelector(state => state.userInfoAuditList);
    const dispatch = useAppDispatch();

    const auditOnClick = (record: UserInterface) => {
        // TODO 此处做RSA加密处理
        dispatch(setPath(`/user/audit`));
        navigate(`/user/audit/info?user=${encodeURIComponent(rsaEncrypt.encrypt(String(record.key)))}`)
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
            width: '15vw'
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
            width: '10vw',
            render: (record: UserInterface) => <div>
                <Flex justify={"center"} gap={"middle"}>
                    <div className={"chenyun-user-content-list-option"} onClick={() => auditOnClick(record)}>审核</div>
                </Flex>
            </div>
        }
    ];

    const [pageTotal, setPageTotal] = useState<number>()
    const paginations: TablePaginationConfig = {
        showQuickJumper: true,
        current: userInfoAuditList.currentPage,
        total: pageTotal,
        onChange: (pageNumber) => {
            dispatch(setUserInfoAuditListCurrentPage(pageNumber));
            // 刷新页面
            dispatch(setUserInfoAuditListFlush());
        },
        pageSize: 8,
        hideOnSinglePage: true,
        showSizeChanger: false,
        showTitle: true,
        itemRender: itemRender,
        showTotal: (total) => <div style={{paddingTop: '2px'}}>{`共 ${total} 个用户`}</div>,
    }

    // 管理表格中的选中行的数据
    const [selectedAuditRowKeys, setSelectedAuditRowKeys] = useState<React.Key[]>([]);
    const rowSelectionProps = {
        selectedRowKeys: selectedAuditRowKeys,
        onChange: (newSelectedRowKeys: React.Key[]) => {
            setSelectedAuditRowKeys(newSelectedRowKeys);
        }
    }

    useEffect(() => {
        // 获取待审核用户列表总数
        const getNumberOfUserInfoAudit = async () => {
            try {
                const res = await getNumberOfUserInfoAuditAPI();
                setPageTotal(res.data.data);
            } catch (error) {
                navigate("/error")
            }
        }
        // 获取待审核用户列表
        const getUserInfoAuditList = async () => {
            try {
                setLoading(true);
                // @ts-ignore
                const res = await getUserInfoAuditListAPI(userInfoAuditList.currentPage, 8);
                dispatch(setUserInfoAuditListData(res.data.data));
            } catch (error) {
                navigate("/error")
            }
        }

        getNumberOfUserInfoAudit();
        getUserInfoAuditList().then(() => {
            setLoading(false);
        });
    }, [userInfoAuditList.flush])

    return (
        <>
            <div>
                {path === '/user/audit' ?
                    <ConfigProvider theme={{
                        components: {
                            Table: {
                                rowSelectedBg: 'rgba(234,157,59,0.1)',
                                rowSelectedHoverBg: 'rgba(234,157,59,0.4)'
                            }
                        }
                    }}>
                        <Table<UserInterface>
                            rowSelection={{type:'radio', ...rowSelectionProps }}
                            columns={columns}
                            dataSource={userInfoAuditList.data}
                            loading={loading}
                            pagination={paginations}
                        />
                    </ConfigProvider> :
                    <Outlet />
                }
            </div>
        </>
    )
}

export default UserInfoAudit;
