import { Check, Close, QuestionMark } from "@mui/icons-material";
import { CardActions, Button, ButtonGroup } from "@mui/material";

//komponenta treba dobiti koji je id korisnika i događaja o kojem se radi
function Zainteresiranost({ id, dogadajId }) {
  function vote(kategorija) {
    let obj = { idDogadaj: dogadajId, idKorisnik: id, kategorija: kategorija };
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(obj),
    };
    fetch("/api/dogadaj/zainteresiranost", options);
  }

  return (
    <CardActions disableSpacing>
      <ButtonGroup variant="outlined" size="small">
        <Button
          variant="contained"
          color="success"
          startIcon={<Check />}
          onClick={() => vote("SIGURNO")}
        >
          Dolazim
        </Button>
        <Button
          color="info"
          startIcon={<QuestionMark />}
          onClick={() => vote("MOZDA")}
        >
          Mozda
        </Button>
        <Button color="error" startIcon={<Close />} onClick={() => vote("NE")}>
          Ne Dolazim
        </Button>
      </ButtonGroup>
    </CardActions>
  );
}

export default Zainteresiranost;
