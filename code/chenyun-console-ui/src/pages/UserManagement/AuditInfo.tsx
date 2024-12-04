import './AuditInfo.css'
import {
    Button,
    Descriptions,
    DescriptionsProps,
    Flex,
    Image,
    Modal,
    Popconfirm,
    Input,
    Select, message, Spin
} from "antd";
import {useLocation, useNavigate} from "react-router-dom";
import {ChangeEvent, useEffect, useState} from "react";
import {useAppDispatch, useAppSelector} from "../../store/hooks.ts";
import {setUserInfoData} from "../../store/modules/userInfoSlice.tsx";
import {getUserInfoByIdAPI} from "../../apis/UserManagement/getUserInfoByIdAPI.tsx";
import {refuseUserInfoAuditAPI} from "../../apis/UserManagement/refuseUserInfoAuditAPI.tsx";
import {setUserInfoAuditListFlush} from "../../store/modules/userInfoAuditListSlice.tsx";
import {infoAuditConfirmAPI} from "../../apis/UserManagement/infoAuditConfirmAPI.tsx";
import {LoadingOutlined} from "@ant-design/icons";

const {TextArea} = Input;

const AuditInfo = () => {

    // 路由工具
    const navigate = useNavigate();

    const location = useLocation();

    const [messageApi, contextHolder] = message.useMessage();

    // 页面待加载
    const [pageLoading, setPageLoading] = useState<boolean>(true);

    const userInfo = useAppSelector(state => state.userInfo);
    const historicalPath = useAppSelector(state => state.historicalPath);
    const dispatch = useAppDispatch();

    const borderedItems: DescriptionsProps['items'] = [
        {
            key: '1',
            label: '昵称',
            children: userInfo.data && userInfo.data.nickname,
        },
        {
            key: '2',
            label: '国家地区',
            children: userInfo.data && userInfo.data.location && userInfo.data.location.split('/')[0],
        },
        {
            key: '3',
            label: '邮箱',
            children: userInfo.data && userInfo.data.email,
        },
        {
            key: '4',
            label: '所在省份',
            children: userInfo.data && userInfo.data.location && userInfo.data.location.split('/')[1],
        },
        {
            key: '5',
            label: '所在城市',
            children: userInfo.data && userInfo.data.location && userInfo.data.location.split('/')[2],
        },
        {
            key: '6',
            label: '用户状态',
            children: userInfo.data && userInfo.data.userStatus === 0 ? '封号' :
                userInfo.data && userInfo.data.userStatus === 1 ? '违规' : '正常'
        },
        {
            key: '7',
            label: '个人简介',
            children: (
                <>
                    <div id={'chenyun-user-info'} style={{height:'28vh', overflow:'auto'}}>
                        {userInfo.data && userInfo.data.information}
                    </div>
                </>
            ),
        },
    ];

    // 返回上一页
    const goBackOnClick = () => {
        navigate(String(historicalPath.path));
    }

    // 不通过按钮点击后触发的窗口
    const [open, setOpen] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(true);
    const showLoading = () => {
        setOpen(true);
        setLoading(true);

        // Simple loading mock. You should add cleanup logic in real world.
        setTimeout(() => {
            setLoading(false);
        }, 500);
    };

    // 不合格原因
    const [reasonValue, setReasonValue] = useState<string[]>()
    const handleChange = (value: string[]) => {
        setReasonValue(value);
    };
    // 不通过详细理由
    const [rejectionValue, setRejectionValue] = useState<string>()
    const inputRejectionValueOnChange = (e: ChangeEvent) => {
        setRejectionValue(e.target.innerHTML);
    }
    // 不通过模块中确定按钮触发的回调
    const refuseConfirmOnClick = () => {
        if (reasonValue == undefined || reasonValue?.length === 0) {
            messageApi.open({
                type: 'warning',
                content: '不合格原因必须选择'
            })
        } else {
            const refuseUserInfoAudit = async () => {
                try {
                    // @ts-ignore
                    const res = await refuseUserInfoAuditAPI(userInfo.data.key, reasonValue, rejectionValue);
                    if (res.data.data === true) {
                        setOpen(false);
                        messageApi.open({
                            type: 'success',
                            content: '操作完成'
                        })
                        setTimeout(() => {
                            navigate("/user/audit");
                            dispatch(setUserInfoAuditListFlush())
                        }, 500)
                    } else {
                        messageApi.open({
                            type: 'error',
                            content: '操作失败, 请稍后重试'
                        })
                    }
                } catch (error) {
                    messageApi.open({
                        type: 'error',
                        content: '出错了,请联系工作人员'
                    })
                }
            }
            refuseUserInfoAudit();
        }

    }
    // 不通过模块中取消按钮触发的回调
    const refuseCancelOnClick = () => {
        setOpen(false);
    }

    // 审核信息通过按钮点击确定后触发的回调
    const infoAuditConfirmOnConfirm = () => {
        const infoAuditConfirm = async () => {
            try {
                // @ts-ignore
                const res = await infoAuditConfirmAPI(userInfo.data.key);
                if (res.data.data === true) {
                    messageApi.open({
                        type: "success",
                        content: "操作成功"
                    })
                    setTimeout(() => {
                        navigate("/user/audit");
                        dispatch(setUserInfoAuditListFlush());
                    }, 500)
                } else {
                    messageApi.open({
                        type: "warning",
                        content: '服务繁忙请稍后再试'
                    })
                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: '出错了, 请联系工作人员'
                })
            }
        }
        infoAuditConfirm();
    }


    useEffect(() => {
        // 根据uri去查询对应用户信息
        const getUserInfoById = async () => {
            try {
                const userId = new URLSearchParams(location.search).get("user");
                const res = await getUserInfoByIdAPI(userId);
                dispatch(setUserInfoData(res.data.data));
            } catch (error) {
                navigate("/error")
            }
        }
        getUserInfoById().then(() => {
            // @ts-ignore
            console.log(userInfo.data.avatar)
            setPageLoading(false);
        })
    }, [])

    return (
        <>
            {contextHolder}
            <div className={"chenyun-user-content-audit-info"}>
                <Flex justify={"center"} gap={"large"}>
                    {pageLoading ?
                        <Flex align={"center"} style={{height: '60vh',}}>
                            <Spin indicator={<LoadingOutlined style={{ fontSize: 80, fontWeight: 'bold' }} spin />} />
                        </Flex> :
                        <>
                            <div style={{width: '40vw', height: '60vh'}}>
                                <div  className={"chenyun-user-content-audit-info-title"}>
                                    <Flex style={{height: '5vh'}} align={"center"}>
                                        <Flex style={{height:'25px', width:'25px'}} align={"center"}>
                                            <svg aria-hidden={true} width={"20px"} height={"20px"} className={"chenyun-user-content-audit-info-title-back"} onClick={goBackOnClick}>
                                                <use xlinkHref={"#icon-back"} />
                                            </svg>
                                        </Flex>
                                        <div>待审核基本信息</div>
                                    </Flex>
                                </div>
                                <div className={"chenyun-user-content-audit-info-avatar"}>
                                    <Flex style={{height: '55vh', width: '40vw'}} justify={"center"} align={"center"}>
                                        <Image
                                            width={400}
                                            src={userInfo.data && userInfo.data.avatar}
                                            fallback={"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMIAAADDCAYAAADQvc6UAAABRWlDQ1BJQ0MgUHJvZmlsZQAAKJFjYGASSSwoyGFhYGDIzSspCnJ3UoiIjFJgf8LAwSDCIMogwMCcmFxc4BgQ4ANUwgCjUcG3awyMIPqyLsis7PPOq3QdDFcvjV3jOD1boQVTPQrgSkktTgbSf4A4LbmgqISBgTEFyFYuLykAsTuAbJEioKOA7DkgdjqEvQHEToKwj4DVhAQ5A9k3gGyB5IxEoBmML4BsnSQk8XQkNtReEOBxcfXxUQg1Mjc0dyHgXNJBSWpFCYh2zi+oLMpMzyhRcASGUqqCZ16yno6CkYGRAQMDKMwhqj/fAIcloxgHQqxAjIHBEugw5sUIsSQpBobtQPdLciLEVJYzMPBHMDBsayhILEqEO4DxG0txmrERhM29nYGBddr//5/DGRjYNRkY/l7////39v///y4Dmn+LgeHANwDrkl1AuO+pmgAAADhlWElmTU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAAqACAAQAAAABAAAAwqADAAQAAAABAAAAwwAAAAD9b/HnAAAHlklEQVR4Ae3dP3PTWBSGcbGzM6GCKqlIBRV0dHRJFarQ0eUT8LH4BnRU0NHR0UEFVdIlFRV7TzRksomPY8uykTk/zewQfKw/9znv4yvJynLv4uLiV2dBoDiBf4qP3/ARuCRABEFAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghgg0Aj8i0JO4OzsrPv69Wv+hi2qPHr0qNvf39+iI97soRIh4f3z58/u7du3SXX7Xt7Z2enevHmzfQe+oSN2apSAPj09TSrb+XKI/f379+08+A0cNRE2ANkupk+ACNPvkSPcAAEibACyXUyfABGm3yNHuAECRNgAZLuYPgEirKlHu7u7XdyytGwHAd8jjNyng4OD7vnz51dbPT8/7z58+NB9+/bt6jU/TI+AGWHEnrx48eJ/EsSmHzx40L18+fLyzxF3ZVMjEyDCiEDjMYZZS5wiPXnyZFbJaxMhQIQRGzHvWR7XCyOCXsOmiDAi1HmPMMQjDpbpEiDCiL358eNHurW/5SnWdIBbXiDCiA38/Pnzrce2YyZ4//59F3ePLNMl4PbpiL2J0L979+7yDtHDhw8vtzzvdGnEXdvUigSIsCLAWavHp/+qM0BcXMd/q25n1vF57TYBp0a3mUzilePj4+7k5KSLb6gt6ydAhPUzXnoPR0dHl79WGTNCfBnn1uvSCJdegQhLI1vvCk+fPu2ePXt2tZOYEV6/fn31dz+shwAR1sP1cqvLntbEN9MxA9xcYjsxS1jWR4AIa2Ibzx0tc44fYX/16lV6NDFLXH+YL32jwiACRBiEbf5KcXoTIsQSpzXx4N28Ja4BQoK7rgXiydbHjx/P25TaQAJEGAguWy0+2Q8PD6/Ki4R8EVl+bzBOnZY95fq9rj9zAkTI2SxdidBHqG9+skdw43borCXO/ZcJdraPWdv22uIEiLA4q7nvvCug8WTqzQveOH26fodo7g6uFe/a17W3+nFBAkRYENRdb1vkkz1CH9cPsVy/jrhr27PqMYvENYNlHAIesRiBYwRy0V+8iXP8+/fvX11Mr7L7ECueb/r48eMqm7FuI2BGWDEG8cm+7G3NEOfmdcTQw4h9/55lhm7DekRYKQPZF2ArbXTAyu4kDYB2YxUzwg0gi/41ztHnfQG26HbGel/crVrm7tNY+/1btkOEAZ2M05r4FB7r9GbAIdxaZYrHdOsgJ/wCEQY0J74TmOKnbxxT9n3FgGGWWsVdowHtjt9Nnvf7yQM2aZU/TIAIAxrw6dOnAWtZZcoEnBpNuTuObWMEiLAx1HY0ZQJEmHJ3HNvGCBBhY6jtaMoEiJB0Z29vL6ls58vxPcO8/zfrdo5qvKO+d3Fx8Wu8zf1dW4p/cPzLly/dtv9Ts/EbcvGAHhHyfBIhZ6NSiIBTo0LNNtScABFyNiqFCBChULMNNSdAhJyNSiECRCjUbEPNCRAhZ6NSiAARCjXbUHMCRMjZqBQiQIRCzTbUnAARcjYqhQgQoVCzDTUnQIScjUohAkQo1GxDzQkQIWejUogAEQo121BzAkTI2agUIkCEQs021JwAEXI2KoUIEKFQsw01J0CEnI1KIQJEKNRsQ80JECFno1KIABEKNdtQcwJEyNmoFCJAhELNNtScABFyNiqFCBChULMNNSdAhJyNSiECRCjUbEPNCRAhZ6NSiAARCjXbUHMCRMjZqBQiQIRCzTbUnAARcjYqhQgQoVCzDTUnQIScjUohAkQo1GxDzQkQIWejUogAEQo121BzAkTI2agUIkCEQs021JwAEXI2KoUIEKFQsw01J0CEnI1KIQJEKNRsQ80JECFno1KIABEKNdtQcwJEyNmoFCJAhELNNtScABFyNiqFCBChULMNNSdAhJyNSiECRCjUbEPNCRAhZ6NSiAARCjXbUHMCRMjZqBQiQIRCzTbUnAARcjYqhQgQoVCzDTUnQIScjUohAkQo1GxDzQkQIWejUogAEQo121BzAkTI2agUIkCEQs021JwAEXI2KoUIEKFQsw01J0CEnI1KIQJEKNRsQ80JECFno1KIABEKNdtQcwJEyNmoFCJAhELNNtScABFyNiqFCBChULMNNSdAhJyNSiEC/wGgKKC4YMA4TAAAAABJRU5ErkJggg=="}
                                            placeholder={
                                                <Image
                                                    preview={false}
                                                    src={`${userInfo.data && userInfo.data.avatar}?x-oss-process=image/blur,r_50,s_50/quality,q_1/resize,m_mfit,h_200,w_200`}
                                                    width={200}
                                                />
                                            }
                                            style={{borderRadius: '5px', border: '1px solid rgba(0,0,0,0.3)'}}
                                        />
                                    </Flex>
                                </div>
                            </div>

                            <div className={"chenyun-user-content-audit-info-form"}>
                                <Flex vertical={true} style={{height:'60vh'}} justify={"space-evenly"}>
                                    <Descriptions
                                        bordered
                                        size={"default"}
                                        items={borderedItems}
                                    />
                                    <Flex style={{paddingRight:'25px'}} justify={"flex-end"} gap={"large"}>
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
                                            <Select
                                                mode="multiple"
                                                size={'middle'}
                                                placeholder="请选择不合格原因"
                                                onChange={handleChange}
                                                style={{ width: '100%', marginBottom: '10px' }}
                                                options={[
                                                    {
                                                        value: '0',
                                                        label: '头像不合格'
                                                    },
                                                    {
                                                        value: '1',
                                                        label: '个人简介不合格'
                                                    },
                                                    {
                                                        value: '2',
                                                        label: '用户呢称不合格'
                                                    }
                                                ]}
                                            />
                                            <TextArea placeholder={"请输入不通过的具体理由，让用户知道该如何修改"} onChange={inputRejectionValueOnChange} autoSize={{minRows: 4, maxRows: 10}} />
                                        </Modal>
                                        <Popconfirm title={"确定通过该用户的信息吗？"} onConfirm={infoAuditConfirmOnConfirm}>
                                            <Button type={"primary"}>通过</Button>
                                        </Popconfirm>
                                    </Flex>
                                </Flex>
                            </div>
                        </>
                    }
                </Flex>
            </div>
        </>
    )
}

export default AuditInfo;
