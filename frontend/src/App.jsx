import { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Register from "./components/Register.jsx";
import Login from "./components/Login.jsx";
import Header from "./components/Header.jsx";
import Nopage from "./components/Nopage.jsx";
import Footer from "./components/Footer";
import UserList from "./components/UserList.jsx";
import Filter from "./components/Filter.jsx";
import { Box, Container } from "@mui/material";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [loadingUser, setLoadingUser] = useState(true);
  const [role, setRole] = useState(""); // pri loginu treba napraviti setRole ili tako nesto

  function validateSession() {
    fetch("/api/user/validate")
      .then((response) => {
        if (response.status === 200) {
          setIsLoggedIn(true);
          response.json().then((user) => setRole(user.uloga));
        } else {
          onLogout();
        }
      })
      .finally(setLoadingUser(false));
  }

  useEffect(validateSession, []);

  if (loadingUser) {
    return <div>Loading...</div>;
  }

  function onLogin() {
    validateSession();
  }

  function onLogout() {
    fetch("/api/logout").finally(setIsLoggedIn(false));
  }
  if (isLoggedIn) {
    return (
      <>
        <Header onLogout={onLogout} role={role} />
        <Container
          sx={{ display: "flex", flexDirection: { xs: "column", sm: "row" } }}
        >
          <Filter />
          <UserList />
        </Container>
        <Footer />
      </>
    );
  } else {
    return (
      <>
        <Router>
          <Routes>
            <Route exact path="/" element={<Login onLogin={onLogin} />} />
            <Route path="/register" element={<Register onLogin={onLogin} />} />
            <Route path="*" element={<Nopage />} />
          </Routes>
        </Router>
        <Footer />
      </>
    );
  }
}

export default App;
