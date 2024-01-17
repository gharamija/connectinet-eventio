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
import { useNavigate } from "react-router-dom";
import { ContactPageTwoTone } from "@mui/icons-material";

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

  const navigate = useNavigate();

  return (
    <Card sx={{ width: { xs: "90vw", md: "50vw", maxWidth: 500 } }}>
      <CardHeader
        avatar={<Avatar sx={{ bgcolor: red[500] }} aria-label="event" />}
        title={event.nazivDogadaja}
        subheader={event.username}
        action={
          <IconButton
            aria-label="profil"
            onClick={() => navigate(`/profil/${event.organizatorId}`)}
            title="Profil organizatora"
          >
            <ContactPageTwoTone fontSize="large" />
          </IconButton>
        }
      />
      {event.galerija && (
        <CardMedia
          component="img"
          key={Date.now()}
          height="200"
          src={`/api/static/${event.galerija}`}
          alt="Slika nije pronađena"
          sx={{ objectFit: "contain" }}
        />
      )}
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
