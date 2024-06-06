import {
    AutoComplete,
    Button,
    Cascader,
    Checkbox,
    Col,
    Form,
    Input,
    InputNumber, Modal,
    Row,
    Select,
} from 'antd';
import { useState } from 'react';
import {addBook} from "../server/bookserver";
const { Option } = Select;
const formItemLayout = {
    labelCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 8,
        },
    },
    wrapperCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 16,
        },
    },
};
const tailFormItemLayout = {
    wrapperCol: {
        xs: {
            span: 24,
            offset: 0,
        },
        sm: {
            span: 16,
            offset: 8,
        },
    },
};
export const BookForm = (props) => {

    const [form] = Form.useForm();
    const [initValue, setInitValue] = useState({});//[id,name,author,price,type,image
    const onFinish = (values) => {
        let formData=values;
        formData.id=initValue.id;
        if(formData.id===undefined||formData.id===null)formData.id=0;
        addBook(formData,(data)=>props.returnBook(data))
        console.log('Received values of form: ', formData);
        props.handleCancel();
        form.resetFields();
    };
    if(props.initValue!==undefined&&props.initValue!==null&&props.initValue!==initValue)
    {
        setInitValue(props.initValue)
        form.resetFields();
        form.setFieldsValue(props.initValue)
    }
    return (
        <Modal title="Add new Book" open={props.IsOpen} onCancel={props.handleCancel} onOk={props.handleOk} footer={null}>
            <Form
                {...formItemLayout}
                form={form}
                name="addBookForm"
                onFinish={onFinish}
                style={{
                    maxWidth: 600,
                }}
                scrollToFirstError
            >
                <Form.Item
                    name="name"
                    label="Book Name"
                    rules={[
                        {
                            required: true,
                            message: 'Please input book name!',
                        },
                    ]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    name="author"
                    label="Author"
                    rules={[
                        {
                            required: true,
                            message: 'Please input author!',
                        },
                    ]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    name="price"
                    label="Price"
                    rules={[
                        {
                            required: true,
                            message: 'Please input Price!',
                        },
                    ]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    name="type"
                    label="Type"
                    rules={[
                        {
                            required: true,
                            message: 'Please input Type!',
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    name="image"
                    label="Image"
                    rules={[
                        {
                            required: true,
                            message: 'Please input Image!',
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    name="isbn"
                    label="ISBN"
                    rules={[
                        {
                            required: true,
                            message: 'Please input ISBN!',
                        }
                    ]}
                >
                    <Input/>
                </Form.Item>
                <Form.Item
                    name="description"
                    label="Description"
                    rules={[
                        {
                            required: true,
                            message: 'Please input Description!',
                        },
                    ]}
                >
                    <Input.TextArea showCount maxLength={500} size='large'/>
                </Form.Item>

                <Form.Item
                    name = "inventory"
                    label="Inventory"
                    rules={[
                        {
                            required: true,
                            message: 'Please input Inventory!',
                        }
                    ]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item {...tailFormItemLayout}>
                    <Button type="primary" htmlType="submit">
                        Add
                    </Button>
                </Form.Item>
            </Form>
        </Modal>
    );
};
