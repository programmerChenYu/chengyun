import './index.css';
import '@wangeditor/editor/dist/css/style.css'
import {
    Anchor,
    Button,
    Flex,
    Input,
    message,
    Modal,
    Popconfirm,
    Spin
} from "antd";
import {LoadingOutlined, SwapLeftOutlined} from "@ant-design/icons";
import {useNavigate} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import {useAppDispatch, useAppSelector} from "../../../store/hooks.ts";
import './prism.css';
import Prism from 'prismjs';
import {setBlogInfoContent} from "../../../store/modules/blogInfoSlice.tsx";
import {getBlogInfoAPI} from "../../../apis/BlogManagement/BlogInfo/getBlogInfoAPI.tsx";
import {blogInfoProcessAPI, blogInfoRefuseAPI} from "../../../apis/BlogManagement/BlogInfo/blogInfoOption.tsx";

interface AnchorItem {
    key: string,
    href: string,
    title: string,
    children: AnchorItem[]
}

const BlogInfo = () => {

    const {TextArea} = Input;

    const navigate = useNavigate();

    // 获取历史路径
    // const historicalPath = useAppSelector(state => state.historicalPath.path);

    // 判断是否是已审核文章(默认不是)
    const [auditedArticle, setAuditedArticle] = useState<boolean>(false);

    // 全局提示
    const [messageApi, contextHolder] = message.useMessage();

    // 页面待加载
    const [pageLoading, setPageLoading] = useState<boolean>(true);

    // 指定的文章
    const blogInfo = useAppSelector(state => state.blogInfo);
    const dispatch = useAppDispatch();

    // 返回按钮点击后的回调
    const backButtonOnClick = () => {
        // 区分从哪个页面点击来的，依次跳回对应页面
        if (auditedArticle) {
            navigate("/blog/audited")
        } else {
            navigate("/blog/reviewed")
        }
        // if (historicalPath === '/blog/reviewed') {
        //     navigate("/blog/reviewed");
        // } else if (historicalPath === '/blog/audited') {
        //     navigate("/blog/audited");
        // } else {
        //     navigate("/blog");
        // }
    }

    // 不通过按钮点击后触发的窗口
    const [open, setOpen] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(true);
    const showLoading = () => {
        setOpen(true);
        setLoading(true);

        setTimeout(() => {
            setLoading(false);
        }, 500);
    };

    // 不通过模块中确定按钮触发的回调
    const refuseConfirmOnClick = () => {
        const params = new URLSearchParams(location.search);
        const blogInfoRefuse = async () => {
            try {
                await blogInfoRefuseAPI(params.get("article"));
            } catch (error) {
                navigate("/error");
            }
        }

        blogInfoRefuse().then(() => {
            messageApi.open({
                type: "success",
                content: "操作完成"
            })
            setTimeout(() => {
                setOpen(false);
                navigate("/blog/reviewed");
            }, 500)
        })

    }
    // 不通过模块中取消按钮触发的回调
    const refuseCancelOnClick = () => {
        setOpen(false);
    }


    // 通过按钮点击后二次确认触发的回调
    const processOnConfirm = () => {
        const params = new URLSearchParams(location.search);
        const blogInfoProcess = async () => {
            try {
                await blogInfoProcessAPI(params.get("article"));
            } catch (error) {
                navigate("/error");
            }
        }

        blogInfoProcess().then(() => {
            messageApi.open({
                type: "success",
                content: "操作完成"
            })
            setTimeout(() => {
                navigate("/blog/reviewed");
            }, 700)
        })
    }

    const contentView = useRef<HTMLDivElement>(null);

    const items: AnchorItem[] = [];

    useEffect(() => {
        const params = new URLSearchParams(location.search);
        const getBlogInfo = async () => {
            try {
                const res = await getBlogInfoAPI(params.get("article"));
                dispatch(setBlogInfoContent(res.data.data.content));
                if (res.data.data.status === 2) {
                    setAuditedArticle(true);
                }
            } catch (error) {
                navigate("/error");
            }
        }

        getBlogInfo().then(() => {
            setPageLoading(false);
        });


    }, [])

    useEffect(() => {
        // 渲染页面
        if (!pageLoading) {
            const current = contentView.current;
            if (current) {
                if (blogInfo.content != undefined) {
                    current.innerHTML = blogInfo.content;
                }
                Prism.highlightAll();

            }

            const parser = new DOMParser();
            // 解析HTML字符串为DOM文档
            if (blogInfo.content) {
                const doc = parser.parseFromString(blogInfo.content, 'text/html');
                // 遍历所有的h标签
                const headings = doc.querySelectorAll('h1, h2, h3, h4, h5, h6');
                headings.forEach((heading) => {
                    if (heading.tagName === 'H1') {
                        items.push({
                            key: String(heading.id),
                            href: `#${heading.id}`,
                            title: String(heading.innerHTML),
                            children: []
                        })
                    } else if (heading.tagName === 'H2') {
                        items[items.length-1].children.push({
                            key: String(heading.id),
                            href: `#${heading.id}`,
                            title: String(heading.innerHTML),
                            children: []
                        })
                    } else if (heading.tagName === 'H3') {
                        items[items.length-1].children[items[items.length-1].children.length-1].children.push({
                            key: String(heading.id),
                            href: `#${heading.id}`,
                            title: String(heading.innerHTML),
                            children: []
                        })
                    }
                })
            }
        }
    }, [pageLoading])

    return (
        <>
            {contextHolder}
            <div className={"chenyun-blog-info"}>
                <Flex vertical={true} align={"center"} gap={"large"}>
                    {pageLoading ?
                        <Flex align={"center"} style={{height: '60vh',}}>
                            <Spin indicator={<LoadingOutlined style={{ fontSize: 80, fontWeight: 'bold' }} spin />} />
                        </Flex> :
                        <Flex>
                            <Flex vertical={true}>
                                <div style={{height:'3vh', backgroundColor:'#FFFFFFFF', paddingLeft:'10px', paddingTop:'8px'}}>
                                    <Flex style={{height:'30px'}} align={"center"}>
                                        <SwapLeftOutlined className={"chenyun-blog-info-back"} onClick={backButtonOnClick} />
                                    </Flex>
                                </div>
                                <div id={'editor-content-view'} className={'editor-content-view'} ref={contentView} style={{height: '82vh',width: '78vw', backgroundColor:'white', paddingLeft: '20px', paddingRight: '20px', overflow: 'auto'}}>
                                </div>
                            </Flex>
                            <div className={"chenyun-blog-info-right"}>
                                <Flex vertical={true} justify={"space-between"} style={{height: '85vh'}}>
                                    <Anchor style={{height: "700px", overflow: 'auto'}}
                                            onClick={(e, link) => {
                                                e.preventDefault();
                                                const element = document.getElementById(link.href.slice(1));
                                                if (element) {
                                                    element.scrollIntoView({behavior:'smooth'})
                                                }
                                            }}
                                            items={items}
                                            getContainer={() => document.getElementById('editor-content-view') as HTMLElement}
                                            targetOffset={20}
                                    />
                                    {!auditedArticle && <Flex gap={"middle"} style={{paddingBottom:'10px'}}>
                                        <Button danger onClick={showLoading}>不通过</Button>
                                        <Modal
                                            title={<p>不通过理由</p>}
                                            footer={
                                                <Flex justify={"flex-end"} gap={"large"}>
                                                    <Button onClick={refuseCancelOnClick}>
                                                        取消
                                                    </Button>
                                                    <Button type="primary" onClick={refuseConfirmOnClick}>
                                                        确定
                                                    </Button>
                                                </Flex>
                                            }
                                            loading={loading}
                                            centered
                                            open={open}
                                            onCancel={() => setOpen(false)}
                                        >
                                            <TextArea placeholder={"请输入不通过的理由，让用户知道该如何修改"} autoSize={{minRows: 4, maxRows: 10}} />
                                        </Modal>
                                        <Popconfirm title={"确定该文章通过审核吗？"} onConfirm={processOnConfirm}>
                                            <Button type={"primary"}>通过</Button>
                                        </Popconfirm>
                                    </Flex>}
                                </Flex>
                            </div>
                        </Flex>
                    }
                    <div className={"chenyun-blog-info-author"}>
                        <Flex style={{height:'5vh'}} align={"center"}>
                            <div>Copyright © 2024 爱吃小鱼的橙子出品 <a href={"https://github.com/programmerChenYu/chengyun"} target={'_blank'}>chenyun.com</a></div>
                        </Flex>
                    </div>
                </Flex>
            </div>
        </>
    );
}

export default BlogInfo;
