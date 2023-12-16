import { decodeToken } from "react-jwt";
import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, json } from "react-router-dom";
import Register from "./components/Register.jsx";
import Login from "./components/Login.jsx";
import Header from "./components/Header.jsx";
import Nopage from "./components/Nopage.jsx";
import UserList from "./components/UserList.jsx";

function App() {
  const [userId, setUserId] = useState("");

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [loadingUser, setLoadingUser] = useState(true);

  function validateToken(token) {
    if (token) {
      fetch("/api/validate-token", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((response) => {
          if (response.status === 200) {
            const decodedToken = decodeToken(token);
            if (decodedToken) {
              setUserId(decodedToken.userId);
              setIsLoggedIn(true);
            } else {
              onLogout();
            }
          } else {
            onLogout();
          }
        })
        .finally(() => {
          setLoadingUser(false);
        });
    } else {
      setLoadingUser(false);
    }
  }

  useEffect(() => {
    const token = localStorage.getItem("token");
    validateToken(token);
  }, []);

  if (loadingUser) {
    return <div>Loading...</div>;
  }

  function onLogin(token) {
    localStorage.setItem("token", token);
    validateToken(token);
  }

  function onLogout() {
    localStorage.removeItem("token");
    setIsLoggedIn(false);
  }
  if (isLoggedIn) {
    return (
      <>
        <Header onLogout={onLogout} />
        <UserList />
      </>
    );
  } else {
    return (
      <Router>
        <Routes>
          <Route exact path="/" element={<Login onLogin={onLogin} />} />
          <Route path="/register" element={<Register onLogin={onLogin} />} />
          <Route path="*" element={<Nopage />} />
        </Routes>
      </Router>
    );
  }
}

export default App;
