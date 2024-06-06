import React, {useEffect, useState} from "react";
import {getAllOrderAdmin} from "../server/orderserver";
import {Image, Table} from "antd";
import dayjs from "dayjs";
import {SearchOrder} from "./SearchOrder";
import {checkIsAuth} from "../server/userlogin";
import {redirect} from "react-router-dom";

export const OrderManagement=()=>
{
    const [orders,setOrders]=useState([]);

    useEffect(
        ()=>
        {
            getAllOrderAdmin(
                (data)=>setOrders(data)
            )
        },[]
    )


    console.log("admin orders",orders)

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
            },
            {
                title: 'User',
                dataIndex: 'userName',
                key: 'userName',
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
                    purchaseTime:dayjs(orders[i].purchaseTime).format('YYYY-MM-DD HH:mm:ss'),
                    userName:orders[i].userName
                }
            )
        }

        return(
            <div>
                <SearchOrder callbackOrder={setOrders} isUser={false}></SearchOrder>
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