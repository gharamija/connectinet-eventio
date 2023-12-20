import { useState, useEffect } from "react";
import Header from "./Header.jsx";
import Footer from "./Footer";
import UserList from "./UserList.jsx";
import Filter from "./Filter.jsx";
import { Box, Container, Fab } from "@mui/material";
import { Add } from "@mui/icons-material";
import AddDogadajDialog from "./AddDogadajDialog.jsx";

function Homepage({ onLogout, role }) {
  const [dialogOpen, setDialogOpen] = useState(false);
  const [query, setQuery] = useState("");

  return (
    <Box sx={{ marginBottom: 15 }}>
      <Header onLogout={onLogout} role={role} />
      <Container
        sx={{ display: "flex", flexDirection: { xs: "column", sm: "row" } }}
      >
        <Filter setQuery={setQuery} />
        <UserList />
        <Fab
          color="primary"
          aria-label="add"
          sx={{ position: "fixed", right: 15, bottom: 100 }}
          onClick={() => setDialogOpen(true)}
        >
          <Add />
        </Fab>
      </Container>
      <Footer />
      <AddDogadajDialog
        handleClose={() => setDialogOpen(false)}
        open={dialogOpen}
      />
    </Box>
  );
}

export default Homepage;
