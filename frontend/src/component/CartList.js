
import React, {useEffect, useState} from "react";
import {Image, Space, Table, Tag} from 'antd';
import {delCart, getCarts} from "../server/cartsever";
const { Column, ColumnGroup } = Table;

let CartList=(props)=>
{
    console.log('Carlist update');

    console.log("carts:",props.carts);

    return (
        <Table dataSource={
            props.carts.map(
                (item,index)=>
                {
                    return(
                        {
                            key:index,
                            bookimg:<Image
                                src={item.book.image}
                                height={100}
                            />,
                            bookname:item.book.name,
                            bookAmount:item.count,
                            bookprice:item.totalprice,
                            id:item.book.id,
                        }
                    )
                }
            )
        }>
            <ColumnGroup title="Book">
                <Column title="Cover" dataIndex="bookimg" key="bookimg" />
                <Column title="Name" dataIndex="bookname" key="bookname" />
            </ColumnGroup>
            <Column title="Amount" dataIndex="bookAmount" key="BookAmount" />
            <Column title="Price" dataIndex="bookprice" key="Bookprice" render={(re)=>(re.toFixed(2)) }/>
            <Column
                title="Action"
                key="action"
                render={(_, record) => (
                    <Space size="middle">
                        <a
                            onClick={()=>{delCart(record.id,sessionStorage.getItem('UserId'),(data)=>props.onclickDelete(data))}}
                        >Delete
                        </a>
                    </Space>
                )}
            />
        </Table>
    );
}

export default CartList;