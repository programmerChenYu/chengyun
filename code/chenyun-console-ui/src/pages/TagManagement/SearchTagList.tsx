import {
    Badge,
    ConfigProvider,
    Flex, message,
    PaginationProps, Popconfirm,
    Table,
    TableColumnsType,
    TablePaginationConfig,
} from "antd";
import React, {useEffect, useState} from "react";
import {TagInterface} from "../../interface";
import './TagList.css'
import {useAppDispatch, useAppSelector} from "../../store/hooks.ts";
import {
    deactivateTagAPI,
    deleteTagAPI,
    enableTagAPI,
    pagingQuerySearchTagListAPI
} from "../../apis/TagManagement/tagOption.tsx";
import {
    setSearchTagListCountOfPage,
    setSearchTagListCurrentPage,
    setSearchTagListData,
    setSearchTagListFlush
} from "../../store/modules/searchTagListSlice.tsx";

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

const SearchTagList: React.FC<TableComponentProps> = ({rowSelection}) => {

    // 全局提示
    const [messageApi, contextHolder] = message.useMessage();

    const searchTagList = useAppSelector(state => state.searchTagList);
    const dispatch = useAppDispatch();

    const [loading, setLoading] = useState<boolean>(true);

    // 启用/停用按钮的确认回调
    const enableOrDeactivateOnConfirm = (record: TagInterface) => {
        // 发请求启用/停用
        if (record.tagStatus === 1) {
            // 标签已经启用，所以要停用该标签
            const deactivateTag = async () => {
                try {
                    const res = await deactivateTagAPI(String(record.key));
                    if (res.data.data === true) {
                        messageApi.open({
                            type: 'success',
                            content: `标签【${record.tagName}】已停用`
                        })
                    } else {
                        messageApi.open({
                            type: 'warning',
                            content: '标签停用失败，请稍后重试'
                        })
                    }
                } catch (error) {
                    messageApi.open({
                        type: "error",
                        content: '出错了，请联系工作人员'
                    })
                }
            }
            deactivateTag().then(() => {
                dispatch(setSearchTagListFlush());
            })
        } else {
            // 标签已经停用，所以要启用该标签
            const enableTag = async () => {
                try {
                    const res = await enableTagAPI(String(record.key));
                    if (res.data.data === true) {
                        messageApi.open({
                            type: 'success',
                            content: `标签【${record.tagName}】已启用`
                        })
                    } else {
                        messageApi.open({
                            type: 'warning',
                            content: '标签停用失败，请稍后重试'
                        })
                    }
                } catch (error) {
                    messageApi.open({
                        type: "error",
                        content: '出错了，请联系工作人员'
                    })
                }
            }
            enableTag().then(() => {
                dispatch(setSearchTagListFlush())
            })
        }
    }
    // 删除按钮确认回调
    const deleteOnConfirm = (record: TagInterface) => {
        // 发请求删除该标签
        const deleteTag = async () => {
            try {
                const res = await deleteTagAPI(String(record.key));
                if (res.data.data === true) {
                    messageApi.open({
                        type: 'success',
                        content: `标签【${record.tagName}】已删除`
                    })
                } else {
                    messageApi.open({
                        type: 'warning',
                        content: '标签停用失败，请稍后重试'
                    })
                }
            } catch (error) {
                messageApi.open({
                    type: "error",
                    content: '出错了，请联系工作人员'
                })
            }
        }
        deleteTag().then(() => {
            dispatch(setSearchTagListFlush());
        })
    }


    const columns: TableColumnsType<TagInterface> = [
        {
            title: '标签名',
            dataIndex: 'tagName',
            align: "center",
            width: '20vw'
        },
        {
            title: '状态',
            dataIndex: 'tagStatus',
            align: "center",
            width: '10vw',
            render: (value) => <div>
                {value == 0 ?
                    <Badge color={"#FF0000FF"} text={"停用"}/> :
                    <Badge color={"#008000FF"} text={"启用"} />}
            </div>
        },
        {
            title: '创建人',
            dataIndex: 'creator',
            align: "center",
            width: '15vw'
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            align: "center",
            width: '15vw'
        },
        {
            title: '操作',
            align: "center",
            dataIndex: 'tagStatus',
            width: '20vw',
            render: (value, record: TagInterface) => <div>
                <Flex justify={"center"} gap={"middle"}>
                    <Popconfirm title={`你确认要${value == 0 ? '启用' : '停用'}吗？`}
                                onConfirm={() => enableOrDeactivateOnConfirm(record)}>
                        {value == 0 ?
                            <div className={"chenyun-tag-content-list-option"}>启用</div> :
                            <div className={"chenyun-tag-content-list-option"}>停用</div>}
                    </Popconfirm>
                    <Popconfirm title={"你确认要删除吗？"} onConfirm={() => deleteOnConfirm(record)}>
                        <div className={"chenyun-tag-content-list-option"}>删除</div>
                    </Popconfirm>
                </Flex>
            </div>
        }
    ];

    const paginations: TablePaginationConfig = {
        showQuickJumper: true,
        current: searchTagList.currentPage,
        total: searchTagList.countOfPage,
        onChange: (pageNumber) => {
            dispatch(setSearchTagListCurrentPage(pageNumber));
            dispatch(setSearchTagListFlush());
        },
        pageSize: 7,
        hideOnSinglePage: true,
        showSizeChanger: false,
        showTitle: true,
        itemRender: itemRender,
        showTotal: (total) => <div style={{paddingTop: '2px'}}>{`共 ${total} 个标签`}</div>,
    }

    useEffect(() => {
        const searchParams = new URLSearchParams(location.search);
        const pagingQueryTagList = async () => {
            try {
                console.log(searchParams.get("search"),"::::",searchParams.get("status"))
                const res = await pagingQuerySearchTagListAPI(searchParams.get("search"), searchParams.get("status"), searchTagList.currentPage, 7);
                dispatch(setSearchTagListData(res.data.data.tagList));
                dispatch(setSearchTagListCountOfPage(res.data.data.countOfPage));
            } catch (error) {
            }
        }

        pagingQueryTagList().then(() => {
            setLoading(false);
        });

    }, [searchTagList.flush])

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
                    <Table<TagInterface>
                        rowSelection={{ type: "checkbox", ...rowSelection }}
                        columns={columns}
                        loading={loading}
                        dataSource={searchTagList.data}
                        pagination={paginations}
                    />
                </ConfigProvider>
            </div>
        </>
    )
}

export default SearchTagList;
