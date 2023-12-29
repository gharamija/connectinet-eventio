import { useState, useContext } from "react";
import { Box, Container, Fab } from "@mui/material";
import { Add } from "@mui/icons-material";
import AddDogadajDialog from "./AddDogadajDialog.jsx";
import MyEventsCards from "./MyEventsCards.jsx";
import { RoleContext } from "../App.jsx";

function MyEventsPage() {
  const [dialogOpen, setDialogOpen] = useState(false);
  const [query, setQuery] = useState("");
  const role = useContext(RoleContext);

  return (
    <Box sx={{ marginBottom: 15 }}>
      <Container
        sx={{ display: "flex", flexDirection: { xs: "column", sm: "row" } }}
      >
        <MyEventsCards query={query} />
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
      />
    </Box>
  );
}

export default MyEventsPage;
