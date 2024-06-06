import {useEffect, useState} from "react";
import {Avatar, DatePicker, Space, Table,Card,Col,Row,Statistic} from "antd";
import {searchBookByDate} from "../server/bookserver";

export const UserStatistics = () => {

    const [books,setBooks]=useState([]);

    useEffect(
        ()=>{
            searchBookByDate(sessionStorage.getItem('UserId'),null,(data)=>setBooks(data))
        },[]
    )

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
            title:'count',
            dataIndex:'count',
            key:'count'
        },
        {
            title:'total price',
            dataIndex:'totalPrice',
            key:'totalPrice',
            render:(re)=>(re.toFixed(2))
        }
    ]

    const tableDate=(books)=>
    {
        let lis=[];
        for(let i = 0;i<books.length;i++)
        {
            lis.push(
                {
                    key:books[i].id,
                    name:books[i].name,
                    image:books[i].image,
                    count:books[i].count,
                    totalPrice:books[i].price*books[i].count
                }
            )
        }
        return lis;
    }

    const handleDate=(dateValue,dateString)=>
    {
        searchBookByDate(sessionStorage.getItem('UserId'),dateValue,(data)=>setBooks(data))
    }
    console.log(tableDate(books))
    const total=()=>
    {
        let sumPrice,sumCount;
        sumPrice=0;
        sumCount=0;
        for(let i = 0;i<books.length;i++)
        {
            sumPrice+=books[i].price*books[i].count;
            sumCount+=books[i].count;
        }
        return (
            <Row gutter={16}>
                <Col span={12}>
                    <Card bordered={false}>
                        <Statistic
                            title="Total Price"
                            value={sumPrice}
                            precision={2}
                            valueStyle={{
                                color: '#3f8600',
                            }}
                        />
                    </Card>
                </Col>
                <Col span={12}>
                    <Card bordered={false}>
                        <Statistic
                            title="Total Count"
                            value={sumCount}
                            precision={0}
                            valueStyle={{
                                color: '#91c98a',
                            }}
                        />
                    </Card>
                </Col>
            </Row>
        )
    }
    return (
        <Space direction='vertical' style={{width:'100%',}}>
            <DatePicker.RangePicker
                size='large'
                style={{width:'80%',marginTop:'10px'}}
                showTime
                onChange={handleDate}
                onCalendarChange={handleDate}
            >

            </DatePicker.RangePicker>
            <Table columns={columns} dataSource={tableDate(books)} pagination={false} rowKey={record=>record.id}>
            </Table>
            {total()}
        </Space>

    )
}