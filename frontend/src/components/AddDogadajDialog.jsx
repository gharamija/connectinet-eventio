import { useContext, useState, useEffect } from "react";
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
  Input,
} from "@mui/material";

import vrste from "./Vrste";
import lokacije from "./Lokacije";
import { IdContext } from "../App";
import { CloudUpload } from "@mui/icons-material";

//ako se u props daje dogadajId, onda znaci edit postojeceg događaja
function AddDogadajDialog({ handleClose, open, dogadajId }) {
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
  });
  const [file, setFile] = useState(null);

  useEffect(() => {
    setFile(null);
    if (dogadajId) {
      fetch(`/api/dogadaj/${dogadajId}`).then((response) =>
        response.json().then((dogadaj) => setForm(dogadaj))
      );
    }
  }, []);

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
      method: dogadajId ? "PUT" : "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(form),
    };

    let url = dogadajId
      ? `/api/dogadaj/update/${dogadajId}`
      : `/api/dogadaj/izrada/${id}`;

    fetch(url, options).then((response) => {
      if (response.status === 200) {
        handleClose();
        if (file) {
          //response bi trebao imati novi id ako je događaj tek nastao
          response.text().then((text) => uploadImage(text));
        }
      } else {
        response.text().then((text) => setError(text));
      }
    });
  }

  function uploadImage(newId) {
    let formData = new FormData();
    formData.append("file", file);

    const options = {
      method: "POST",
      body: formData,
    };

    let url = dogadajId
      ? `/api/dogadaj/slika/${dogadajId}`
      : `/api/dogadaj/slika/${newId}`;

    fetch(url, options);
  }

  function handleFileUpload(e) {
    const file = e.target.files[0];
    if (file.type.startsWith("image")) {
      setFile(file);
    } else {
      setFile(null);
      e.target.value = null;
    }
  }

  return (
    <Dialog onClose={handleClose} open={open} onSubmit={onSubmit}>
      <DialogTitle>
        {dogadajId ? "Izmjena Događaja" : "Dodavanje Događaja"}
      </DialogTitle>
      <Box component="form" sx={{ m: 2 }}>
        <TextField
          label="Naziv događaja"
          name="nazivDogadaja"
          onChange={onChange}
          value={form.nazivDogadaja}
          required
          fullWidth
          margin="dense"
          inputProps={{ maxLength: 200 }}
        />
        <FormControl fullWidth margin="dense">
          <InputLabel id="vrsta-label">Vrsta događaja</InputLabel>
          <Select
            labelId="vrsta-label"
            label="Vrsta događaja"
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
        <FormControl fullWidth margin="dense">
          <InputLabel id="lokacija-label">Lokacija</InputLabel>
          <Select
            labelId="lokacija-label"
            label="Lokacija"
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
          label="Opis lokacije"
          name="opisLokacije"
          onChange={onChange}
          value={form.opisLokacije}
          required
          fullWidth
          margin="dense"
          inputProps={{ maxLength: 250 }}
        />
        <TextField
          name="vrijemePocetka"
          type="datetime-local"
          onChange={onChange}
          value={form.vrijemePocetka}
          required
          fullWidth
          margin="dense"
        />
        <TextField
          label="Cijena ulaznice"
          name="cijenaUlaznice"
          onChange={onChange}
          value={form.cijenaUlaznice}
          required
          fullWidth
          margin="dense"
          inputProps={{ maxLength: 9 }}
        />
        <TextField
          label="Opis"
          name="opis"
          onChange={onChange}
          value={form.opis}
          required
          fullWidth
          margin="dense"
          inputProps={{ maxLength: 250 }}
        />
        <Button
          type="button"
          component="label"
          variant="outlined"
          startIcon={<CloudUpload />}
          fullWidth
          sx={{ mt: 1, mb: 1 }}
        >
          {file ? file.name : "Odaberite sliku"}
          <Input
            type="file"
            name="galerija"
            onChange={handleFileUpload}
            sx={{ opacity: 0 }}
            inputProps={{ accept: "image/*" }}
          />
        </Button>
        <Collapse in={error !== ""}>
          <Alert severity="error">{error}</Alert>
        </Collapse>
        <Grid container spacing={1} sx={{ mt: 1 }}>
          <Grid item xs={6}>
            <Button type="submit" variant="contained" fullWidth size="large">
              {dogadajId ? "Spremi" : "Dodaj"}
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
