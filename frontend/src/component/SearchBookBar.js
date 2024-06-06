import Search from "antd/es/input/Search";
import {searchBook} from "../server/bookserver";

export const SearchBookBar=(props)=>
{
    const {callbackBook}=props;

    const handleSearch=(value)=>
    {
        searchBook(value,(data)=>callbackBook(data))
    }

    return(
        <>
            <Search
                style={{width:'70%',marginBottom:'10px'}}
                size='large'
                allowClear
                placeholder='search book'
                onSearch={handleSearch}
                onChange={(e)=>{handleSearch(e.target.value)}}
            >

            </Search>
        </>
    )
}