import * as React from "react";
import './App.css'
import { createBrowserRouter, Outlet, RouterProvider, useNavigate } from "react-router-dom";
import Register from "./components/Register.jsx";
import Login from "./components/Login.jsx";
import Header from "./components/Header.jsx";


function App() {
  const [isLoggedIn, setIsLoggedIn] = React.useState(false);
  const [loadingUser, setLoadingUser] = React.useState(true);

  const router = createBrowserRouter([
    {
      path: "/",
      element: <AppContainer onLogout={onLogout} />,
      children: [
        {
          path: "register",
          element: <Register />
        }
      ]
    }
  ]);

  React.useEffect(() => {
    fetch("/api/user")
      .then(response => {
        if (response.status !== 401) {
          setLoadingUser(false);
          setIsLoggedIn(true);
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

  if (!isLoggedIn) {
    return (
      <div className="App">
        <Login onLogin={onLogin} />
      </div>
    )
  }

  return (
    <RouterProvider router={router} />
  )
}

export default App

function AppContainer(props) {
  console.log(props)
  return (
    <div>
      <Header onLogout={props.onLogout} />
      <div className="App">
        <Outlet />
      </div>
    </div>
  )
}

