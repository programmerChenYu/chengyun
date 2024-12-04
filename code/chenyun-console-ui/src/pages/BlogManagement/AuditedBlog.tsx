import './AuditedBlog.css'
import {ConfigProvider, Flex, PaginationProps, Popconfirm, Table, TableColumnsType, TablePaginationConfig} from "antd";
import {BlogPostInterface} from "../../interface";
import React, {useEffect, useState} from "react";
import {useAppDispatch, useAppSelector} from "../../store/hooks.ts";
import {useLocation, useNavigate} from "react-router-dom";
import {setPath} from "../../store/modules/historicalPathSlice.tsx";
import {
    setBlogListCountOfPage,
    setBlogListCurrentPage,
    setBlogListData,
    setBlogListFlush
} from "../../store/modules/blogListSlice.tsx";
import {pagingQueryAuditedArticleListAPI} from "../../apis/BlogManagement/pagingQueryAuditedArticleListAPI.tsx";

const itemRender: PaginationProps['itemRender'] = (_, type, originalElement) => {
    if (type === 'prev') {
        return <div>{'<<'}</div>;
    }
    if (type === 'next') {
        return <div>{'>>'}</div>;
    }
    return originalElement;
};

const AuditedBlog = () => {

    const dispatch = useAppDispatch();
    const blogList = useAppSelector(state => state.blogList);
    const location = useLocation();
    const navigate = useNavigate();

    const [loading, setLoading] = useState<boolean>(true);

    // 点击查看按钮触发的回调
    const viewOnClick = (key: string) => {
        dispatch(setPath(location.pathname));
        navigate(`/blog-info?article=${encodeURIComponent(key)}`);
    }

    const columns: TableColumnsType<BlogPostInterface> = [
        {
            title: '文章ID',
            dataIndex: 'key',
            align: "center",
            width: '4vw'
        },
        {
            title: '文章题目',
            dataIndex: 'title',
            align: "center",
            width: '20vw',
        },
        {
            title: '作者',
            dataIndex: 'author',
            align: "center",
            width: '15vw'
        },
        {
            title: '文章提交时间',
            dataIndex: 'updatedAt',
            align: "center",
            width: '15vw'
        },
        {
            title: '操作',
            align: "center",
            width: '10vw',
            render: (record: BlogPostInterface) => <Flex justify={"center"} gap={"middle"}>
                <Flex justify={"center"} gap={"middle"} style={{width: '10vw'}}>
                    <div className={"chenyun-blog-content-list-option-audited"} onClick={() => viewOnClick(String(record.key))}>查看</div>
                    <Popconfirm title={"确定要下架该文章吗，这是一个风险操作"}>
                        <div className={"chenyun-blog-content-list-option-audited"}>下架</div>
                    </Popconfirm>
                </Flex>
            </Flex>
        }
    ];

    // 管理表格中的选中行的数据
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);
    const rowSelectionProps = {
        selectedRowKeys: selectedRowKeys,
        onChange: (newSelectedRowKeys: React.Key[]) => {
            setSelectedRowKeys(newSelectedRowKeys);
        }
    }

    const paginations: TablePaginationConfig = {
        showQuickJumper: true,
        current: blogList.currentPage,
        total: blogList.countOfPage,
        onChange: (pageNumber) => {
            dispatch(setBlogListCurrentPage(pageNumber));
            dispatch(setBlogListFlush());
        },
        pageSize: 8,
        hideOnSinglePage: true,
        showSizeChanger: false,
        showTitle: true,
        itemRender: itemRender,
        showTotal: (total) => <div style={{paddingTop: '2px'}}>{`共 ${total} 篇博文`}</div>,
    }

    useEffect(() => {
        const pagingQueryAuditedArticleList = async () => {
            try {
                const res = await pagingQueryAuditedArticleListAPI(blogList.currentPage, 8);
                dispatch(setBlogListData(res.data.data.blogPostList));
                dispatch(setBlogListCountOfPage(res.data.data.countOfPage));
            } catch (error) {}
        }

        pagingQueryAuditedArticleList().then(() => {
            setLoading(false);
        });
    }, [blogList.flush])

    return (
        <>
            <div>
                <ConfigProvider theme={{
                    components: {
                        Table: {
                            rowSelectedBg: 'rgba(234,157,59,0.1)',
                            rowSelectedHoverBg: 'rgba(234,157,59,0.4)'
                        }
                    }
                }}>
                    <Table<BlogPostInterface>
                        rowSelection={{ type: "radio", ...rowSelectionProps}}
                        columns={columns}
                        loading={loading}
                        dataSource={blogList.data}
                        pagination={paginations}
                    />
                </ConfigProvider>
            </div>
        </>
    )
}

export default AuditedBlog;
