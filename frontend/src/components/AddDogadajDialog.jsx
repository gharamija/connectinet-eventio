import { useContext, useState } from "react";
import {
  Dialog,
  DialogTitle,
  Button,
  Box,
  TextField,
  Select,
  MenuItem,
  Collapse,
  Alert,
  Grid,
  InputLabel,
  FormControl,
} from "@mui/material";
import vrste from "./Vrste";
import lokacije from "./Lokacije";
import { IdContext } from "../App";

function AddDogadajDialog({ handleClose, open }) {
  const id = useContext(IdContext);

  const [error, setError] = useState("");
  const [form, setForm] = useState({
    nazivDogadaja: "",
    vrsta: "",
    lokacija: "",
    opisLokacije: "",
    vrijemePocetka: "",
    cijenaUlaznice: "",
    opis: "",
    galerija: "",
  });

  function onChange(event) {
    const { name, value } = event.target;
    setForm((oldForm) => ({
      ...oldForm,
      [name]: value === "clear" ? "" : value,
    }));
  }

  function onSubmit(e) {
    e.preventDefault();
    setError("");
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(form),
    };
    fetch(`/api/dogadaj/izrada/${id}`, options).then((response) => {
      if (response.status === 200) {
        handleClose();
      } else {
        setError(response.statusText);
      }
    });
  }

  return (
    <Dialog onClose={handleClose} open={open} onSubmit={onSubmit}>
      <DialogTitle>Dodavanje Događaja</DialogTitle>
      <Box component="form" sx={{ m: 2 }}>
        <TextField
          label="naziv"
          name="nazivDogadaja"
          onChange={onChange}
          required
          fullWidth
          margin="normal"
        />
        <FormControl fullWidth sx={{ mt: 1, mb: 1 }}>
          <InputLabel id="vrsta-label">Vrsta</InputLabel>
          <Select
            labelId="vrsta-label"
            label="vrsta"
            name="vrsta"
            onChange={onChange}
            value={form.vrsta}
            required
            fullWidth
          >
            <MenuItem key={"clear"} value={"clear"}>
              --
            </MenuItem>
            {vrste.map((vrsta) => (
              <MenuItem key={vrsta} value={vrsta}>
                {vrsta}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <FormControl fullWidth sx={{ mt: 1, mb: 1 }}>
          <InputLabel id="lokacija-label">Lokacija</InputLabel>
          <Select
            labelId="lokacija-label"
            label="lokacija"
            name="lokacija"
            onChange={onChange}
            value={form.lokacija}
            required
            fullWidth
          >
            <MenuItem key={"clear"} value={"clear"}>
              --
            </MenuItem>
            {lokacije.map((lok) => (
              <MenuItem key={lok} value={lok}>
                {lok}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <TextField
          label="opis lokacije"
          name="opisLokacije"
          onChange={onChange}
          required
          fullWidth
          margin="normal"
        />
        <TextField
          name="vrijemePocetka"
          type="datetime-local"
          onChange={onChange}
          required
          fullWidth
          margin="normal"
        />
        <TextField
          label="cijena ulaznice"
          name="cijenaUlaznice"
          onChange={onChange}
          required
          fullWidth
          margin="normal"
        />
        <TextField
          label="opis"
          name="opis"
          onChange={onChange}
          required
          fullWidth
          margin="normal"
        />
        <TextField
          label="galerija"
          name="galerija"
          onChange={onChange}
          required
          fullWidth
          margin="normal"
        />
        <Collapse in={error !== ""}>
          <Alert severity="error">{error}</Alert>
        </Collapse>
        <Grid container spacing={1} sx={{ mt: 1 }}>
          <Grid item xs={6}>
            <Button type="submit" variant="contained" fullWidth size="large">
              Dodaj
            </Button>
          </Grid>
          <Grid item xs={6}>
            <Button
              variant="outlined"
              fullWidth
              size="large"
              onClick={handleClose}
            >
              Odustani
            </Button>
          </Grid>
        </Grid>
      </Box>
    </Dialog>
  );
}

export default AddDogadajDialog;
