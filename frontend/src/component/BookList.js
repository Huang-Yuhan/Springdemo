import React, {useEffect, useState} from "react";
import BookCard from "./BookCard";
import {Layout, Space} from "antd";
import {Content} from "antd/es/layout/layout";
import {getBooks} from "../server/bookserver";

/*class BookList extends React.Component
{
    constructor(props) {
        super(props);
        this.state={books:[]}
    }
    componentDidMount() {
        getBooks((data)=>{this.setState({books:data})});
    }

    render() {

        console.log(this.state.books);
        let List=[];
        let listSize=this.state.books.length;
        for(let i=0;i<listSize;i++) {
            List.push(
                <BookCard
                    bookInfo={this.state.books[i]}
                />
            );
        }
        return (
            <Content>
                <Space
                    wrap={true}
                    a
                >
                    {List}
                </Space>
            </Content>

        );
    }
}*/

let BookList=(props)=>
{
    const [list,setList]=useState([])

    const initialBookList=(books)=>
    {
        let lis=[];
        if(books!==null&&books!==undefined)
        for(let i=0;i<books.length;i++)
            lis.push(
                <BookCard
                    key={i}
                    bookInfo={books[i]}
                >

                </BookCard>
            )
        setList(lis)
    }

    useEffect(
        ()=>initialBookList(props.books),
        [props]
    )

    return (
        <Content>
            <Space
                wrap={true}
            >
                {list}
            </Space>
        </Content>

    );
}

export default BookList;