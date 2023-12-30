import { useState } from "react";
import Filter from "./Filter.jsx";
import { Box, Container } from "@mui/material";
import AllEventsCards from "./AllEventsCards.jsx";

function Homepage() {
  const [query, setQuery] = useState("");

  return (
    <Box sx={{ marginBottom: 15 }}>
      <Container
        sx={{ display: "flex", flexDirection: { xs: "column", sm: "row" } }}
      >
        <Filter setQuery={setQuery} />
        <AllEventsCards query={query} />
      </Container>
    </Box>
  );
}

export default Homepage;
