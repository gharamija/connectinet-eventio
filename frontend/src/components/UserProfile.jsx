import { useState, useContext, useEffect } from "react";
import { IdContext } from "../App";
import {
  Container,
  Box,
  Typography,
  TextField,
  IconButton,
  Button,
  Collapse,
  Alert,
} from "@mui/material";
import { Check, Close, Edit } from "@mui/icons-material";
import Header from "./Header";
import Footer from "./Footer";

function Editable({ edit, editSetter, value, valueSetter, children }) {
  const [oldValue, setOldValue] = useState("");

  return (
    <Box
      component="form"
      sx={{ display: "flex" }}
      onSubmit={(e) => {
        e.preventDefault();
        editSetter(false);
      }}
    >
      {children}
      {!edit && (
        <IconButton
          onClick={() => {
            editSetter(true);
            setOldValue(value);
          }}
        >
          <Edit />
        </IconButton>
      )}
      {edit && (
        <IconButton type="submit">
          <Check />
        </IconButton>
      )}
      {edit && (
        <IconButton
          onClick={() => {
            valueSetter(oldValue);
            editSetter(false);
          }}
        >
          <Close />
        </IconButton>
      )}
    </Box>
  );
}

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
        <Container
          maxWidth="xs"
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "flex-start",
          }}
        >
          <Typography variant="h4">Podaci</Typography>
          <TextField
            label="username"
            name="username"
            value={profile.username}
            onChange={onChange}
            disabled
            sx={{ mt: 1 }}
          />
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
              sx={{ mt: 1 }}
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
              sx={{ mt: 1 }}
            />
          </Editable>
          <Collapse in={error}>
            <Alert severity="error">Došlo je do pogreške</Alert>
          </Collapse>
          <Collapse in={success}>
            <Alert severity="success">Promjena uspješna!</Alert>
          </Collapse>
          <Button
            variant="contained"
            size="large"
            onClick={submit}
            sx={{ mt: 1, ml: 2 }}
          >
            Spremi promjene
          </Button>
        </Container>
        <Footer />
      </Box>
    </>
  );
}

export default UserProfile;
