import './index.css';
import {
    Button,
    ConfigProvider,
    Flex,
    Input,
    Layout,
    Modal,
    Popconfirm,
    Select,
    Radio,
    RadioChangeEvent,
    message
} from "antd";
import TagList from "./TagList.tsx";
import React, {useState} from "react";
import {useAppDispatch} from "../../store/hooks.ts";
import {setTagListFlush} from "../../store/modules/tagListSlice.tsx";
import {batchDeactivateAPI, batchEnableAPI, createTagAPI} from "../../apis/TagManagement/tagOption.tsx";
import {useLocation, useNavigate} from "react-router-dom";
import SearchTagList from "./SearchTagList.tsx";
import {setSearchTagListFlush} from "../../store/modules/searchTagListSlice.tsx";
import {rsaEncrypt} from "../../utils";

const TagManagement = () => {
    const {Content} = Layout;

    // 全局提示
    const [messageApi, contextHolder] = message.useMessage();

    const location = useLocation();
    const navigate = useNavigate();

    const dispatch = useAppDispatch();

    // 搜索框中的数据
    const [searchValue, setSearchValue] = useState<string>("");
    const searchValueOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchValue(e.target.value);
    }
    // 状态选择框中的数据
    const [statusValue, setStatusValue] = useState<number>();
    const statusValueOnChange = (value: number) => {
        setStatusValue(value);
        console.log(statusValue)
    }
    // 点击查询按钮触发的回调函数
    const searchButtonOnClick = () => {
        console.log('要查询的标签是: ',searchValue,' 要查询的状态是：', statusValue)
        if ((searchValue === "" || searchValue == undefined) && statusValue == undefined) {
            navigate(`/tag`);
        } else {
            if (searchValue === "" || searchValue == undefined) {
                navigate(`/tag?status=${encodeURIComponent(rsaEncrypt.encrypt(String(statusValue)))}`);
                dispatch(setSearchTagListFlush());
            }else if (statusValue == undefined) {
                navigate(`/tag?search=${encodeURIComponent(rsaEncrypt.encrypt(searchValue))}`);
                dispatch(setSearchTagListFlush());
            } else {
                navigate(`/tag?search=${encodeURIComponent(rsaEncrypt.encrypt(searchValue))}&status=${encodeURIComponent(rsaEncrypt.encrypt(String(statusValue)))}`);
                dispatch(setSearchTagListFlush());
            }
        }
    }
    // 点击清空按钮触发的回调函数
    const clearSearchButtonOnClick = () => {
        setSearchValue("");
        setStatusValue(undefined);
    }

    // 表格中的选中行的数据
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);
    const [isVisible, setIsVisible] = useState<boolean>(false);

    const rowSelectionProps = {
        selectedRowKeys: selectedRowKeys,
        onChange: (newSelectedRowKeys: React.Key[]) => {
            if (newSelectedRowKeys.length > 0) {
                setIsVisible(true);
            } else {
                setIsVisible(false);
            }
            const keys:React.Key[] = [];
            keys.push(...selectedRowKeys);
            keys.push(...newSelectedRowKeys);
            setSelectedRowKeys(keys);
        }
    }

    // 清空列表中选中的
    const clearSelectedOnClick = () => {
        setIsVisible(false);
        console.log(isVisible)
        setSelectedRowKeys([]);
    }
    // 批量停用按钮回调
    const batchDeactivateOnConfirm = () => {
        console.log(selectedRowKeys);
        // 发请求批量停用
        const batchDeactivate = async () => {
            try {
                const tagKeyList = selectedRowKeys.map((key) => String(key))
                const res = await batchDeactivateAPI(tagKeyList);
                if (res.data.data === true) {
                    messageApi.open({
                        type: 'success',
                        content: '批量停用完成'
                    })
                } else {
                    messageApi.open({
                        type: 'warning',
                        content: '系统繁忙，请稍后再试'
                    })
                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: '出错了，请联系工作人员'
                })
            }
        }

        batchDeactivate().then(() => {
            dispatch(setTagListFlush());
            setSelectedRowKeys([]);
            setIsVisible(false);
        })
    }
    // 批量启用按钮回调
    const batchEnableOnConfirm = () => {
        console.log(selectedRowKeys);
        // 发请求批量启用
        const batchEnable = async () => {
            try {
                const tagIds = selectedRowKeys.map((key) => String(key));
                const res = await batchEnableAPI(tagIds);
                if (res.data.data === true) {
                    messageApi.open({
                        type: 'success',
                        content: '批量启用完成'
                    })
                } else {
                    messageApi.open({
                        type: 'warning',
                        content: '系统繁忙，请稍后再试'
                    })
                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: '出错了，请联系工作人员'
                })
            }
        }
        batchEnable().then(() => {
            dispatch(setTagListFlush());
            setSelectedRowKeys([]);
            setIsVisible(false);
        })
    }
    // 新建按钮
    // 弹出新建标签模块
    const [createTagModelOpen, setCreateTagModelOpen] = useState<boolean>(false);
    const showCreateTagModelOnClick = () => {
        setCreateTagModelOpen(true);
    }
    // 新建模块输入标签名
    const [newTagName, setNewTagName] = useState<string>()
    const tagNameOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setNewTagName(e.target.value);
    }
    // 新建按钮模块状态选择
    const [newTagStatus, setNewTagStatus] = useState<number>();
    const tagStatusOnChange = (e: RadioChangeEvent) => {
        console.log(e.target.value);
        setNewTagStatus(e.target.value);
    }
    const [createTagInputState, setCreateTagInputState] = useState<boolean>(true)
    // 弹出新建标签模块确认按钮
    const createTagOnClick = () => {
        console.log(newTagName, newTagStatus);
        if (newTagName !== undefined && newTagName.trim() !== "") {
            //TODO 像后端发请求新建标签，传递 Token 进行身份认定
            const createTag = async () => {
                try {
                    const res = await createTagAPI(newTagName, newTagStatus);
                    if (res.data.data === true) {
                        messageApi.open({
                            type: 'success',
                            content: `标签【${newTagName}】已创建，目前状态为【${newTagStatus === 1 ? '启用' : '停用'}】`
                        })
                    } else {
                        messageApi.open({
                            type: 'warning',
                            content: '系统繁忙，请稍后再试'
                        })
                    }
                } catch (error) {
                    messageApi.open({
                        type: 'error',
                        content: '出错了，请联系工作人员'
                    })
                }
            }
            createTag().then(() => {
                setCreateTagModelOpen(false);
                dispatch(setTagListFlush());
                setNewTagName(undefined);
                setNewTagStatus(undefined);
                setCreateTagInputState(true);
            })
        } else {
            messageApi.open({
                type: 'warning',
                content: '标签名必须填写'
            })
            setCreateTagInputState(false);
        }
    }
    // 弹出新建标签模块取消按钮
    const createTagCancelOnClick = () => {
        setNewTagName(undefined);
        setNewTagStatus(undefined);
        setCreateTagModelOpen(false);
        setCreateTagInputState(true);
    }
    return (
        <>
            {contextHolder}
            <ConfigProvider theme={{
                components: {

                }
            }}>
                <div className={"chenyun-tag"}>
                    <Flex vertical={true} align={"center"} gap={"large"}>
                        <Content className={"chenyun-tag-title"}>
                            <Flex align={"center"} style={{height: '50px'}}>
                                <div>标签管理</div>
                            </Flex>
                        </Content>
                        <Content className={"chenyun-tag-content"}>
                            <Flex vertical={true} style={{paddingTop:'24px', paddingLeft:'20px', paddingRight:'20px'}}>
                                <div className={"chenyun-tag-content-search"}>
                                    <Flex gap={"large"}>
                                        <Flex align={"center"}>
                                            <div className={"chenyun-tag-content-search-tagName"}>标签名:</div>
                                            <Input placeholder={"请输入"} style={{width: '12vw'}} value={searchValue} onChange={searchValueOnChange}/>
                                        </Flex>
                                        <Flex align={"center"} style={{marginRight:'30px'}}>
                                            <div className={"chenyun-tag-content-search-status"}>状态:</div>
                                            <Select size={"middle"} style={{width:'100px'}} placeholder={"请选择"} options={[
                                                { value: 0, label: '停用' },
                                                { value: 1, label: '启用' }
                                            ]} value={statusValue} onChange={statusValueOnChange}/>
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
                                            <Button onClick={searchButtonOnClick}>查询</Button>
                                        </ConfigProvider>
                                        <Button onClick={clearSearchButtonOnClick}>清空</Button>
                                    </Flex>
                                </div>
                                <div className={"chenyun-tag-content-option"}>
                                    <Flex gap={"large"}>
                                        <ConfigProvider theme={{
                                        }}>
                                            <Button onClick={showCreateTagModelOnClick} type={"primary"}>+ 新建</Button>
                                            <Modal title="新建标签" centered open={createTagModelOpen}
                                                   footer={
                                                        <Flex justify={"flex-end"} gap={"large"}>
                                                            <Button onClick={createTagCancelOnClick}>取消</Button>
                                                            <Button type={"primary"} onClick={createTagOnClick}>确定</Button>
                                                        </Flex>
                                                    } onCancel={() => {setCreateTagModelOpen(false);setCreateTagInputState(true)}}>
                                                <div className={"chenyun-tag-content-option-createTag"}>
                                                    <Flex vertical={true} justify={"space-evenly"} style={{height:'20vh'}}>
                                                        <Flex align={"center"} gap={"small"}>
                                                            <div style={{fontSize: '16px', fontWeight: "bold"}}>标签名:</div>
                                                            <Input placeholder={"请输入将要创建的标签名"} {...(!createTagInputState && { status: "error" })} style={{width:'200px'}} value={newTagName} onChange={tagNameOnChange} />
                                                        </Flex>
                                                        <Flex gap={"small"} align={"center"}>
                                                            <div style={{fontSize: '16px', fontWeight: "bold"}}>状态选择:</div>
                                                            <Radio.Group onChange={tagStatusOnChange} value={newTagStatus}>
                                                                <Radio value={0}>停用</Radio>
                                                                <Radio value={1}>启用</Radio>
                                                            </Radio.Group>
                                                        </Flex>
                                                    </Flex>
                                                </div>
                                            </Modal>
                                        </ConfigProvider>
                                        <Popconfirm title={"确定批量停用吗，这是一个风险操作"} onConfirm={batchDeactivateOnConfirm}>
                                            <Button disabled={ isVisible ? false : true}>批量停用</Button>
                                        </Popconfirm>
                                        <Popconfirm title={"确定要批量启用吗？"} onConfirm={batchEnableOnConfirm}>
                                            <Button disabled={ isVisible ? false : true}>批量启用</Button>
                                        </Popconfirm>
                                    </Flex>
                                </div>
                                <div className={`chenyun-tag-content-tips ${isVisible ? 'visible' : ''}`}>
                                    <Flex align={"center"} style={{height:'4vh'}}>
                                        <svg className={"chenyun-tag-content-tips-svg"} aria-hidden={"true"} width={'25px'} height={'25px'}>
                                            <use xlinkHref={'#icon-jinggao'}></use>
                                        </svg>
                                        <div style={{paddingLeft:'5px'}}>已选择 {selectedRowKeys.length} 项，点击<span style={{color:'rgba(255, 165, 0)', fontWeight:"bold", cursor:"pointer"}} onClick={clearSelectedOnClick}> 清空</span></div>
                                    </Flex>
                                </div>
                                <div className={"chenyun-tag-content-list"}>
                                    {!location.search ? <TagList rowSelection={rowSelectionProps} /> : <SearchTagList rowSelection={rowSelectionProps} />}
                                </div>
                            </Flex>
                        </Content>
                        <Content className={"chenyun-tag-author"}>
                            <Flex style={{height:'5vh'}} align={"center"}>
                                <div>Copyright © 2024 爱吃小鱼的橙子出品 <a href={"https://github.com/programmerChenYu/chengyun"} target={'_blank'}>chenyun.com</a></div>
                            </Flex>
                        </Content>
                    </Flex>
                </div>
            </ConfigProvider>
        </>
    )
}

export default TagManagement;
