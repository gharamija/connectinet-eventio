import { useContext } from "react";
import { Box } from "@mui/material";
import { RoleContext, IdContext } from "../App";
import CustomCard from "./CustomCard.jsx";
import EditDelete from "./EditDelete.jsx";
import InterestCounter from "./InterestCounter.jsx";
import Recenzija from "./Recenzija.jsx";

export default function MyEventsCards({ events, fetchData }) {
  const role = useContext(RoleContext);
  const id = useContext(IdContext);

  //provjerava ako je datum između prekjučer i danas
  function dvaDana(dat) {
    return new Date(Date.now() - 48 * 3600 * 1000) < dat && dat < Date.now();
  }

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
              dvaDana(new Date(event.vrijemePocetka)) && (
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
