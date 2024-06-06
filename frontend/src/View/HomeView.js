import React, {useEffect, useState} from "react";
import {Layout, Space, Typography} from 'antd';
import SideBar from "../component/SideBar";
import HeaderInfo from "../component/Header";
import {Outlet} from "react-router";
import {checkIsAuth, getUserName} from "../server/userlogin";


const { Header, Sider, Content } = Layout;
const headerStyle = {
    color: '#000',
    height: '9vh',
    backgroundColor: '#fff',
    border:'solid',
    borderWidth:'1px',
    borderColor:'rgb(220,220,220)',
};
const contentStyle = {
    textAlign: 'center',
    minHeight: '91vh',
    lineHeight: '110%',
    color: '#000',
    backgroundColor: '#fff',
    border: 'solid',
    borderWidth: '1px',
    borderColor:'rgb(220,220,220)',

};
const siderStyle = {
    textAlign: 'center',
    lineHeight: '120px',
    color: '#000',
    backgroundColor: '#fff',
    border:'solid',
    borderWidth:'1px',
    borderColor:'rgb(220,220,220)',
};

const HomeView=()=>
{
    const [userid,setUserId]=useState(sessionStorage.getItem("UserId"));
    const [isauth,setIsAuth]=useState(false);
    const [username,setUserName]=useState("");
    useEffect(
        ()=>
        {
            checkIsAuth(
                userid,
                ()=>{setIsAuth(false);},
                ()=>{
                    setIsAuth(true);
                    getUserName(userid)
                },
                ()=>{setIsAuth(true);getUserName(userid)},
                ()=>setIsAuth(false)
            )
            getUserName(userid,(data)=>setUserName(data))
        }
        ,[]
    )

    console.log(userid);
    console.log(isauth);
    return(
        <div>
            {
                isauth&&<>
                    <Space
                        direction="vertical"
                        style={{
                            width: '100%',
                        }}
                        size={[0, 48]}
                    >
                        <Layout>
                            <Sider style={siderStyle}>
                                <SideBar/>
                            </Sider>


                            <Layout>
                                <Header style={headerStyle}>
                                    <HeaderInfo userName={sessionStorage.getItem('UserName')}/>
                                </Header>
                                <Content style={contentStyle}>
                                    <Outlet/>
                                </Content>
                            </Layout>
                        </Layout>
                    </Space>
                </>
            }
            {
                (!isauth)&& <Typography>
                    you need to login first
                    <br/>
                    localhost:3000/Login
                </Typography>
            }
        </div>
    );
}

export default HomeView;