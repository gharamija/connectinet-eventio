import React, { useState } from "react";
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
  });
  const [error, setError] = React.useState("");
  const [isChecked, setIsChecked] = useState(false);

  const navigate = useNavigate();

  function onChange(event) {
    const { name, value, type, checked } = event.target;
    if (type === "checkbox" && name === "admin") {
      setRegisterForm((oldForm) => ({ ...oldForm, [name]: checked }));
      setIsChecked(checked);
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
      }&poveznica=${registerForm.poveznica}`;
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
          response.text().then((text) => setError(text));
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
          response.text().then((text) => setError(text));
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
          Registracija
        </Typography>
        <Box component="form" onSubmit={onSubmit} sx={{ mt: 1 }}>
          <TextField
            label="Korisničko ime"
            name="username"
            onChange={onChange}
            required
            fullWidth
            margin="normal"
            inputProps={{ maxLength: 30 }}
          />
          <TextField
            label="Email"
            name="email"
            type="email"
            onChange={onChange}
            required
            fullWidth
            margin="normal"
            inputProps={{ maxLength: 100 }}
          />
          <TextField
            label="Lozinka"
            name="password"
            type="password"
            onChange={onChange}
            required
            fullWidth
            margin="normal"
            inputProps={{ maxLength: 200 }}
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
                label="Naziv događaja"
                name="naziv"
                onChange={onChange}
                required
                fullWidth
                margin="normal"
                inputProps={{ maxLength: 200 }}
              />
              <TextField
                label="Adresa"
                name="adresa"
                onChange={onChange}
                required
                fullWidth
                margin="normal"
                inputProps={{ maxLength: 200 }}
              />
              <TextField
                label="Poveznica"
                name="poveznica"
                onChange={onChange}
                fullWidth
                margin="normal"
                inputProps={{ maxLength: 200 }}
              />
            </>
          )}
          <Collapse in={error !== ""}>
            <Alert severity="error">{error}</Alert>
          </Collapse>
          <Grid container spacing={1} sx={{ mt: 1 }}>
            <Grid item xs={6}>
              <Button href="/" variant="outlined" fullWidth size="large">
                Prijava
              </Button>
            </Grid>
            <Grid item xs={6}>
              <Button
                type="submit"
                variant="contained"
                color="secondary"
                fullWidth
                size="large"
              >
                Registracija
              </Button>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
}

export default Register;
