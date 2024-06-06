import dayjs from "dayjs";


export const getBooks=(callBack)=>
{
    fetch("http://localhost:8080/getBooks",
        {
            method:'POST',
            body:JSON.stringify({search:null}),
            headers:
                {
                    'Content-Type':"application/json"
                }
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
                for(let i = 0;i<data.length;i++)
                    data[i].key=data[i].id;
                //console.log(data);
                callBack(data);
            }
        )
        .catch(
            (error)=>{
                console.log('Error:',error);
            }
        )
}

export const getBook=(Id,callBack)=>
{
    fetch("http://localhost:8080/getBook?id="+Id)
        .then(
            (response)=>
            {
                return response.json();
            }
        )
        .then(
            (data)=>
            {
                for(let i = 0;i<data.length;i++)
                    data[i].key=data[i].id;
                callBack(data);
            }
        )
        .catch(
            (error)=>
            {
                console.log('Error:',error);
            }
        )
}

export const delBook=(Id,callBack)=>{
    fetch("http://localhost:8080/delBook",
        {
            method:'POST',
            body:JSON.stringify({id:Id}),
            headers:
                {
                    'Content-Type':"application/json"
                }
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
                for(let i = 0;i<data.length;i++)
                    data[i].key=data[i].id;
                callBack(data);
            }
        )
        .catch(
            (error)=>
            {
                console.log('Error:',error);
            }
        )
}

export const addBook=(book,callBack)=>{
    fetch("http://localhost:8080/addBook",
        {
            method:'POST',
            body:JSON.stringify(book),
            headers:
                {
                    'Content-Type':"application/json"
                }
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
                for(let i = 0;i<data.length;i++)
                    data[i].key=data[i].id;
                callBack(data);
            }
        )
        .catch(
            (error)=>{
                console.log('Error:',error);
            }
        )
}

export const searchBook=(prefix,callBack)=>{
    fetch(
        "http://localhost:8080/searchBooks",
        {
            method:'POST',
            body:JSON.stringify({prefix:prefix}),
            headers:
                {
                    'Content-Type':"application/json"
                }
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
                for(let i = 0;i<data.length;i++)
                    data[i].key=data[i].id;
                callBack(data);
            }
        )
        .catch(
            (error)=>{
                console.log('Error:',error);
            }
        )
}

export const searchBookByDate=(userId,date,callBack)=>{
    if(date===null)date=[null,null]
    if(date[0]===null||date[0]===undefined)date[0]=new dayjs('1970-01-01 00:00:00');
    if(date[1]===null||date[1]===undefined)date[1]=new dayjs('2100-01-01 00:00:00');
    fetch(
        "http://localhost:8080/searchBooksByDate",
        {
            method:'POST',
            body:JSON.stringify({
                beginTime:date[0].format('YYYY-MM-DD HH:mm:ss'),
                endTime:date[1].format('YYYY-MM-DD HH:mm:ss'),
                UserId:userId
            }),
            headers:
                {
                    'Content-Type':"application/json"
                }
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
                for(let i = 0;i<data.length;i++)
                    data[i].key=data[i].id;
                callBack(data);
            }
        )
        .catch(
            (error)=>{
                console.log('Error:',error);
            }
        )
}

export const searchAdminBook=(date,callBack)=>{
    if(date===null)date=[null,null]
    if(date[0]===null||date[0]===undefined)date[0]=new dayjs('1970-01-01 00:00:00');
    if(date[1]===null||date[1]===undefined)date[1]=new dayjs('2100-01-01 00:00:00');
    fetch(
        "http://localhost:8080/AdminBookStatistics",
        {
            method:'POST',
            body:JSON.stringify({
                beginTime:date[0].format('YYYY-MM-DD HH:mm:ss'),
                endTime:date[1].format('YYYY-MM-DD HH:mm:ss'),
            }),
            headers:
                {
                    'Content-Type':"application/json"
                }
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
                for(let i = 0;i<data.length;i++)
                    data[i].key=data[i].id;
                callBack(data);
            }
        )
        .catch(
            (error)=>{
                console.log('Error:',error);
            }
        )
}