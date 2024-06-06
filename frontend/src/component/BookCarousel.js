import React from "react";

import {Carousel, Image} from "antd";
import book1 from "../assets/carousel/book1.jpg"
import book2 from "../assets/carousel/book2.jpg"
import book3 from "../assets/carousel/book3.jpg"
import book4 from "../assets/carousel/book4.jpg"

let BookCarousel=()=>
{
    return (
        <Carousel
            effect="fade"
            autoplay
            style={
                {
                    width:'90%',
                    margin:"auto",
                }
            }
        >
            <div>
                <Image src ={book1} preview={false}/>
            </div>
            <div>
                <Image src={book2} preview={false}/>
            </div>
            <div>
                <Image src={book3} preview={false}/>
            </div>
            <div>
                <Image src={book4} preview={false}/>
            </div>
        </Carousel>
    );
}

export default BookCarousel;