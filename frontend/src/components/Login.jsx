import React from 'react';
import './Login.css';
import {useNavigate}   from 'react-router-dom';


function Login(props) {
    const [loginForm, setLoginForm] = React.useState({ username: '', password: '' });
    const [error, setError] = React.useState('');

    const navigate = useNavigate();

    function onChange(event) {
        const { name, value } = event.target;
        setLoginForm(oldForm => ({ ...oldForm, [name]: value }))
    }

    function onSubmit(e) {
        e.preventDefault();
        setError("");
        const body = `username=${loginForm.username}&password=${loginForm.password}`;
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: body
        };
        fetch('/login', options)
            .then(response => {
                if (response.status === 401) {
                    setError("Login failed");
                } else {
                    props.onLogin();
                }
            });
    }

    const goToRegister = () => {
        navigate('/register');
      };

    return (
        <div className="App">   <div className="Login">
            <h1 className='Naslov'>Login</h1>
            <form onSubmit={onSubmit}>
                <div className="FormRow">
                    <label>Username</label>
                    <input name='username' onChange={onChange} value={loginForm.username} required />
                </div>
                <div className="FormRow">
                    <label>Password</label>
                    <input name='password' type="password" onChange={onChange} value={loginForm.password} required />
                </div>
                <div className='error'>{error}</div>
                <div className="button-container">
                    <button type="submit">Login</button>
                    {/* <button onClick={() => window.location.href = '/register'}>Register</button> */}
                    <button onClick={goToRegister}>Register</button> 
                </div>
            </form>
        </div>
        </div>
    )
}

export default Login;
