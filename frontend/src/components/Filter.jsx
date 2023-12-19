import React, { useState } from "react";
import { Select, MenuItem, Box, InputLabel, Button } from "@mui/material";
import lokacije from "./Lokacije";
import ResponsiveDrawer from "./ResponsiveDrawer";
import ResponsiveDrawerButton from "./ResponsiveDrawerButton";

function Filter(props) {
  const [filter, setFilter] = useState({
    sort: "vrijeme-uzlazno",
    lokacija: "",
    stanje: "",
    cijena: "",
  });

  function handleChange(event) {
    const { name, value } = event.target;
    setFilter((oldFilter) => ({ ...oldFilter, [name]: value }));
  }

  function getQuery() {
    let query = "";
    if (filter.sort) query += `sort=${filter.sort}&`;
    if (filter.lokacija) query += `lokacija=${filter.lokacija}&`;
    if (filter.stanje) query += `stanje=${filter.stanje}&`;
    if (filter.cijena) query += `cijena=${filter.cijena}&`;

    if (query === "") {
      return "";
    } else {
      return "?" + query.substring(0, query.length - 1);
    }
  }

  let stuff = (
    <>
      <Box sx={{ display: "flex", flexDirection: "column", margin: 2 }}>
        <InputLabel id="sort-label">Sortiraj</InputLabel>
        <Select
          name="sort"
          value={filter.sort}
          onChange={handleChange}
          labelId="sort-label"
          size="small"
          sx={{ marginBottom: 1 }}
        >
          <MenuItem value="vrijeme-uzlazno">Vrijeme uzlazno</MenuItem>
          <MenuItem value="vrijeme-silazno">Vrijeme silazno</MenuItem>
          <MenuItem value="zainteresirani-uzlazno">
            Broj zainteresiranih uzlazno
          </MenuItem>
          <MenuItem value="zainteresirani-silazno">
            Broj zainteresiranih silazno
          </MenuItem>
        </Select>
        <InputLabel id="lokacija-label">Lokacija</InputLabel>
        <Select
          name="lokacija"
          value={filter.lokacija}
          onChange={handleChange}
          labelId="lokacija-label"
          size="small"
          sx={{ marginBottom: 1 }}
        >
          {lokacije.map((lok) => (
            <MenuItem key={lok} value={lok}>
              {lok}
            </MenuItem>
          ))}
        </Select>
        <InputLabel id="cijena-label">Cijena</InputLabel>
        <Select
          name="cijena"
          value={filter.cijena}
          onChange={handleChange}
          labelId="cijena-label"
          size="small"
          sx={{ marginBottom: 1 }}
        >
          <MenuItem value="placeno">Placeno</MenuItem>
          <MenuItem value="besplatno">Besplatno</MenuItem>
        </Select>
        <Button
          variant="contained"
          color="primary"
          onClick={props.handleFilter}
        >
          Filtriraj
        </Button>
      </Box>
    </>
  );

  return <ResponsiveDrawerButton stuff={stuff} />;
}

export default Filter;
