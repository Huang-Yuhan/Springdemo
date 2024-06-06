import {useEffect, useState} from "react";
import {Avatar, Card, Col, DatePicker, Row, Space, Statistic, Table} from "antd";
import {searchAdminBook, searchBookByDate} from "../server/bookserver";

export const AdminBookStatistics=()=>{
    const [books,setBooks]=useState([]);
    useEffect(
        ()=>{
            searchAdminBook(null,(data)=>setBooks(data))
        },[]
    )
    const columns=[
        {
            title:'cover',
            dataIndex:'image',
            key:'img',
            render:(re)=>(<Avatar src={re} height={50}></Avatar>)
        },
        {
            title:'name',
            dataIndex:'name',
            key:'name'
        },
        {
            title:'sold count',
            dataIndex:'count',
            key:'count'
        },
        {
            title:'total price',
            dataIndex:'totalPrice',
            key:'totalPrice',
            render:(re)=>(re.toFixed(2))
        }
    ]

    const tableDate=(books)=>
    {
        let lis=[];
        for(let i = 0;i<books.length;i++)
        {
            lis.push(
                {
                    key:books[i].id,
                    name:books[i].name,
                    image:books[i].image,
                    count:books[i].count,
                    totalPrice:books[i].price*books[i].count
                }
            )
        }
        return lis;
    }

    const handleDate=(dateValue,dateString)=>
    {
        searchAdminBook(dateValue,(data)=>setBooks(data))
    }
    console.log(tableDate(books))
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
            <Table columns={columns} dataSource={tableDate(books)} pagination={false} rowKey={record=>record.id}>
            </Table>
        </Space>

    )
}