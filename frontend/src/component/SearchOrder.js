import {DatePicker} from "antd";
import Search from "antd/es/input/Search";
import {useState} from "react";
import {searchOrderAdmin, searchOrderUser} from "../server/orderserver";

export const SearchOrder=(props)=>
{
    const {callbackOrder,isUser}=props;
    const [date,setDate]=useState([null,null]);
    const [prefix,setPrefix]=useState('');
    const handleSearch=(value)=>{
        setPrefix(value)
        if(isUser)searchOrderUser(date,prefix,sessionStorage.getItem('UserId'),(data)=>callbackOrder(data))
        else searchOrderAdmin(date,prefix,(data)=>callbackOrder(data))
    }
    const handleDate=(dateValue,dateString)=>
    {
        setDate(dateValue)
        if(isUser)searchOrderUser(date,prefix,sessionStorage.getItem('UserId'),(data)=>callbackOrder(data))
        else searchOrderAdmin(date,prefix,(data)=>callbackOrder(data))
    }
    return(
        <>
            <DatePicker.RangePicker
                showTime
                size='large'
                style={{width:'70%',marginBottom:'10px',marginTop:'10px'}}
                onChange={handleDate}
                onCalendarChange={handleDate}
            >
            </DatePicker.RangePicker>
            <Search
                style={{width:'70%',marginBottom:'10px'}}
                size='large'
                allowClear
                placeholder='search order with detailed book name'
                onSearch={handleSearch}
                onChange={(e)=>{handleSearch(e.target.value)}}
            >

            </Search>
        </>
    )
}