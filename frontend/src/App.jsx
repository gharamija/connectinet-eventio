import { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Register from "./components/Register.jsx";
import Login from "./components/Login.jsx";
import Nopage from "./components/Nopage.jsx";
import Footer from "./components/Footer";
import { Box, Container } from "@mui/material";
import Homepage from "./components/Homepage.jsx";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [loadingUser, setLoadingUser] = useState(true);
  const [role, setRole] = useState(""); // pri loginu treba napraviti setRole ili tako nesto

  function validateSession() {
    fetch("/api/user")
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
    return <Homepage onLogout={onLogout} role={role} />;
  } else {
    return (
      <Box sx={{ marginBottom: 15 }}>
        <Router>
          <Routes>
            <Route exact path="/" element={<Login onLogin={onLogin} />} />
            <Route path="/register" element={<Register onLogin={onLogin} />} />
            <Route path="*" element={<Nopage />} />
          </Routes>
        </Router>
        <Footer />
      </Box>
    );
  }
}

export default App;
