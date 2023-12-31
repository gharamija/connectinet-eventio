import * as React from "react";
import { useContext } from "react";
import { Box, Typography } from "@mui/material";
import { RoleContext, IdContext } from "../App";
import CustomCard from "./CustomCard.jsx";
import EditDelete from "./EditDelete.jsx";
import InterestCounter from "./InterestCounter.jsx";
import Recenzija from "./Recenzija.jsx";
import PrikazRecenzije from "./PrikazRecenzije.jsx";

export default function MyEventsCards({ query }) {
  const [events, setEvents] = React.useState([]); // ovdje se spremaju eventi za pojedinog korisnika koji ce se prikazati
  const role = useContext(RoleContext);
  const id = useContext(IdContext);

  React.useEffect(() => {
    if (role === "POSJETITELJ") {
      fetch(`/api/dogadaj/user/${id}${query}`).then((response) => {
        if (response.ok) {
          response.json().then((events) => {
            setEvents(events);
          });
        }
      });
    } else if (role === "ORGANIZATOR") {
      fetch(`/api/dogadaj/organizator/${id}`).then((response) => {
        if (response.ok) {
          response.json().then((events) => {
            setEvents(events);
          });
        }
      });
    } else {
      console.log("Za trenutnog Aliena nema dogadjaja na Zemlji");
    }
  }, [role, id, query]);

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        gap: 3,
      }}
    >
      {events.map((event) => (
        <InterestCounter
          key={event.dogadajId}
          interest={
            parseInt(event.sigurnoZainteresiranost) +
            parseInt(event.mozdaZainteresiranost)
          }
        >
          <CustomCard event={event}>
            {role === "ORGANIZATOR" && [
              event.recenzije.map((rec) => (
                <PrikazRecenzije key={rec.recenzijaId} rec={rec} />
              )),
              <EditDelete key="editdelete" dogadajId={event.dogadajId} />,
            ]}
            {role === "POSJETITELJ" &&
              new Date(event.vrijemePocetka) < new Date() && (
                <Recenzija id={id} dogadajId={event.dogadajId} />
              )}
          </CustomCard>
        </InterestCounter>
      ))}
    </Box>
  );
}
