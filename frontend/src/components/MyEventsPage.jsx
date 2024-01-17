import { useState, useContext, useEffect } from "react";
import { Box, Container, Fab } from "@mui/material";
import { Add } from "@mui/icons-material";
import AddDogadajDialog from "./AddDogadajDialog.jsx";
import MyEventsCards from "./MyEventsCards.jsx";
import BuduciSvrseniFilter from "./BuduciSvrseniFilter.jsx";
import { RoleContext, IdContext } from "../App.jsx";

function MyEventsPage() {
  const [events, setEvents] = useState([]); // ovdje se spremaju eventi za pojedinog korisnika koji ce se prikazati
  const [dialogOpen, setDialogOpen] = useState(false);
  const [query, setQuery] = useState("");
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

  useEffect(() => {
    fetchData();
  }, [role, query]);

  return (
    <Box sx={{ marginBottom: 15 }}>
      <Container
        sx={{
          display: "flex",
          flexDirection: { xs: "column", md: "row" },
          alignItems: { xs: "center", md: "flex-start" },
          justifyContent: { xs: "flex-start", md: "center" },
        }}
      >
        {role === "POSJETITELJ" && <BuduciSvrseniFilter setQuery={setQuery} />}
        <MyEventsCards events={events} fetchData={fetchData} />
        <Fab
          color="primary"
          aria-label="add"
          sx={{ position: "fixed", right: 15, bottom: 100 }}
          onClick={() => setDialogOpen(true)}
          disabled={role !== "ORGANIZATOR"}
        >
          <Add />
        </Fab>
      </Container>
      <AddDogadajDialog
        handleClose={() => setDialogOpen(false)}
        open={dialogOpen}
        fetchData={fetchData}
      />
    </Box>
  );
}

export default MyEventsPage;
