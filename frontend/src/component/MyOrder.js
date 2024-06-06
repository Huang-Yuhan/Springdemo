import React, {Component, useEffect, useState} from 'react';
import {Card, Image, Space, Table} from "antd";
import {getAllOrder, getAllOrderUser} from "../server/orderserver";
import dayjs from "dayjs";
import {SearchOrder} from "./SearchOrder";

let MyOrder=()=>
{
    const [orders,setOrders]=useState([]);

    useEffect(
        ()=>
        {
            getAllOrderUser(
                sessionStorage.getItem('UserId'),
                (data)=>setOrders(data)
            )
        },[]
    )

    console.log("orders",orders);

    const expandedRowRender = (record,index) => {
        const columns = [
            {
              title: 'cover',
              dataIndex: ['book','image'],
              key:'img',
                render:(re)=>(<Image src={re} height={50}></Image>)
            },
            {
                title: 'BookName',
                dataIndex: ['book','name'],
                key: 'bookName',
            },
            {
                title: 'BookCount',
                dataIndex: 'bookCount',
                key: 'bookCount',
            },
            {
                title: 'Total price',
                dataIndex: 'totalPrice',
                key: 'totalPrice',
                render: (re)=>(re.toFixed(2))
            },
        ];
        return <Table columns={columns} dataSource={orders[index].orderlist} pagination={false} />;
    };
    const totalColumn = () =>
    {
        const columns=[
            {
                title: 'Book Type Count',
                dataIndex: 'typeCount',
                key: 'typeCount',
            },
            {
                title: 'Total price',
                dataIndex: 'totalPrice',
                key: 'totalPrice',
                render: (record)=>(record.toFixed(2))
            },
            {
                title:'PurchaseTime',
                dataIndex:'purchaseTime',
                key:'purchaseTime',
            }
        ]
        const data=[];
        for(let i=0;i<orders.length;i++)
        {
            data.push(
                {
                    typeCount:orders[i].orderlist.length,
                    totalPrice:orders[i].totalPrice,
                    key:i,
                    purchaseTime:dayjs(orders[i].purchaseTime).format('YYYY-MM-DD HH:mm:ss')
                }
            )
        }

        return(
            <div>
                <SearchOrder callbackOrder={setOrders} isUser={true}></SearchOrder>
                <Table columns={columns} dataSource={data} expandable={
                    {
                        expandedRowRender
                    }
                }></Table>
            </div>
        )
    }




    return totalColumn();
}

export default MyOrder;