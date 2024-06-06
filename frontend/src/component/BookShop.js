import React, {useEffect, useState} from "react";
import BookCarousel from "./BookCarousel";
import {Layout, Space} from "antd";
import BookList from "./BookList";
import {getBooks} from "../server/bookserver";
import {SearchBookBar} from "./SearchBookBar";

const style=
{
    background:'#fff'
}

let BookShop=()=>
{
    const [books,setBooks]=useState();

    useEffect(
        ()=>
        {
            getBooks(
                (data)=>setBooks(data)
            )
        },
        []
    )
    console.log(books)
    return(
        <Layout
            style={style}
        >
            <br/>
            <Layout style={{background:'#fff',marginLeft:'20%'}}>
                <SearchBookBar callbackBook={setBooks} ></SearchBookBar>
            </Layout>
            <br/>
            <BookCarousel/>
            <BookList books={books}/>
        </Layout>
    );
}

export default BookShop;