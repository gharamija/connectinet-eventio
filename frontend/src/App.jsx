import { createContext, useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Register from "./components/Register.jsx";
import Login from "./components/Login.jsx";
import Nopage from "./components/Nopage.jsx";
import Footer from "./components/Footer";
import { Box } from "@mui/material";
import Homepage from "./components/Homepage.jsx";
import Profile from "./components/Profile.jsx";
import Header from "./components/Header";
import UserList from "./components/UserList";
import MyEventsPage from "./components/MyEventsPage.jsx";
import Subscriptions from "./components/Subscriptions.jsx";

const RoleContext = createContext();
const IdContext = createContext();
const UsernameContext = createContext();

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [loadingUser, setLoadingUser] = useState(true);

  const [role, setRole] = useState("");
  const [id, setId] = useState("");
  const [username, setUsername] = useState("");

  function validateSession() {
    fetch("/api/user")
      .then((response) => {
        if (response.status === 200) {
          setIsLoggedIn(true);
          response.json().then((user) => {
            setRole(user.uloga);
            setId(user.id);
            setUsername(user.username);
          });
        } else {
          onLogout();
        }
      })
      .finally(setLoadingUser(false));
  }

  useEffect(validateSession, []);

  if (loadingUser) {
    return <div>Ucitavanje...</div>;
  }

  function onLogin() {
    validateSession();
  }

  function onLogout() {
    fetch("/api/logout").finally(setIsLoggedIn(false));
  }

  if (isLoggedIn) {
    return (
      <RoleContext.Provider value={role}>
        <IdContext.Provider value={id}>
          <UsernameContext.Provider value={username}>
            <Router>
              <Header onLogout={onLogout} />
              <Routes>
                <Route exact path="/" element={<Homepage />} />
                <Route path="/moji" element={<MyEventsPage />} />
                <Route path="/profil" element={<Profile />} />
                <Route path="/korisnici" element={<UserList />} />
                <Route path="/pretplate" element={<Subscriptions />} />
                <Route path="*" element={<Nopage />} />
              </Routes>
              <Footer />
            </Router>
          </UsernameContext.Provider>
        </IdContext.Provider>
      </RoleContext.Provider>
    );
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
export { RoleContext, IdContext, UsernameContext };
