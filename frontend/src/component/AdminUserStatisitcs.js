import {useEffect, useState} from "react";
import {searchAdminBook} from "../server/bookserver";
import {Avatar, DatePicker, Space, Table} from "antd";
import {getUserStatistics} from "../server/adminserver";

export const AdminUserStatisitcs=()=>{
    const[users,setUsers]=useState([])
    useEffect(
        ()=>{
            getUserStatistics(null,(data)=>setUsers(data))
        },[]
    )
    const columns=[
        {
            title:'name',
            dataIndex:'name',
            key:'name'
        },
        {
            title:'purchase price',
            dataIndex:'purchasePrice',
            key:'totalPrice',
            render:(re)=>(re.toFixed(2))
        }
    ]

    const tableDate=(users)=>
    {
        let lis=[];
        for(let i =0;i<users.length;i++)
        {
            lis.push(
                {
                    key:users[i].id,
                    name:users[i].name,
                    purchasePrice:users[i].purchasePrice,
                }
            )
        }

        return lis;
    }

    const handleDate=(dateValue,dateString)=>
    {
        getUserStatistics(dateValue,(data)=>setUsers(data))
    }
    console.log(users)
    console.log(tableDate(users))
    return (
        <Space direction='vertical' style={{width:'100%',}}>
            <DatePicker.RangePicker
                size='large'
                style={{width:'80%',marginTop:'10px'}}
                showTime
                onChange={handleDate}
                onCalendarChange={handleDate}
            >

            </DatePicker.RangePicker>
            <Table columns={columns} dataSource={tableDate(users)} pagination={false} rowKey={record=>record.id}>
            </Table>
        </Space>

    )
}