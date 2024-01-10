import * as React from "react";
import { styled } from "@mui/material/styles";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Collapse from "@mui/material/Collapse";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import { red } from "@mui/material/colors";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import PrikazRecenzije from "./PrikazRecenzije.jsx";

const ExpandMore = styled((props) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? "rotate(0deg)" : "rotate(180deg)",
  marginLeft: "auto",
  transition: theme.transitions.create("transform", {
    duration: theme.transitions.duration.shortest,
  }),
}));

export default function CustomCard({ event, children }) {
  const [expanded, setExpanded] = React.useState(false);

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  return (
    <Card sx={{ width: { xs: "90vw", md: "50vw", maxWidth: 500 } }}>
      <CardHeader
        avatar={
          <Avatar sx={{ bgcolor: red[500] }} aria-label="event">
            {event.organizator}
          </Avatar>
        }
        title={event.nazivDogadaja}
        subheader={event.username}
      />
      <CardMedia
        component="img"
        height="194"
        src={`/api/static/${event.galerija}`}
      />
      <CardContent sx={{ pb: 0 }}>
        <Typography paragraph color="text.primary">
          {event.lokacija.replaceAll("_", " ")}
        </Typography>
        <Typography paragraph color="text.secondary">
          {event.opisLokacije}
        </Typography>
        <Typography paragraph color="text.primary">
          {new Date(event.vrijemePocetka).toLocaleString("en-GB", {
            dateStyle: "medium",
            timeStyle: "short",
          })}
        </Typography>
      </CardContent>
      <CardActions disableSpacing>
        <ExpandMore
          expand={expanded}
          onClick={handleExpandClick}
          aria-expanded={expanded}
          aria-label="show more"
        >
          <ExpandMoreIcon />
        </ExpandMore>
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <CardContent>
          <Typography paragraph>{event.opis}</Typography>
          <Typography paragraph>Cijena: {event.cijenaUlaznice}</Typography>
          {event.recenzije.map((rec) => (
            <PrikazRecenzije key={rec.recenzijaId} rec={rec} />
          ))}
        </CardContent>
      </Collapse>
      {children}
    </Card>
  );
}
