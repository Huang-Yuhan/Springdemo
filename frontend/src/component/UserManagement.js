import {useEffect, useState} from "react";
import {banUser, getUsers, recoverUser} from "../server/adminserver";
import {Button, Layout, List, Space, Table, Tag} from "antd";
import {checkIsAuth, deleteUser} from "../server/userlogin";
import {redirect} from "react-router-dom";

let UserManagement=()=>
{
    const [users,setUsers]=useState([]);
    useEffect(
        ()=>
        {
            getUsers((data)=>setUsers(data))
        },[]
    )



    const columns=[
        {
            title:'name',
            dataIndex:'name',
            key:'name'
        },
        {
            title: 'state',
            dataIndex: 'state',
            key:'state',
            render:(_,{state})=>
                (
                    <>
                        {
                            state==='allow'&&<Tag color='green'>
                                ALLOW
                            </Tag>
                        }
                        {
                            state==='ban'&&<Tag color='red'>
                                BAN
                            </Tag>
                        }
                    </>
                )
        },
        {
            title: 'type',
            dataIndex: 'type',
            key:'type',
            render:(_,record)=>
                (
                    <>
                        {
                            record.type==='user'&&<Tag color='green'>
                                USER
                            </Tag>
                        }
                        {
                            record.type==='admin'&&<Tag color='blue'>
                                ADMIN
                            </Tag>
                        }
                    </>
                )
        },
        {
            title: 'action',
            key:'action',
            render:(_,record)=>
                (
                    <Space size="middle">
                        {
                            record.state==='allow'&&<Button
                                type = 'primary'
                                key = 'BAN'
                                danger
                                onClick={()=>banUser(record.userId,(data)=>setUsers(data))}
                                disabled={record.type !== 'user'}
                            >
                                BAN
                            </Button>
                        }
                        {
                            record.state==='ban'&&<Button
                                type = 'primary'
                                key = 'RECOVER'
                                style={{backgroundColor:'darkseagreen'}}
                                onClick={()=>recoverUser(record.userId,(data)=>setUsers(data))}
                            >
                                RECOVER
                            </Button>
                        }
                        <Button
                            type='primary' key='delete' danger onClick={()=>deleteUser(record.userId,(data)=>setUsers(data))}
                            disabled={record.type !== 'user'}
                        >
                            Delete
                        </Button>
                    </Space>
                )
        }
    ]

    console.log("users",users)

    return(

        <Table
            columns={columns}
            dataSource={users}
        >

        </Table>
    );
}

export default UserManagement