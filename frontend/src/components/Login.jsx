import React from "react";
import {
  Typography,
  Container,
  CssBaseline,
  Box,
  TextField,
  Button,
  Grid,
  Collapse,
  Alert,
} from "@mui/material";

function Login(props) {
  const [loginForm, setLoginForm] = React.useState({
    username: "",
    password: "",
  });
  const [error, setError] = React.useState(false);

  function onChange(event) {
    const { name, value } = event.target;
    setLoginForm((oldForm) => ({ ...oldForm, [name]: value }));
  }

  function onSubmit(e) {
    e.preventDefault();
    setError(false);
    const body = `username=${loginForm.username}&password=${loginForm.password}`;
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: body,
    };
    fetch("/api/login", options).then((response) => {
      if (response.status === 200) {
        props.onLogin();
      } else {
        setError(true);
      }
    });
  }

  return (
    <Container component="main" maxWidth="xs">
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
          <Collapse in={error}>
            <Alert severity="error">Login failed</Alert>
          </Collapse>
          <Grid container spacing={1} sx={{ mt: 1 }}>
            <Grid item xs={6}>
              <Button type="submit" variant="contained" fullWidth size="large">
                Login
              </Button>
            </Grid>
            <Grid item xs={6}>
              <Button
                href="/register"
                variant="outlined"
                color="secondary"
                fullWidth
                size="large"
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
