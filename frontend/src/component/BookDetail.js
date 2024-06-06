import React, {useEffect} from "react";
import {Button, Image, Layout, message, notification, Space} from "antd";
import {StepBackwardFilled} from "@ant-design/icons";
import {Content, Footer} from "antd/es/layout/layout";

import {Badge,Descriptions} from "antd";
import {Link, useLocation} from "react-router-dom";
import {getBook} from "../server/bookserver";
import {addCart} from "../server/cartsever";
import {checkIsAuth} from "../server/userlogin";
import dayjs from "dayjs";

const {Item}=Descriptions;



const fontstyle={
    fontSize:15
}


export function BookDetail()
{
    const [book,setBook]=React.useState({name:"",author:"",price:0,image:"",publiser:"",introduction:"",status:"",id:0});
    const [usertype,setUserType]=React.useState("INVALID_USER");
    let t=useLocation().pathname.split('/');
    useEffect(
        ()=>
        {
            //console.log(t.at(t.length-1));
            getBook(
                t.at(t.length-1),
                (data)=>{setBook(data)}
            );
        },
        []
    )

    useEffect(
        ()=>
        {
            checkIsAuth(
                sessionStorage.getItem('UserId'),
                ()=>{},
                ()=>{setUserType("USER");},
                ()=>{setUserType("ADMIN");},
                ()=>{setUserType("BAN");}
            )
        }
    )

    const [messageApi, contextHolder] = message.useMessage();
    const success = (message) => {
        messageApi.open({
            type: 'success',
            content: message,
        });
    };

        return (
            <>
                {contextHolder}
            <Layout style={{backgroundColor:"#fff"}}>
                <Link
                    to={"/BookShop"}
                    style={
                        {
                            width:'100px',
                            marginLeft:'20px',
                            marginTop:'20px',
                        }
                    }
                >
                    <Button

                        type="primary"
                        icon={<StepBackwardFilled/>}
                        size="large"
                    >
                        Back
                    </Button>
                </Link>
                <Content>
                    <br/>
                    <Space size={100}>
                        <Image
                            src ={book.image}
                            width={400}
                            height={400}
                        />
                        <Descriptions
                            title="Book Info"
                            bordered
                            column = {1}
                            labelStyle={fontstyle}
                            contentStyle={fontstyle}
                        >
                            <Item label="Name" >{book.name}</Item>
                            <Item label="Author" >{book.author}</Item>
                            <Item label="Type" >{book.type}</Item>
                            <Item label="Price" >{book.price}</Item>
                            <Item label="ISBN" >{book.isbn}</Item>
                            <Item label="Inventory" >{book.inventory}</Item>
                        </Descriptions>
                    </Space>
                    <Descriptions
                        bordered
                        style={{marginLeft:'270px',marginRight:'270px'}}
                    >
                        <Item label="instruction">{book.description}</Item>
                    </Descriptions>
                </Content>
                {
                    usertype==='USER'&&<Space
                        style={{backgroundColor:'#fff',margin:'auto',marginTop:'15px'}}
                        size={30}
                    >
                        <Button
                            type='primary'
                            size='large'
                            onClick={()=> {addCart(book.id, sessionStorage.getItem('UserId'), ()=>success('add this book to carlist successfully'))}}
                        >
                            Add to Cart
                        </Button>
                        <Button type='default' size='large'
                                onClick={
                                    ()=> {
                                        fetch(
                                            "http://localhost:8080/AddOrder",
                                            {
                                                method: 'POST',
                                                headers: {
                                                    'Content-Type': "application/json"
                                                },
                                                body: JSON.stringify(
                                                    {
                                                        UserId:sessionStorage.getItem('UserId'),
                                                        bookId:book.id,
                                                        purchaseTime:new dayjs().format('YYYY-MM-DD HH:mm:ss'),
                                                    }
                                                )
                                            }
                                        )
                                            .then(
                                                (response) => {
                                                    success('add this book to order successfully');
                                                    return response.json();
                                                }
                                            )
                                            .then(
                                                (data) => {
                                                    console.log(data);
                                                }
                                            )
                                            .catch(
                                                (error) => {
                                                    console.error(error);
                                                }
                                            )
                                    }
                                }
                        >
                            Purchase now
                        </Button>
                    </Space>
                }
            </Layout>
            </>
        );
}

export default BookDetail;