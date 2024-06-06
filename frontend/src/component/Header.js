import React, {useState} from "react";
import {Avatar, Button, Space, Typography} from "antd";
import UserAvatar from "../assets/img/useravatar.webp"
import {LogoutOutlined} from "@ant-design/icons";
import {useNavigate} from "react-router-dom";
import {logout} from "../server/userlogin";

const HeaderInfo=(props)=>
{
    const navigate=useNavigate();
    console.log("props",props)
    console.log("username:",props.userName)
    return (
        <Space
            style={
                {
                    float:'right',
                }
            }
        >
            <Typography.Title
                level={5}
                style={{margin:'auto',fontFamily:'Comic Sans MS'}}
            >
                Hello!! {props.userName}
            </Typography.Title>
            <Avatar
                src={UserAvatar}
            />
            <Button
                icon={<LogoutOutlined />}
                onClick={
                    ()=>
                    {
                        logout();
                        navigate("/login");
                    }
                }
            >
                Log Out
            </Button>
        </Space>

    );
}

export default HeaderInfo;