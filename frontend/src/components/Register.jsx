import React, { useState } from "react";
import "./Register.css";
import { useNavigate } from "react-router-dom";

function Register(props) {
  const [registerForm, setRegisterForm] = React.useState({
    username: "",
    email: "",
    password: "",
    type: "",
    naziv: "",
    adresa: "",
    poveznica: "",
    clanarina: "",
  });
  const [error, setError] = React.useState("");
  const [isChecked, setIsChecked] = useState(false);
  const [isPaid, setIsPaid] = useState(false);

  const navigate = useNavigate();

  function onChange(event) {
    const { name, value, type, checked } = event.target;
    if (type === "checkbox" && name === "admin") {
      setRegisterForm((oldForm) => ({ ...oldForm, [name]: checked }));
      setIsChecked(checked);
    } else if (type === "checkbox" && name === "clanarina") {
      setRegisterForm((oldForm) => ({ ...oldForm, [name]: checked }));
      setIsPaid(checked);
    } else {
      setRegisterForm((oldForm) => ({ ...oldForm, [name]: value }));
    }
  }

  function onSubmit(e) {
    e.preventDefault();
    setError("");
    if (isChecked) {
      const body = `username=${registerForm.username}&email=${
        registerForm.email
      }&password=${
        registerForm.password
      }&uloga=${"ORGANIZATOR"}&nazivOrganizacije=${registerForm.naziv}&adresa=${
        registerForm.adresa
      }&poveznica=${registerForm.poveznica}&članarina=${
        registerForm.clanarina
      }`;
      const options = {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: body,
      };
      fetch("/api/organizator/register", options).then((response) => {
        if (response.status === 400) {
          setError(response.statusText);
        } else {
          goToLogin();
        }
      });
    } else {
      const body = `username=${registerForm.username}&email=${
        registerForm.email
      }&password=${registerForm.password}&uloga=${"POSJETITELJ"}`;
      const options = {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: body,
      };
      fetch("/api/user/register", options).then((response) => {
        if (response.status === 400) {
          setError(response.statusText);
        } else {
          goToLogin();
        }
      });
    }
  }

  const goToLogin = () => {
    navigate("/");
  };

  return (
    <div className="App">
      <div className="Register">
        <div className="Title">
          <h1 className="Naslov">Register</h1>
        </div>
        <form onSubmit={onSubmit}>
          <div className="FormRow">
            <label>Username</label>
            <input
              name="username"
              onChange={onChange}
              value={registerForm.username}
              required
            />
          </div>
          <div className="FormRow">
            <label>Email</label>
            <input
              name="email"
              onChange={onChange}
              value={registerForm.email}
              type="email"
              required
            />
          </div>
          <div className="FormRow">
            <label>Password</label>
            <input
              name="password"
              type="password"
              onChange={onChange}
              value={registerForm.password}
              required
            />
          </div>
          <div className="FormRow">
            <label>
              Organiser role
              <input
                type="checkbox"
                name="admin"
                className="CheckBoxZaReg"
                checked={isChecked}
                onChange={onChange}
              />
            </label>
          </div>
          {isChecked && (
            <div className="AddInfo">
              <div className="FormRow">
                <label>Name</label>
                <input
                  name="naziv"
                  onChange={onChange}
                  value={registerForm.naziv}
                  required
                />
              </div>
              <div className="FormRow">
                <label>Address</label>
                <input
                  name="adresa"
                  onChange={onChange}
                  value={registerForm.adresa}
                  required
                />
              </div>
              <div className="FormRow">
                <label>Link</label>
                <input
                  name="poveznica"
                  onChange={onChange}
                  value={registerForm.poveznica}
                />
              </div>
              <div className="FormRow">
                <label>Subscription</label>
                <input
                  type="checkbox"
                  name="clanarina"
                  className="CheckBoxZaReg"
                  onChange={onChange}
                  checked={isPaid}
                />
              </div>
            </div>
          )}
          <div className="error">{error}</div>
          <div className="button-container">
            <button type="submit">Register</button>
            {/* <button onClick={() => window.location.href = '/'}>Login</button> */}
            <button onClick={goToLogin}>Login</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Register;
