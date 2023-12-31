import { CardActions, Rating, TextField, Typography } from "@mui/material";

function PrikazRecenzije({ rec }) {
  return (
    <CardActions
      sx={{
        display: "flex",
        flexWrap: "wrap",
        justifyContent: "space-between",
        gap: 1,
      }}
    >
      <Typography variant="body1" color="initial">
        {rec.username}
      </Typography>
      <Rating value={rec.ocjena} readOnly />
      <TextField value={rec.tekst} multiline maxRows={3} fullWidth />
    </CardActions>
  );
}

export default PrikazRecenzije;
