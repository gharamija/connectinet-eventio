import { useState, useContext, useEffect, Fragment } from "react";
import { IdContext } from "../App";
import {
  Container,
  Box,
  Typography,
  TextField,
  Button,
  Collapse,
  Alert,
  Grid,
} from "@mui/material";
import Header from "./Header";
import Footer from "./Footer";
import Editable from "./Editable";
import Notifications from "./Notifications";

function UserProfile({ onLogout }) {
  const id = useContext(IdContext);

  const [error, setError] = useState(false);
  const [success, setSuccess] = useState(false);
  const [editPass, setEditPass] = useState(false);
  const [editEmail, setEditEmail] = useState(false);
  const [profile, setProfile] = useState({
    username: "",
    email: "",
    password: "",
  });

  useEffect(() => {
    fetch("/api/user").then((response) => {
      response.json().then((details) => {
        setProfile(details);
      });
    });
  }, []);

  function onChange(event) {
    const { name, value } = event.target;
    setProfile((oldProfile) => ({ ...oldProfile, [name]: value }));
  }

  function submit() {
    const options = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(profile),
    };
    fetch("/api/user/" + id, options).then((response) => {
      if (response.status === 200) {
        setSuccess(true);
        setTimeout(() => setSuccess(false), 2000);
      } else {
        setError(true);
      }
    });
  }

  return (
    <>
      <Box sx={{ marginBottom: 15 }}>
        <Header onLogout={onLogout} />
        <Container maxWidth="sm" sx={{ mb: 2 }}>
          <Typography variant="h4" mb={1}>
            Podaci
          </Typography>
          <Grid container spacing={1} alignItems="center">
            <Grid item xs={10}>
              <TextField
                label="username"
                name="username"
                value={profile.username}
                onChange={onChange}
                disabled
                fullWidth
              />
            </Grid>
            <Editable
              edit={editPass}
              editSetter={setEditPass}
              value={profile.password}
              valueSetter={(val) => setProfile({ ...profile, password: val })}
            >
              <TextField
                label="password"
                name="password"
                type="password"
                value={profile.password}
                onChange={onChange}
                disabled={!editPass}
                required
                fullWidth
              />
            </Editable>
            <Editable
              edit={editEmail}
              editSetter={setEditEmail}
              value={profile.email}
              valueSetter={(val) => setProfile({ ...profile, email: val })}
            >
              <TextField
                label="email"
                name="email"
                type="email"
                value={profile.email}
                onChange={onChange}
                disabled={!editEmail}
                required
                fullWidth
              />
            </Editable>
            <Collapse in={error}>
              <Alert severity="error" sx={{ m: 1 }}>
                Došlo je do pogreške
              </Alert>
            </Collapse>
            <Collapse in={success}>
              <Alert severity="success" sx={{ m: 1 }}>
                Promjena uspješna!
              </Alert>
            </Collapse>
            <Grid item xs={5}>
              <Button
                variant="contained"
                size="large"
                onClick={submit}
                fullWidth
              >
                Spremi
              </Button>
            </Grid>
          </Grid>
        </Container>
        <Notifications />
        <Footer />
      </Box>
    </>
  );
}

export default UserProfile;
