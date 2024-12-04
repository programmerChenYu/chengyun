import {ConfigProvider, Flex, message, PaginationProps, Table, TableColumnsType, TablePaginationConfig} from "antd";
import {UserInterface} from "../../interface";
import React, {useEffect, useState} from "react";
import {Outlet, useNavigate} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "../../store/hooks.ts";
import {
    setSearchUserInfoAuditListCurrentPage, setSearchUserInfoAuditListData,
    setSearchUserInfoAuditListFlush, setSearchUserInfoAuditListTotalPage
} from "../../store/modules/searchUserInfoAuditListSlice.tsx";
import {getSearchUserInfoAuditListAPI} from "../../apis/UserManagement/getSearchUserInfoAuditListAPI.tsx";
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

const SearchAuditUserList = () => {

    // 全局消息弹框
    const [messageApi, contextHolder] = message.useMessage();

    const navigate = useNavigate();
    // 当前的路由路径
    // const path = useLocation().pathname;

    // 加载中
    const [loading, setLoading] = useState<boolean>(true);

    const searchUserInfoAuditList = useAppSelector(state => state.searchUserInfoAuditList);
    const dispatch = useAppDispatch();

    const auditOnClick = (record: UserInterface) => {
        // TODO 此处做RSA加密处理
        dispatch(setPath(location.pathname+location.search));
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

    const paginations: TablePaginationConfig = {
        showQuickJumper: true,
        current: searchUserInfoAuditList.currentPage,
        total: searchUserInfoAuditList.totalPage,
        onChange: (pageNumber) => {
            dispatch(setSearchUserInfoAuditListCurrentPage(pageNumber));
            // 刷新页面
            dispatch(setSearchUserInfoAuditListFlush());
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
            // console.log(`selectedRowKeys: ${newSelectedRowKeys}`, 'selectedRows: ', selectedRows: UserInterface[]);
        }
    }

    useEffect(() => {
        const searchParams = new URLSearchParams(location.search);
        // 获取搜索的待审核用户列表
        const getSearchUserInfoAuditList = async () => {
            try {
                setLoading(true);
                // @ts-ignore
                const res = await getSearchUserInfoAuditListAPI(searchParams.get("search"), searchUserInfoAuditList.currentPage, 8);
                dispatch(setSearchUserInfoAuditListData(res.data.data));
                if (res.data.data.length > 0) {
                    dispatch(setSearchUserInfoAuditListTotalPage(res.data.data[0].countOfPage))
                } else {
                    dispatch(setSearchUserInfoAuditListTotalPage(0))
                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: `出错了，请联系工作人员`
                })
            }
        }

        getSearchUserInfoAuditList().then(() => {
            setLoading(false);
        })
    }, [searchUserInfoAuditList.flush])

    return (
        <>
            {contextHolder}
            <div>
                {location.search.includes("search") ?
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
                            dataSource={searchUserInfoAuditList.data}
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

export default SearchAuditUserList;
