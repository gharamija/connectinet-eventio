import { useState } from "react";
import Filter from "./Filter.jsx";
import { Box, Container, Fab } from "@mui/material";
import { Add } from "@mui/icons-material";
import AddDogadajDialog from "./AddDogadajDialog.jsx";
import Cards from "./Cards.jsx";

const QueryContext = createContext();

function Homepage() {
  const [dialogOpen, setDialogOpen] = useState(false);
  const [query, setQuery] = useState("");

  return (
    <Box sx={{ marginBottom: 15 }}>
      <Container
        sx={{ display: "flex", flexDirection: { xs: "column", sm: "row" } }}
      >
        <Filter setQuery={setQuery} />
        <Fab
          color="primary"
          aria-label="add"
          sx={{ position: "fixed", right: 15, bottom: 100 }}
          onClick={() => setDialogOpen(true)}
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

export default Homepage;
export { QueryContext };
