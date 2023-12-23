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
  const [changed, setChanged] = useState(false);
  const [editUsername, setEditUsername] = useState(false);
  const [editPass, setEditPass] = useState(false);
  const [editEmail, setEditEmail] = useState(false);
  const [profile, setProfile] = useState({
    id: "",
    username: "",
    password: "",
    email: "",
    uloga: "",
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
    setProfile({ ...profile, [name]: value });
  }

  function submit() {
    const options = {
      method: "PUT",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: `username=${profile.username}&password=${
        profile.password ? profile.password : ""
      }&email=${profile.email}&uloga=${profile.uloga}`,
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
            <Editable
              edit={editUsername}
              editSetter={setEditUsername}
              value={profile.username}
              valueSetter={(val) => setProfile({ ...profile, username: val })}
              change={setChanged}
            >
              <TextField
                label="username"
                name="username"
                value={profile.username}
                onChange={onChange}
                disabled={!editUsername}
                required
                fullWidth
              />
            </Editable>
            <Editable
              edit={editPass}
              editSetter={setEditPass}
              value={profile.password}
              valueSetter={(val) => setProfile({ ...profile, password: val })}
              change={setChanged}
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
              change={setChanged}
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
                disabled={!changed || editUsername || editPass || editEmail}
              >
                Spremi
              </Button>
            </Grid>
          </Grid>
        </Container>
        <Notifications id={id} />
        <Footer />
      </Box>
    </>
  );
}

export default UserProfile;
