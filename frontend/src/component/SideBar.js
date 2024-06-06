import React, {useEffect, useState} from "react";
import {
    ShoppingOutlined,
    ShoppingCartOutlined,
    UserOutlined,
    MoneyCollectFilled, SettingOutlined, BarChartOutlined,
} from "@ant-design/icons";
import {Image, Menu, Space} from "antd";
import {Link} from "react-router-dom";
import logo from "../assets/img/BookStoreLogo.png"
import {checkIsAuth} from "../server/userlogin";
function getItem(label, key, icon, children, type) {
    return {
        key,
        icon,
        children,
        label,
        type,
    };
}

const Useritems=[
    getItem(
        'Books',
        '1',
        <Link to={'/BookShop'}>
            <ShoppingOutlined/>
        </Link>
    ),
    getItem('My Cart',
        '2',
        <Link to={'/MyCart'}>
            <ShoppingCartOutlined/>
        </Link>
    ),
    getItem('My Order',
        '3',
        <Link to={'/MyOrder'}>
            <MoneyCollectFilled/>
        </Link>
    ),
    getItem('My profile',
        '4',
        <Link to={'UserProfile'}>
            <UserOutlined/>
        </Link>
    ),
    getItem('Book Statistics',
        '5',
        <Link to={'/BookStatistics'}>'
            <BarChartOutlined />
            </Link>
    ),
];

const AdminItems=[
    getItem(
        'Books',
        '1',
        <Link to={'/BookShop'}>
            <ShoppingOutlined/>
        </Link>
    ),
    getItem('My profile',
        '4',
        <Link to={'UserProfile'}>
            <UserOutlined/>
        </Link>
    ),
    getItem('User management',
        '5',
        <Link to={'UserManagement'}>
            <SettingOutlined />
        </Link>
    ),
    getItem('Book management',
        '6',
        <Link to={'BookManagement'}>
            <SettingOutlined />
        </Link>
    ),
    getItem('Order management',
        '7',
        <Link to={'OrderManagement'}>
            <SettingOutlined />
        </Link>
    ),
    getItem(
      'Book Statistics',
        '8',
        <Link to={'/AdminBookStatistics'}>
            <BarChartOutlined />
        </Link>
    ),
    getItem(
        'User Statistics',
        '9',
        <Link to={'/AdminUserStatistics'}>
            <BarChartOutlined />
        </Link>
    )

];

let SideBar=()=>
{
    const [userId,setUserId]=useState(sessionStorage.getItem('UserId'));
    const [sideLis,setSideLis]=useState(Useritems);
    useEffect(
        ()=>checkIsAuth(
            userId,
            ()=>{},
            ()=>setSideLis(Useritems),
            ()=>setSideLis(AdminItems),
        )
    )
    return (
        <Space direction='vertical'>
            <Image
                src={logo}
                preview={false}
            />
            <Menu
                items={sideLis}
                theme='light'
            />

        </Space>

    );
}


export default SideBar;