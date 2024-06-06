import dayjs from "dayjs";

export const CartToOrder=(
    UserId,
    callback
)=>
{
    fetch(
        "http://localhost:8080/CartToOrder",
        {
            method: 'POST',
            headers: {
                'Content-Type': "application/json"
            },
            body: JSON.stringify({UserId:UserId,purchaseTime:new dayjs().format('YYYY-MM-DD HH:mm:ss')})
        }
    ).then(
            (response)=>
            {
                callback();
                console.log (response);
            },
        )
}

export const getAllOrderUser=(
    userId,
    callback
)=>
{
    fetch(
        "http://localhost:8080/MyOrder",
        {
            method: 'POST',
            headers: {
                'Content-Type': "application/json"
            },
            body: JSON.stringify({UserId:userId})
        }
    ).then(
            (response)=>
            response.json()
        )
        .then(
            (data)=>
            {
                console.log(data)
                callback(data)
            }
        )
}

export const searchOrderUser=(date,prefix,userid,callback)=>{
    if(date===null)date=[null,null]
    if(date[0]===null||date[0]===undefined)date[0]=new dayjs('1970-01-01 00:00:00');
    if(date[1]===null||date[1]===undefined)date[1]=new dayjs('2100-01-01 00:00:00');
    fetch(
        "http://localhost:8080/SearchOrderUser",
        {
            method: 'POST',
            headers: {
                'Content-Type': "application/json"
            },
            body: JSON.stringify({
                    beginTime:date[0].format('YYYY-MM-DD HH:mm:ss'),
                    endTime:date[1].format('YYYY-MM-DD HH:mm:ss'),
                    prefix:prefix,
                    UserId:userid
                }
            )
        }
    ).then(
        (response)=>
            response.json()
    )
        .then(
            (data)=>
            {
                console.log(data)
                callback(data)
            }
        )
}

export const searchOrderAdmin=(date,prefix,callback)=>{
    if(date===null)date=[null,null]
    if(date[0]===null||date[0]===undefined)date[0]=new dayjs('1970-01-01 00:00:00');
    if(date[1]===null||date[1]===undefined)date[1]=new dayjs('2100-01-01 00:00:00');
    fetch(
        "http://localhost:8080/SearchOrderAdmin",
        {
            method: 'POST',
            headers: {
                'Content-Type': "application/json"
            },
            body: JSON.stringify({
                    beginTime:date[0].format('YYYY-MM-DD HH:mm:ss'),
                    endTime:date[1].format('YYYY-MM-DD HH:mm:ss'),
                    prefix:prefix,
                }
            )
        }
    ).then(
        (response)=>
            response.json()
    )
        .then(
            (data)=>
            {
                console.log(data)
                callback(data)
            }
        )
}

export const getAllOrderAdmin=(
    callback
)=>{
    fetch(
        "http://localhost:8080/GetAllOrder",
        {
            method: 'POST',
            headers: {
                'Content-Type': "application/json"
            },
            body: JSON.stringify({})
        }
    ).then(
        (response)=>
            response.json()
    )
        .then(
            (data)=>
            {
                console.log(data)
                callback(data)
            }
        )
}