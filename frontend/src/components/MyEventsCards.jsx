import * as React from "react";
import { useContext } from "react";
import { Box } from "@mui/material";
import { RoleContext, IdContext } from "../App";
import CustomCard from "./CustomCard.jsx";
import EditDelete from "./EditDelete.jsx";
import InterestCounter from "./InterestCounter.jsx";
import Recenzija from "./Recenzija.jsx";

export default function MyEventsCards({ query }) {
  const [events, setEvents] = React.useState([]); // ovdje se spremaju eventi za pojedinog korisnika koji ce se prikazati
  const role = useContext(RoleContext);
  const id = useContext(IdContext);

  function fetchData() {
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
    }
  }

  React.useEffect(() => {
    fetchData();
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
            {role === "ORGANIZATOR" && (
              <EditDelete dogadajId={event.dogadajId} fetchData={fetchData} />
            )}
            {role === "POSJETITELJ" &&
              new Date(event.vrijemePocetka) < new Date() && (
                <Recenzija
                  id={id}
                  dogadajId={event.dogadajId}
                  fetchData={fetchData}
                />
              )}
          </CustomCard>
        </InterestCounter>
      ))}
    </Box>
  );
}
