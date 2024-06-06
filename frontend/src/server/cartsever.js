
export  const getCarts=(
    UserId,
    callback
)=>
{
    fetch(
        'http://localhost:8080/getCartLists',
        {
            method:'POST',
            headers: {
                'Content-Type': "application/json"
            },
            body:JSON.stringify({UserId:UserId}),
        }
    ).then(
            (response)=>
            {
                return response.json();
            },
        ).then(
            (data)=>
            {
                callback(data)
                console.log(data);
            }
        )
}

export const delCart=(
    bookId,
    UserId,
    callback
)=>
{
    fetch(
        'http://localhost:8080/DelCartLists',
        {
            method:'POST',
            body:JSON.stringify({UserId:UserId,bookId:bookId}),
            headers: {
                'Content-Type': "application/json"
            },
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
            console.log(data);
            callback(data);
        }
    )
}

export const addCart=(
    bookId,
    UserId,
    callback,
)=>
{
    fetch(
        'http://localhost:8080/AddCartLists',
        {
            method:'POST',
            body:JSON.stringify({bookId:bookId,UserId:UserId}),
            headers: {
                'Content-Type': "application/json"
            },
        }
    )
        .then(
        (response)=>
        response.json()
    )
        .then(
        (data)=>
        {
            console.log(data);
            callback();
        }
    )
}