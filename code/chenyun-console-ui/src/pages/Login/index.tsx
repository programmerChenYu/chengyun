import {Button, Checkbox, ConfigProvider, Flex, Form, Input, message} from "antd";
import './index.css'
import {LockOutlined, UserOutlined} from "@ant-design/icons";
import {loginAPI} from "../../apis/Login/loginAPI.tsx";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
const Login = () => {
    // 全局提示
    const [messageApi, contextHolder] = message.useMessage();

    const [loginStatus, setLoginStatus] = useState<boolean>(false);

    const navigate = useNavigate();

    const onFinish = (values: any) => {
        console.log(values)
        const login = async () => {
            try {
                const res = await loginAPI(values.username, values.password);
                if (res.data.data === "0101004") {
                    messageApi.open({
                        type: "warning",
                        content: "用户名或密码错误"
                    })
                } else if (res.data.data === '0101003') {
                    messageApi.open({
                        type: 'error',
                        content: "您以被封号"
                    })
                } else if (res.data.data === '0000001') {
                    messageApi.open({
                        type: 'warning',
                        content: '网站太火爆了，请稍后再试'
                    })
                } else if (res.data.data === '0101002') {
                    messageApi.open({
                        type: 'error',
                        content: '请使用管理员账号登陆'
                    })
                } else {
                    if (values.remember) {
                        localStorage.setItem('cyUsername', values.username);
                        localStorage.setItem('cyPassword', values.password);
                    } else {
                        localStorage.removeItem('cyUsername');
                        localStorage.removeItem('cyPassword');
                    }
                    setLoginStatus(true);
                    messageApi.open({
                        type: "success",
                        content: "登陆成功"
                    })
                    localStorage.setItem('cyToken', res.data.data)
                }
            } catch (error) {
            }
        }

        login();
    };

    useEffect(() => {
        if (loginStatus) {
            setTimeout(() => {
                navigate("/");
            }, 500)
        }
    }, [loginStatus])

    return (
        <>
            {contextHolder}
            <ConfigProvider theme={{
                token: {
                    colorPrimary: 'rgba(234,157,59,0.4)'
                }
            }}>
                <Flex style={{width: '100vw', height:'100vh'}} justify={"center"} align={"center"}>
                    <div className={'chenyun-login'}>
                        <Flex vertical={true}>
                            <Flex className={'chenyun-login-title'} align={"center"} justify={"center"}>
                                <svg aria-hidden={"true"} width={'70px'} height={'70px'}>
                                    <use xlinkHref={"#icon-chengyun"} />
                                </svg>
                                <div style={{marginLeft:'5px', fontSize:'24px', fontWeight:"900"}}>橙云控制中心</div>
                            </Flex>
                            <Flex className={'chenyun-login-content'} justify={"center"} >
                                <Form
                                    name="login"
                                    initialValues={{ remember: false ,
                                    username: localStorage.getItem('cyUsername') ? localStorage.getItem('cyUsername') : '',
                                    password: localStorage.getItem('cyPassword') ? localStorage.getItem('cyPassword') : '',}}
                                    style={{width: '300px',height:'240px'}}
                                    onFinish={onFinish}
                                >
                                    <Form.Item
                                        name="username"
                                        rules={[{ required: true, message: '请输入你的用户名' }]}
                                    >
                                        <Input prefix={<UserOutlined />} placeholder="用户名" />
                                    </Form.Item>
                                    <Form.Item
                                        name="password"
                                        rules={[{ required: true, message: '请输入你的密码' }]}
                                    >
                                        <Input prefix={<LockOutlined />} type="password" placeholder="密码" />
                                    </Form.Item>
                                    <Form.Item>
                                        <Flex justify="space-between" align="center">
                                            <Form.Item name="remember" valuePropName="checked" noStyle>
                                                <Checkbox>记住我</Checkbox>
                                            </Form.Item>
                                            <a href="" style={{color: 'rgba(234,157,59)'}}>忘记密码</a>
                                        </Flex>
                                    </Form.Item>

                                    <Form.Item>
                                        <Button block type="primary" htmlType="submit">
                                            登录
                                        </Button>
                                    </Form.Item>
                                </Form>
                            </Flex>
                        </Flex>
                    </div>
                </Flex>
            </ConfigProvider>
        </>
    )
}

export default Login;
