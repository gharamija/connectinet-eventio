import {
  CardActions,
  Rating,
  Button,
  TextField,
  FormControl,
} from "@mui/material";
import { useState } from "react";

function Recenzija({ id, dogadajId, fetchData }) {
  const [rating, setRating] = useState(null);
  const [recenzija, setRecenzija] = useState("");
  const [valid, setValid] = useState(false);

  function clear() {
    setRating(null);
    setRecenzija("");
    setValid(false);
  }

  function onSubmit() {
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
    fetch("/api/dogadaj/recenzija", options).then(() => {
      fetchData();
      clear();
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
        />
      </FormControl>
      <Button variant="contained" onClick={onSubmit} disabled={!valid}>
        Pohrani recenziju
      </Button>
    </CardActions>
  );
}

export default Recenzija;
