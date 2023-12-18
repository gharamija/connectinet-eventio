import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, json } from "react-router-dom";
import Register from "./components/Register.jsx";
import Login from "./components/Login.jsx";
import Header from "./components/Header.jsx";
import Nopage from "./components/Nopage.jsx";
import Filter from "./components/Filter.jsx";
import { Box, Card, CardContent, Drawer, Typography } from "@mui/material";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(true);
  const [loadingUser, setLoadingUser] = useState(true);

  function validateSession() {
    fetch("/api/user")
      .then((response) => {
        if (response.status === 200) {
          console.log(response.json());
          setIsLoggedIn(true);
        }
      })
      .finally(() => {
        setLoadingUser(false);
      });
  }

  useEffect(() => {
    validateSession();
  }, []);

  if (loadingUser) {
    return <div>Loading...</div>;
  }

  function onLogin() {
    validateSession();
  }

  function onLogout() {
    setIsLoggedIn(false);
  }
  if (isLoggedIn) {
    return (
      <>
        <Header onLogout={onLogout} zIndex={1400} />
        <Box style={{ display: "flex" }}>
          <Filter handleFilter={console.log(Filter.getQuery())} />
          <Box>
            <Card>
              <CardContent>
                <Typography variant="h4">Ovo je kartica</Typography>
                <Typography>
                  Lorem ipsum dolor sit amet consectetur adipisicing elit. Aut,
                  qui? Error quas voluptatibus adipisci nam!
                </Typography>
              </CardContent>
            </Card>
            <Card>
              <CardContent>
                <Typography variant="h4">Ovo je kartica</Typography>
                <Typography>
                  Lorem ipsum dolor sit amet consectetur adipisicing elit. Aut,
                  qui? Error quas voluptatibus adipisci nam!
                </Typography>
              </CardContent>
            </Card>
          </Box>
        </Box>
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
