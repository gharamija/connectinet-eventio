import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Register from "./components/Register.jsx";
import Login from "./components/Login.jsx";
import Header from "./components/Header.jsx";
import Nopage from "./components/Nopage.jsx"
import './App.css';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [loadingUser, setLoadingUser] = useState(true);

  useEffect(() => {
    fetch("/")
      .then(response => {
        if (response.status !== 401) {
          setLoadingUser(false);
          setIsLoggedIn(false);
        } else {
          setLoadingUser(false);
        }
      })
  }, []);

  if (loadingUser) {
    return <div>Loading...</div>
  }

  function onLogin() {
    setIsLoggedIn(true)
  }

  function onLogout() {
    setIsLoggedIn(false);
  }
  if (isLoggedIn) {
    return (
      <Router>
        <Routes>
          <Route path="/" element={<Header onLogout={onLogout} />} />
          <Route path="login" />
        </Routes>
      </Router>
    );
  } else {
    return (
      <Router>
        <Routes>
          <Route path="/" element={<Login onLogin={onLogin} />} />
          <Route path="register" element={<Register onLogin={onLogin} />} />
          <Route path="*" element={<Nopage />} />
        </Routes>
      </Router>
    );
  }
}

export default App;


