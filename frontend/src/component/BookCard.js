import React from "react";
import {Card} from "antd";
import {Link, Router} from "react-router-dom";

const {Meta} =Card;

let BookCard=(props)=>
{
    const {bookInfo} = props;
    const dest="/BookDetail/"+bookInfo.id.toString();
    return (
        <Link to={dest}>
            <Card
                hoverable
                style={{
                    width: 240,
                }}
                cover={<img alt={bookInfo.name} src={bookInfo.image} />}
            >
                <Meta
                    title={bookInfo.name}
                    description={'￥'+bookInfo.price}
                />
            </Card>
        </Link>
    )
}

export default BookCard;