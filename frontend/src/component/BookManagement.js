import {useEffect, useState} from "react";
import {delBook, getBooks} from "../server/bookserver";
import {Avatar, Button, Layout, Modal, Space, Table} from "antd";
import {BookForm} from "./BookForm";
import {SearchBookBar} from "./SearchBookBar";
import {checkIsAuth} from "../server/userlogin";
import {redirect, useNavigate,useHistory} from "react-router-dom";

let BookManagement=()=>
{
    const [books,setbooks]=useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [initValue, setInitValue] = useState({});//[id,name,author,price,type,image

    const showModal = () => {
        setIsModalOpen(true);
    };
    const handleOk = () => {
        setIsModalOpen(false);
    };
    const handleCancel = () => {
        setIsModalOpen(false);
    };
    useEffect(
        ()=>{
             getBooks((data)=>setbooks(data))
        },[]
    )
    //console.log(books)
    const handleEdit=(record)=>{
        setInitValue(record)
        showModal();
    }
    const handleAdd=()=>
    {
        setInitValue({})
        showModal();
    }
    const columns=[
        {
            title:'cover',
            dataIndex:'image',
            key:'img',
            render:(re)=>(<Avatar src={re} height={50}></Avatar>)
        },
        {
            title:'name',
            dataIndex:'name',
            key:'name'
        },
        {
            title:'author',
            dataIndex:'author',
            key:'author'
        },
        {
            title:'price',
            dataIndex:'price',
            key:'price',
        },
        {
            title:'type',
            dataIndex:'type',
            key:'type',
        },
        {
            title: 'action',
            key: 'action',
            render:(_,record)=>{
                return(
                    <Space>
                        <Button
                            type = 'primary'
                            onClick={()=>handleEdit(record)}
                        >
                            Edit
                        </Button>
                        <Button
                            danger={true}
                            onClick={()=>{delBook(record.id,()=>getBooks((data)=>setbooks(data)))}}
                        >
                            Delete
                        </Button>
                    </Space>
                )
            }
        }
    ]
    return(
        <>
            <div style={{ display: 'flex', justifyContent: 'flex-start',marginBottom:'10px',marginLeft:'10px',marginTop:'10px'}}>
                <Button onClick={handleAdd} type='primary'>
                    Add Book
                </Button>
            </div>
            <SearchBookBar callbackBook={setbooks}>

            </SearchBookBar>
            <Table columns={columns} dataSource={books}>
            </Table>
            <BookForm
                handleOk={handleOk}
                handleCancel={handleCancel}
                IsOpen={isModalOpen}
                returnBook={setbooks}
                initValue={initValue}
            >
            </BookForm>

        </>
    )
}

export default BookManagement;