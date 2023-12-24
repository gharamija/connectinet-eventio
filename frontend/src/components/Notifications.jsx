import { useState, useEffect, Fragment } from "react";
import {
  Container,
  Typography,
  TextField,
  IconButton,
  Grid,
  InputLabel,
  Select,
  MenuItem,
  FormControl,
} from "@mui/material";
import { Add, Delete } from "@mui/icons-material";
import vrste from "./Vrste";
import lokacije from "./Lokacije";

function Notifications({ id }) {
  const [notif, setNotif] = useState({ vrsta: "", lokacija: "" });
  const [pretplate, setPretplate] = useState([]);

  function dohvati() {
    fetch(`/api/notification/${id}`).then((response) => {
      response.json().then((pretpl) => {
        setPretplate(pretpl);
      });
    });
  }

  useEffect(() => {
    dohvati();
    /*setPretplate([
      { id: "1", vrsta: "UMJETNOST", lokacija: "NOVI_ZAGREB_ISTOK" },
      { id: "2", vrsta: "SPORT", lokacija: "MAKSIMIR" },
    ]);*/
  }, []);

  function onChange(event) {
    const { name, value } = event.target;
    setNotif({ ...notif, [name]: value });
  }

  function onSubmit(e) {
    e.preventDefault();
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(notif),
    };
    fetch(`/api/notificaiton/add/${id}`, options).then(dohvati);
  }

  function obrisiPretplatu(notifId) {
    fetch(`/api/notificaiton/${notifId}`, { method: "DELETE" }).then(dohvati);
  }

  return (
    <Container maxWidth="sm">
      <Typography variant="h4" mb={1}>
        Pretplate na obavijesti
      </Typography>
      <Grid
        container
        component="form"
        onSubmit={onSubmit}
        spacing={1}
        alignItems="center"
      >
        {pretplate.map((pretpl) => (
          <Fragment key={pretpl.id}>
            <Grid item xs={5}>
              <TextField value={pretpl.vrsta} disabled />
            </Grid>
            <Grid item xs={6}>
              <TextField value={pretpl.lokacija} disabled fullWidth />
            </Grid>
            <Grid item xs={1}>
              <IconButton
                onClick={() => obrisiPretplatu(pretpl.id)}
                sx={{ p: 0 }}
              >
                <Delete />
              </IconButton>
            </Grid>
          </Fragment>
        ))}
        <Grid item xs={5}>
          <FormControl fullWidth>
            <InputLabel id="vrsta-label">Vrsta</InputLabel>
            <Select
              labelId="vrsta-label"
              label="vrsta"
              name="vrsta"
              onChange={onChange}
              value={notif.vrsta}
              required
              fullWidth
            >
              {vrste.map((vrsta) => (
                <MenuItem key={vrsta} value={vrsta}>
                  {vrsta}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={6}>
          <FormControl fullWidth>
            <InputLabel id="lokacija-label">Lokacija</InputLabel>
            <Select
              labelId="lokacija-label"
              label="lokacija"
              name="lokacija"
              onChange={onChange}
              value={notif.lokacija}
              required
              fullWidth
            >
              {lokacije.map((lok) => (
                <MenuItem key={lok} value={lok}>
                  {lok}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={1}>
          <IconButton type="submit" sx={{ p: 0 }}>
            <Add />
          </IconButton>
        </Grid>
      </Grid>
    </Container>
  );
}

export default Notifications;
