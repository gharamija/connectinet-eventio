import * as React from "react";
import { useContext } from "react";
import { Box } from "@mui/material";
import { RoleContext, IdContext } from "../App.jsx";
import CustomCard from "./CustomCard.jsx";
import EditDelete from "./EditDelete.jsx";
import InterestCounter from "./InterestCounter.jsx";

export default function AllEventsCards({ query }) {
  const [events, setEvents] = React.useState([]); // ovdje se spremaju eventi za pojedinog korisnika koji ce se prikazati
  const role = useContext(RoleContext);
  const id = useContext(IdContext);

  React.useEffect(() => {
    fetch(`/api/dogadaj/filter${query}`).then((response) => {
      if (response.ok) {
        response.json().then((events) => {
          setEvents(events);
        });
      }
    });
  }, [query]);
  // ako je query "" onda prikazuje sve dogadjaje

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        ml: 5,
        gap: 2,
      }}
    >
      {events.map((event) => (
        <InterestCounter key={event.dogadaj_id} interest={99}>
          <CustomCard event={event}>
            {role === "ADMIN" && <EditDelete />}
          </CustomCard>
        </InterestCounter>
      ))}
    </Box>
  );
}
