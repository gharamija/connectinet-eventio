import * as React from "react";
import { useContext } from "react";
import { Box } from "@mui/material";
import { RoleContext, IdContext } from "../App.jsx";
import CustomCard from "./CustomCard.jsx";
import EditDelete from "./EditDelete.jsx";
import InterestCounter from "./InterestCounter.jsx";
import Zainteresiranost from "./Zainteresiranost.jsx";

export default function AllEventsCards({ query }) {
  const [events, setEvents] = React.useState([]); // ovdje se spremaju eventi za pojedinog korisnika koji ce se prikazati
  const role = useContext(RoleContext);
  const id = useContext(IdContext);

  function fetchData() {
    fetch(`/api/dogadaj/filter${query}`).then((response) => {
      if (response.ok) {
        response.json().then((events) => {
          setEvents(events);
        });
      }
    });
  }

  React.useEffect(() => {
    fetchData();
  }, [query]);
  // ako je query "" onda prikazuje sve dogadjaje

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
            {role === "ADMIN" && (
              <EditDelete dogadajId={event.dogadajId} fetchData={fetchData} />
            )}
            {role !== "ADMIN" && (
              <Zainteresiranost
                id={id}
                dogadajId={event.dogadajId}
                pocKategorija={event.trenutna}
              />
            )}
          </CustomCard>
        </InterestCounter>
      ))}
    </Box>
  );
}
