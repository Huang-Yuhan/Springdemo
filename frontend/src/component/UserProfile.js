import {Button, Form, Space} from "antd";
import {Input} from "antd/lib";
import {useEffect, useState} from "react";
import {getUesrById, updateUserInfo} from "../server/userlogin";

const formItemLayout = {
    labelCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 8,
        },
    },
    wrapperCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 16,
        },
    },
};
const tailFormItemLayout = {
    wrapperCol: {
        xs: {
            span: 24,
            offset: 0,
        },
        sm: {
            span: 16,
            offset: 8,
        },
    },
};
export const UserProfile=()=>
{
    const [form]=Form.useForm();
    const [user,setUser]=useState({})

    useEffect(
        ()=>{
            getUesrById(sessionStorage.getItem('UserId'),(data)=>setUser(data))
        },[]
    )

    useEffect(
        ()=>{
            form.setFieldsValue(user)
        },[user]
    )

    const onFinish=(values)=>{
        let v=values;
        v.UserId=sessionStorage.getItem('UserId')
        console.log(v)
        updateUserInfo(v,(data)=>{setUser(data);window.location.reload()})
        sessionStorage.setItem('UserName',v.name)
    }

    const UserForm=()=>{
        return(
            <Form
                {...formItemLayout}
                form={form}
                name={"User Profile"}
                onFinish={onFinish}
                scrollToFirstError
                style={{
                    width:'50%',
                }}
            >
                <Form.Item
                    name = "name"
                    label = "User Name"
                    rules={[
                        {
                            required: true,
                            message: "Please input your User Name!",
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>
                <Form.Item
                    name = "nickname"
                    label = "Nick Name"
                    rules={[
                        {
                            required: true,
                            message: "Please input your Nick Name!",
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>
                <Form.Item
                    name={"address"}
                    label={"Email"}
                    rules={[
                        {
                            type: "email",
                            message: "The input is not valid E-mail!",
                        },
                        {
                            required: true,
                            message: "Please input your E-mail!",
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    name={"tel"}
                    label={"Phone Number"}
                    rules={[
                        {
                            required: true,
                            message: "Please input your phone number!",
                        },
                    ]}
                    >
                    <Input/>
                </Form.Item>
                <Form.Item {...tailFormItemLayout}>
                    <Button type={"primary"} htmlType={"submit"}>
                        Submit
                    </Button>
                </Form.Item>
            </Form>
        )
    }

    return(
        <div style={{
            marginTop:'50px',
            display:'flex',
            justifyContent:'center',
        }}>
            {UserForm()}
        </div>
    )
}