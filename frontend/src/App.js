import React from "react";
import HomeView from "./View/HomeView";
import {createBrowserRouter, redirect, RouterProvider} from "react-router-dom";
import ErrorPage from "./error-page";
import BookShop from "./component/BookShop";
import MyCart from "./component/MyCart";
import {UserProfile} from "./component/UserProfile";
import BookDetail from "./component/BookDetail";
import LoginView from "./View/LoginView";
import MyOrder from "./component/MyOrder";
import UserManagement from "./component/UserManagement";
import BookManagement from "./component/BookManagement";
import {OrderManagement} from "./component/OrderManagement";
import {UserStatistics} from "./component/UserStatistics";
import {checkIsAuth} from "./server/userlogin";
import {AdminBookStatistics} from "./component/AdminBookStatistics";
import {AdminUserStatisitcs} from "./component/AdminUserStatisitcs";

const onlyAdmin=async ()=>
{
  checkIsAuth(
      sessionStorage.getItem('UserId'),
      ()=>redirect('/login'),
       ()=>redirect('/BookShop'),
       ()=>null,
  )
}

function initRouter()
{
  let result=[
      {
        path:'/',
        element:<HomeView/>,
        errorElement:<ErrorPage/>,
        children:[
          {
            path:"/BookShop",
            element:<BookShop/>,
          },
          {
            path: "/MyCart",
            element: <MyCart/>
          },
          {
            path:"/UserProfile",
            element:<UserProfile/>
          },
          {
            path:"/BookDetail/:DetailId",
            element:<BookDetail/>,
          },
          {
            path:"/MyOrder",
            element: <MyOrder/>,
          },
          {
            path:'/UserManagement',
            element: <UserManagement/>,
          },
          {
            path:'/BookManagement',
            element: <BookManagement/>,
          },
          {
            path:'/OrderManagement',
            element: <OrderManagement/>,
          },
          {
            path  :'/BookStatistics',
            element:<UserStatistics/>
          },
          {
            path:'/AdminBookStatistics',
            element: <AdminBookStatistics/>
          },
          {
            path:'/AdminUserStatistics',
            element: <AdminUserStatisitcs/>
          }
        ],
    },
    {
      index:true,
      element:<LoginView/>
    },
    {
      path: '/Login',
      element: <LoginView/>
    },
  ];
  console.log(result);
  return createBrowserRouter(result);
}


const App = () => {
  const router=initRouter();
  return (
      <RouterProvider router={router}/>
  );
}
export default App;