import React, { useState } from "react";
import "./Register.css";
import { useNavigate } from "react-router-dom";
import {
  Box,
  Checkbox,
  Container,
  CssBaseline,
  FormControlLabel,
  Typography,
  TextField,
  Button,
  Grid,
  Collapse,
  Alert,
} from "@mui/material";

function Register(props) {
  const [registerForm, setRegisterForm] = React.useState({
    username: "",
    email: "",
    password: "",
    type: "",
    naziv: "",
    adresa: "",
    poveznica: "",
    clanarina: "",
  });
  const [error, setError] = React.useState("");
  const [isChecked, setIsChecked] = useState(false);
  const [isPaid, setIsPaid] = useState(false);

  const navigate = useNavigate();

  function onChange(event) {
    const { name, value, type, checked } = event.target;
    if (type === "checkbox" && name === "admin") {
      setRegisterForm((oldForm) => ({ ...oldForm, [name]: checked }));
      setIsChecked(checked);
    } else if (type === "checkbox" && name === "clanarina") {
      setRegisterForm((oldForm) => ({ ...oldForm, [name]: checked }));
      setIsPaid(checked);
    } else {
      setRegisterForm((oldForm) => ({ ...oldForm, [name]: value }));
    }
  }

  function onSubmit(e) {
    e.preventDefault();
    setError("");
    if (isChecked) {
      const body = `username=${registerForm.username}&email=${
        registerForm.email
      }&password=${
        registerForm.password
      }&uloga=${"ORGANIZATOR"}&nazivOrganizacije=${registerForm.naziv}&adresa=${
        registerForm.adresa
      }&poveznica=${registerForm.poveznica}&članarina=${
        registerForm.clanarina
      }`;
      const options = {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: body,
      };
      fetch("/api/organizator/register", options).then((response) => {
        if (response.status === 200) {
          goToLogin();
        } else {
          setError(response.statusText);
        }
      });
    } else {
      const body = `username=${registerForm.username}&email=${
        registerForm.email
      }&password=${registerForm.password}&uloga=${"POSJETITELJ"}`;
      const options = {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: body,
      };
      fetch("/api/user/register", options).then((response) => {
        if (response.status === 200) {
          goToLogin();
        } else {
          setError(response.statusText);
        }
      });
    }
  }

  const goToLogin = () => {
    navigate("/");
  };

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
          Register
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
            label="email"
            name="email"
            type="email"
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
          <FormControlLabel
            control={<Checkbox />}
            label="Organizator"
            name="admin"
            checked={isChecked}
            onChange={onChange}
          />
          {isChecked && (
            <>
              <TextField
                label="naziv"
                name="naziv"
                onChange={onChange}
                required
                fullWidth
                margin="normal"
              />
              <TextField
                label="adresa"
                name="adresa"
                onChange={onChange}
                required
                fullWidth
                margin="normal"
              />
              <TextField
                label="link"
                name="poveznica"
                onChange={onChange}
                fullWidth
                margin="normal"
              />
              <FormControlLabel
                control={<Checkbox />}
                label="Clanarina"
                name="clanarina"
                checked={isPaid}
                onChange={onChange}
              />
            </>
          )}
          <Collapse in={error !== ""}>
            <Alert severity="error">{error}</Alert>
          </Collapse>
          <Grid container spacing={1} sx={{ mt: 1 }}>
            <Grid item xs={6}>
              <Button type="submit" variant="contained" fullWidth size="large">
                Register
              </Button>
            </Grid>
            <Grid item xs={6}>
              <Button
                href="/"
                variant="outlined"
                color="secondary"
                fullWidth
                size="large"
              >
                Login
              </Button>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
}

export default Register;
