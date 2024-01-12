import {
  CardActions,
  Rating,
  Button,
  TextField,
  FormControl,
  Collapse,
  Alert,
} from "@mui/material";
import { useState } from "react";

function Recenzija({ id, dogadajId, fetchData }) {
  const [rating, setRating] = useState(null);
  const [recenzija, setRecenzija] = useState("");
  const [valid, setValid] = useState(false);
  const [error, setError] = useState("");

  function clear() {
    setRating(null);
    setRecenzija("");
    setValid(false);
  }

  function onSubmit() {
    setError("");
    let obj = {
      korisnikId: id,
      dogadajId: dogadajId,
      tekst: recenzija,
      ocjena: rating,
    };
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(obj),
    };
    fetch("/api/dogadaj/recenzija", options).then((response) => {
      if (response.status === 200) {
        fetchData();
        clear();
      } else {
        response.text().then((text) => setError(text));
      }
    });
  }

  return (
    <CardActions
      sx={{
        display: "flex",
        flexDirection: "column",
        gap: 2,
      }}
    >
      <Rating
        value={rating}
        onChange={(event, newRating) => {
          setRating(newRating);
          setValid(newRating);
        }}
      />
      <FormControl fullWidth>
        <TextField
          label="Recenzija"
          value={recenzija}
          multiline
          maxRows={3}
          onChange={(event) => {
            setRecenzija(event.target.value);
          }}
          inputProps={{ maxLength: 200 }}
        />
      </FormControl>
      <Collapse in={error !== ""}>
        <Alert severity="error">{error}</Alert>
      </Collapse>
      <Button variant="contained" onClick={onSubmit} disabled={!valid}>
        Pohrani recenziju
      </Button>
    </CardActions>
  );
}

export default Recenzija;
