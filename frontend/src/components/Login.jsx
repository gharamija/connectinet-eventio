import React from "react";
import {
  Typography,
  Container,
  CssBaseline,
  Box,
  TextField,
  Button,
  Grid,
} from "@mui/material";

function Login(props) {
  const [loginForm, setLoginForm] = React.useState({
    username: "",
    password: "",
  });
  const [error, setError] = React.useState("");

  function onChange(event) {
    const { name, value } = event.target;
    setLoginForm((oldForm) => ({ ...oldForm, [name]: value }));
  }

  function onSubmit(e) {
    e.preventDefault();
    setError("");
    const formData = `username=${loginForm.username}&password=${loginForm.password}`;
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: formData.toString(),
    };
    fetch("/api/login", options).then((response) => {
      if (response.status === 401) {  // ja bi ovo drugacije, kad je 200 onda je okej i onda napravi sve, ostalo faild
        setError("Login failed");
      } else {
        localStorage.setItem('token', response.headers.get('Authorization'));
        props.onLogin();
      }
    });
  }

  return (
    <Container component="main" maxWidth="sm">
      <CssBaseline />
      <Box
        sx={{
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Typography variant="h4" color="text.primary">
          Login
        </Typography>
        <Box component="form" onSubmit={onSubmit} sx={{ mt: 1 }}>
          <TextField
            label="username"
            name="username"
            onChange={onChange}
            required
            fullWidth
            margin="normal"
          />
          <TextField
            label="password"
            name="password"
            type="password"
            onChange={onChange}
            required
            fullWidth
            margin="normal"
          />
          <Grid container spacing={1} sx={{ mt: 1 }}>
            <Grid item xs={6}>
              <Button type="submit" variant="contained" fullWidth>
                Login
              </Button>
            </Grid>
            <Grid item xs={6}>
              <Button
                href="/register"
                variant="outlined"
                color="secondary"
                fullWidth
              >
                Register
              </Button>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
}

export default Login;
