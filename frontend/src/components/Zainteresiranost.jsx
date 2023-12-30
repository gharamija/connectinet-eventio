import { Check, Close, QuestionMark } from "@mui/icons-material";
import { CardActions, Button, ButtonGroup } from "@mui/material";

//komponenta treba dobiti koji je id korisnika i događaja o kojem se radi
//te za sto je glasao trenutni korisnik (kategorija)
function Zainteresiranost({ id, dogadajId, kategorija }) {
  function vote(nova) {
    let obj = { idDogadaj: dogadajId, idKorisnik: id, kategorija: nova };
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
      <ButtonGroup fullWidth>
        <Button
          variant={kategorija === "SIGURNO" ? "contained" : "outlined"}
          color="success"
          startIcon={<Check />}
          onClick={() => vote("SIGURNO")}
        >
          Dolazim
        </Button>
        <Button
          variant={kategorija === "MOZDA" ? "contained" : "outlined"}
          color="info"
          startIcon={<QuestionMark />}
          onClick={() => vote("MOZDA")}
        >
          Mozda
        </Button>
        <Button
          variant={kategorija === "NE" ? "contained" : "outlined"}
          color="error"
          startIcon={<Close />}
          onClick={() => vote("NE")}
        >
          Ne Dolazim
        </Button>
      </ButtonGroup>
    </CardActions>
  );
}

export default Zainteresiranost;
