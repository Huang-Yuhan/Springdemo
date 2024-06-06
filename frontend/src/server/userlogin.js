import {message} from "antd";

export const getUserName=(
    id,
    callback,
)=>
{
    fetch(
        "http://localhost:8080/GetUserName",
        {
            method: 'POST',
            headers:{
                'Content-Type': "application/json"
            },
            body: JSON.stringify({Id:id})
        }
    )
        .then(
            (response)=>
            {
                //console.log(response);
                return response.json();
            },
        )
        .then(
            (data)=>
            {
                console.log(data)
                sessionStorage.setItem("UserName",data);
                if(callback!==null&&callback!==undefined)callback();
            }
        )

}

export const getUserId=(
    account,
    password,
    callback
)=>
{
    fetch(
        "http://localhost:8080/Login",
        {
            method: 'POST',
            headers: {
                'Content-Type': "application/json"

            },
            credentials: 'include',
            body: JSON.stringify({Account:account,Password:password})
        }
    )
        .then(
            (response)=>
            {
                //获取cookie
                console.log("response:",response)
                console.log("cookie:",response.headers.get('Set-Cookie'));
                document.cookie=response.headers.get('Set-Cookie');
                return response.json();
            },
        )
        .then(
            (data)=>
            {
                console.log(data)
                sessionStorage.setItem("UserId",data);
                console.log("get response from backend \n UserId--->",data.toString());
                callback(data);
            }
        )

}

export const register=(
    account,
    password,
    email,
    callback
)=>
{
    fetch(
        "http://localhost:8080/Register",
        {
            method: 'POST',
            headers: {
                'Content-Type': "application/json"
            },
            credentials: 'include',
            body: JSON.stringify({Account:account,Password:password,Email:email})
        }
    )
        .then(
            (response)=>
            {
                return response.json();
            },
        )
        .then(
            (data)=>
            {
                sessionStorage.setItem("UserId",data);
                console.log("get response from backend \n UserId--->",data.toString());
                callback(data);
            }
        )

}

export const checkIsAuth=(
    userID,
    INVALIDDO,
    USERDO,
    ADMINDO,
    BANDO,
)=>
{
    fetch(
        "http://localhost:8080/CheckUserAuth",
        {
            method: 'POST',
            headers: {
                'Content-Type': "application/json"
            },
            credentials: 'include',
            body: JSON.stringify({UserId:userID})
        }
    )
        .then(
            (response)=>
            {
                return response.json();
            },
        )
        .then(
            (data)=>
            {
                sessionStorage.setItem("UserType",data);
                if(data==="INVALID_USER")INVALIDDO();
                if(data==="USER")USERDO();
                if(data==="ADMIN")ADMINDO();
                if(data==='BAN')BANDO();
                console.log("UserID:",userID,"\nUserType:",data);
            }
        )
}

export const deleteUser=(
    userId,
    callback
)=>
{
    fetch(
        "http://localhost:8080/Delete",
        {
            method: 'POST',
            headers: {
                'Content-Type': "application/json"
            },
            body: JSON.stringify({UserId:userId})
        }
    )
        .then(
            (response)=>
            {
                return response.json();
            },
        )
        .then(
            (data)=>
            {
                console.log('after delete',data)
                callback(data)
            }
        )
}

export const getUesrById=(userId,callback)=>{
    fetch(
        "http://localhost:8080/GetUserById",
        {
            method: 'POST',
            headers: {
                'Content-Type': "application/json"
            },
            body: JSON.stringify({UserId:userId})
        }
    )
        .then(
            (response)=>
            {
                return response.json();
            }
        )
        .then(
            (data)=>
            {
                console.log(data)
                callback(data)
            }
        )
        .then(
            (error)=>{
                console.log(error)
            }
        )
}

export const updateUserInfo=(value,callback)=>{
    fetch(
        "http://localhost:8080/UpdateUserInfo",
        {
            method: 'POST',
            headers: {
                'Content-Type': "application/json"
            },
            body: JSON.stringify(value)
        }
    )
        .then(
            (response)=>
            {
                return response.json();
            }
        )
        .then(
            (data)=>
            {
                console.log(data)
                callback(data)
            }
        )
}

export const logout=()=>{
    fetch('http://localhost:8080/Logout',{credentials:'include'}).then((res)=>{
        sessionStorage.setItem('UserId'," ");
        return res.json();
    }).then((data)=>{
        message.success('Logout Success');
        message.info('View Time: '+(data/1000)+'s');
    })
}