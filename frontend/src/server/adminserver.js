import dayjs from "dayjs";

export const getUsers=(
    callback
)=>
{
    fetch(
        'http://localhost:8080/GetUsers',
        {
            method:'POST',
        }
    )
        .then(
            (response)=> response.json()
        )
        .then(
            (data)=>callback(data)
        )
}

export const recoverUser=(
    userid,
    callback
)=>
{
    console.log("ready to send userid:",userid)

    fetch(
        'http://localhost:8080/Recover',
        {
            method:'POST',
            headers:{
                'Content-Type': "application/json"
            },
            body:JSON.stringify({UserId:userid})
        }
    ).then(
        (response)=>response.json()
    ).then(
        (data)=>callback(data)
    )
}

export const banUser=(
    userid,
    callback,
)=>
{
    console.log("ready to send userid:",userid)
    fetch(
        'http://localhost:8080/Ban',
        {
            method:'POST',
            headers:{
                'Content-Type': "application/json"
            },
            body:JSON.stringify({UserId:userid})
        }
    ).then(
        (response)=>response.json()
    ).then(
        (data)=>callback(data)
    )
}

export const getUserStatistics=(date,callback)=>{
    if(date===null)date=[null,null]
    if(date[0]===null||date[0]===undefined)date[0]=new dayjs('1970-01-01 00:00:00');
    if(date[1]===null||date[1]===undefined)date[1]=new dayjs('2100-01-01 00:00:00');
    fetch(
        "http://localhost:8080/AdminUserStatistics",
        {
            method: 'POST',
            headers: {
                'Content-Type': "application/json"
            },
            body: JSON.stringify({
                    beginTime:date[0].format('YYYY-MM-DD HH:mm:ss'),
                    endTime:date[1].format('YYYY-MM-DD HH:mm:ss'),
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