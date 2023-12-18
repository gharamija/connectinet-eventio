import { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Register from "./components/Register.jsx";
import Login from "./components/Login.jsx";
import Header from "./components/Header.jsx";
import Nopage from "./components/Nopage.jsx";
import Footer from "./components/Footer";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(true);
  const [loadingUser, setLoadingUser] = useState(true);
  const [role, setRole] = useState("Posjetitelj"); // pri loginu treba napraviti setRole ili tako nesto

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      fetch("/api/validate-token", {
        headers: {
          Authorization: `${token}`,
        },
      })
        .then((response) => {
          if (response.status === 200) {
            setIsLoggedIn(true);
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
        <Header onLogout={onLogout} role={role} />
        <UserList />
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
