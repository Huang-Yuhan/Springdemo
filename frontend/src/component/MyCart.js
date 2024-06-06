import React, {useEffect, useState} from "react";
import {Button, Layout, message, Space, Typography} from "antd";
import CartList from "./CartList";
import {CartToOrder} from "../server/orderserver";
import {getCarts} from "../server/cartsever";
const {Title} =Typography;

const MyCart=()=>{
    const [messageApi, contextHolder] = message.useMessage();
    const [carts,setCarts]=useState([]);
    const success = () => {
        messageApi.open({
            type: 'success',
            content: 'add carlist to order successfully',
        });
    };
    const fail = () => {
        messageApi.open({
            type: 'error',
            content: 'you need to add carlist first',
        });
    }
    console.log("\nMyCart update\n");

    useEffect(
        ()=>
        {
            getCarts(
                sessionStorage.getItem('UserId'),
                (data) => {setCarts(data)}
            );
        },[]
    )
    return (
        <>
            {contextHolder}
            <Layout direction='vertical' style={{background:'white'}}>
                <Title
                    style={
                        {
                            fontFamily:'Comic Sans MS'
                        }
                    }
                >
                    My Shopping Cart
                </Title>
                <CartList
                    carts={carts}
                    onclickDelete={(data)=>setCarts(data)}
                />
                <Space
                    style={{margin:'auto', display: 'flex', justifyContent: 'flex-start',marginTop:'10px'}}
                >
                    <Button
                        size='large'
                        type='primary'
                        onClick={
                            ()=> {
                                if(carts.length===0)fail();
                                else
                                    CartToOrder(
                                        sessionStorage.getItem('UserId'),
                                        () => {
                                            success();
                                            getCarts(
                                                sessionStorage.getItem('UserId'),
                                                (data)=>{setCarts(data)});
                                        }
                                    )
                            }
                        }
                    >
                        Take Order
                    </Button>
                </Space>
            </Layout>
        </>
    );
}

export default MyCart;