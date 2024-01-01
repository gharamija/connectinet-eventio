import { Check, Close, QuestionMark } from "@mui/icons-material";
import { CardActions, Button, ButtonGroup } from "@mui/material";
import { useState } from "react";

//komponenta treba dobiti koji je id korisnika i događaja o kojem se radi
//te za sto je glasao trenutni korisnik (kategorija)
function Zainteresiranost({ id, dogadajId, pocKategorija }) {
  const [kategorija, setKategorija] = useState(pocKategorija);

  function vote(nova) {
    let query = `?dogadajId=${dogadajId}&korisnikId=${id}&kategorija=${nova}`;
    fetch(`/api/dogadaj/zainteresiranost${query}`, { method: "POST" });
    setKategorija(nova);
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
