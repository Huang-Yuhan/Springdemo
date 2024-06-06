import React, {Component} from 'react';
import LoginBox from "../component/LoginBox";
import '../css/login.css';

class LoginView extends Component {
    componentDidMount() {
        localStorage.clear();
    }

    render() {
        return (
                <LoginBox/>
        );
    }
}

export default LoginView;