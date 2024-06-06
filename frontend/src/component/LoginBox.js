import React, { useEffect} from 'react';
import { message} from "antd";
import {Button} from "antd/lib";
import { useNavigate} from "react-router-dom";
import {checkIsAuth, getUserId, getUserName, register} from "../server/userlogin"


import {
    LockOutlined,
    UserOutlined,
} from '@ant-design/icons';
import {
    LoginForm,
    ProFormText,
    ProConfigProvider,
} from '@ant-design/pro-components';
import { Tabs } from 'antd';
import { useState } from 'react';
import logoimg from "../assets/img/BookStoreLogo.png"

const iconStyles = {
    marginInlineStart: '16px',
    color: 'rgba(0, 0, 0, 0.2)',
    fontSize: '24px',
    verticalAlign: 'middle',
    cursor: 'pointer',
};

function LoginBox(){
    const [loginType, setLoginType] = useState('login');
    const [account,setAccount] = useState("");
    const [password,setPassword]= useState("");
    const [rpassword,setRpassword]=useState("");
    const [userId,setUserId]=useState("");
    const navigate=useNavigate();
    const [button_pressed,setButton_Press]=useState(0);
    const [email,setEmail]=useState("");
    useEffect(
        ()=>
        {
            if(button_pressed===0)return;
            if(loginType==='login')
            {
                getUserId(
                    account,
                    password,
                    (data)=>
                    {
                        setUserId(data);
                        checkIsAuth(
                            data,
                            ()=>{if(button_pressed)message.info("login fail, please check your account and password");},
                            ()=>{getUserName(data);navigate('/BookShop');},
                            ()=>{getUserName(data);navigate('/BookShop');},
                            ()=>{message.info("you are banned!!!");}
                        )
                    }
                )
            }
            if(loginType==='register')
            {
                if(rpassword!==password)
                {
                    message.info("你两次的密码不一致!");
                    return;
                }
                if((password&&email&&account)==false)
                {
                    message.info("你应该输入内容!!!");
                    return;
                }
                register(
                    account,
                    password,
                    email,
                    (data)=>
                    {
                        setUserId(data);
                        checkIsAuth(
                            data,
                            ()=>{message.info("register fail, please change your account");},
                            ()=>{getUserName(userId,()=>{navigate('/BookShop');}); },
                            ()=>{getUserName(userId,()=>{navigate('/BookShop');}); },
                            ()=>{message.info("you are banned!!!");}
                        )
                    }
                )
            }
        },
        [button_pressed]
    )
    console.log("logintype:",loginType);
    console.log("button count:",button_pressed);
    return (
        <ProConfigProvider hashed={false}>
            <div style={{ backgroundColor: 'white',
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                border:'4px solid black',
                borderRadius:'5px',
            }}>
                <LoginForm
                    logo={logoimg}
                    title="BookStore"
                    subTitle="在线书店电子购买网站"
                    submitter={
                        {
                            render: (props, doms) => {
                                return [
                                    loginType==='login'&&(
                                        <>
                                            <Button
                                                type='primary'
                                                key='login'
                                                onClick={
                                                    ()=>setButton_Press(button_pressed+1)
                                                }
                                            >
                                                登录
                                            </Button>
                                        </>
                                    ),

                                    loginType==='register'&&(
                                        <>
                                            <Button
                                                key='register'
                                                onClick={
                                                    ()=>setButton_Press(button_pressed+1)
                                                }
                                            >
                                                注册
                                            </Button>
                                        </>
                                    )
                                ]
                            }
                        }
                    }
                >
                    <Tabs
                        centered
                        activeKey={loginType}
                        onChange={(activeKey) => setLoginType(activeKey)}
                    >
                        <Tabs.TabPane key={'login'} tab={'账号密码登录'} />
                        <Tabs.TabPane key={'register'} tab={'注册'} />
                    </Tabs>
                    {loginType === 'login' && (
                        <>
                            <ProFormText
                                name="username"
                                fieldProps={{
                                    size: 'large',
                                    prefix: <UserOutlined className={'prefixIcon'} />,
                                    value:account,
                                    onChange: (e) => {setAccount(e.target.value);}
                                }}
                                placeholder={'用户名: admin or user'}
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入用户名!',
                                    },
                                ]}
                                valu
                            />
                            <ProFormText.Password
                                name="password"
                                fieldProps={{
                                    size: 'large',
                                    prefix: <LockOutlined className={'prefixIcon'} />,
                                    value:password,
                                    onChange: (e) => {setPassword(e.target.value);}
                                }}
                                placeholder={'密码: ant.design'}
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入密码！',
                                    },
                                ]}
                            />
                        </>
                    )}
                    {loginType === 'register' && (
                        <>
                            <ProFormText
                                name="username"
                                fieldProps={{
                                    size: 'large',
                                    prefix: <UserOutlined className={'prefixIcon'} />,
                                    value:account,
                                    onChange: (e) => {setAccount(e.target.value);}
                                }}
                                placeholder={'用户名: admin or user'}
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入用户名!',
                                    },
                                ]}
                                valu
                            />
                            <ProFormText.Password
                                name="password"
                                fieldProps={{
                                    size: 'large',
                                    prefix: <LockOutlined className={'prefixIcon'} />,
                                    value:password,
                                    onChange: (e) => {setPassword(e.target.value);}
                                }}
                                placeholder={'密码: 你的密码'}
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入密码！',
                                    },
                                ]}
                            />
                            <ProFormText.Password
                                name="repeat_password"
                                fieldProps={{
                                    size: 'large',
                                    prefix: <LockOutlined className={'prefixIcon'} />,
                                    value:rpassword,
                                    onChange: (e) => {setRpassword(e.target.value);}
                                }}
                                dependencies={['password']}
                                placeholder={'请确认你的密码'}
                                rules={[
                                    {
                                        required: true,
                                        message: '请重复你的密码！',
                                    },
                                    ({ getFieldValue }) => ({
                                        validator(_, value) {
                                            if (!value || getFieldValue('password') === value) {
                                                return Promise.resolve();
                                            }
                                            return Promise.reject(new Error('The two passwords that you entered do not match!'));
                                        },
                                    }),
                                ]}
                            />
                            <ProFormText
                                name="email"
                                fieldProps={{
                                    size: 'large',
                                    prefix: <UserOutlined className={'prefixIcon'} />,
                                    value:email,
                                    onChange: (e) => {setEmail(e.target.value);}
                                }}
                                placeholder={'你的邮箱'}
                                rules={[
                                    {
                                        type: 'email',
                                        message: 'The input is not valid E-mail!',
                                    },
                                    {
                                        required: true,
                                        message: 'Please input your E-mail!',
                                    },
                                ]}
                                valu
                            />
                        </>
                    )}
                </LoginForm>
            </div>
        </ProConfigProvider>
    );
};
export default LoginBox;