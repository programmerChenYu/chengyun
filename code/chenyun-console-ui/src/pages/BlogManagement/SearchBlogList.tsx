import './BlogList.css'
import {
    Badge,
    ConfigProvider,
    Flex,
    PaginationProps,
    Popconfirm,
    Table,
    TableColumnsType,
    TablePaginationConfig
} from "antd";
import {BlogPostInterface} from "../../interface";
import React, {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "../../store/hooks.ts";
import {setPath} from "../../store/modules/historicalPathSlice.tsx";
import {
    setBlogListCountOfPage,
    setBlogListCurrentPage,
    setBlogListData,
    setBlogListFlush
} from "../../store/modules/blogListSlice.tsx";
import {pagingQuerySearchArticleListAPI} from "../../apis/BlogManagement/pagingQuerySearchArticleListAPI.tsx";

const itemRender: PaginationProps['itemRender'] = (_, type, originalElement) => {
    if (type === 'prev') {
        return <div>{'<<'}</div>;
    }
    if (type === 'next') {
        return <div>{'>>'}</div>;
    }
    return originalElement;
};

const SearchBlogList = () => {

    const navigate = useNavigate();
    const location = useLocation();

    const dispatch = useAppDispatch();
    const blogList = useAppSelector(state => state.blogList);

    const [loading, setLoading] = useState<boolean>(true)

    // 点击审核和查看跳转到文章详情
    const viewBlogOnClick = (e: React.MouseEvent<HTMLDivElement>, key: string) => {
        // @ts-ignore
        const textContent = e.target.textContent;
        if (textContent === '审核') {
            dispatch(setPath('/blog/reviewed'));
        } else if (textContent === '查看') {
            dispatch(setPath('/blog/audited'));
        } else {
            dispatch((setPath(location.pathname)));
        }
        navigate(`/blog-info?article=${encodeURIComponent(key)}`);
    }

    // 下架按钮触发的回调
    const articleRemovalOnConfirm = (record: BlogPostInterface) => {
        console.log(record)
        dispatch(setBlogListFlush());
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
            title: '状态',
            dataIndex: 'status',
            align: "center",
            width: '6vw',
            render: (value) => <div>
                {value == 1 ?
                    <Badge color={"#FF0000FF"} text={"待审核"}/> :
                    <Badge color={"#008000FF"} text={"已公开"} />}
            </div>
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
            render: (record: BlogPostInterface) => <div>
                <Flex justify={"center"} gap={"middle"}>
                    {record.status == 1 ? <Flex justify={"center"} gap={"middle"} style={{width: '10vw'}}>
                        <div className={"chenyun-blog-content-list-option"} onClick={(e) => viewBlogOnClick(e, String(record.key))}>审核</div>
                    </Flex> : <Flex justify={"center"} gap={"middle"} style={{width: '10vw'}}>
                        <div className={"chenyun-blog-content-list-option"} onClick={(e) => viewBlogOnClick(e, String(record.key))}>查看</div>
                        <Popconfirm title={"确定下架该文章吗，这是一个风险操作"} onConfirm={() => articleRemovalOnConfirm(record)}>
                            <div className={"chenyun-blog-content-list-option"}>下架</div>
                        </Popconfirm>
                    </Flex>}
                </Flex>
            </div>
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
        const params = new URLSearchParams(location.search);
        const pagingQueryArticleList = async () => {
            try {
                const res = await pagingQuerySearchArticleListAPI(params.get("search"), params.get("status"), blogList.currentPage, 8);
                dispatch(setBlogListData(res.data.data.blogPostList));
                dispatch(setBlogListCountOfPage(res.data.data.countOfPage));
            } catch (error) {
                navigate("/error")
            }
        }

        pagingQueryArticleList().then(() => {
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
    );
}

export default SearchBlogList;
