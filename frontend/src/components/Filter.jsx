import React, { useState } from "react";
import { Select, MenuItem, Box, InputLabel, Button } from "@mui/material";
import lokacije from "./Lokacije";
import vrste from "./Vrste";
import ResponsiveDrawer from "./ResponsiveDrawer";
import ResponsiveDrawerButton from "./ResponsiveDrawerButton";

function Filter(props) {
  const [filter, setFilter] = useState({
    sort: "uzlazno",
    lokacija: "",
    vrijeme: "",
    vrsta: "",
    zavrseno: "",
    placanje: "",
  });

  function handleChange(event) {
    event.stopPropagation();
    const { name, value } = event.target;
    setFilter((oldFilter) => ({ ...oldFilter, [name]: value }));
  }

  function getQuery() {
    let query = "";
    if (filter.sort) query += `sort=${filter.sort}&`;
    if (filter.lokacija) query += `lokacija=${filter.lokacija}&`;
    if (filter.vrijeme) query += `vrijeme=${filter.vrijeme}&`;
    if (filter.vrsta) query += `vrsta=${filter.vrsta}&`;
    if (filter.zavrseno) query += `zavrseno=${filter.zavrseno}&`;
    if (filter.placanje) query += `placanje=${filter.placanje}&`;

    if (query === "") {
      return "";
    } else {
      return "?" + query.substring(0, query.length - 1);
    }
  }

  return (
    <>
      <ResponsiveDrawerButton>
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
            <MenuItem value="uzlazno">Vrijeme uzlazno</MenuItem>
            <MenuItem value="silazno">Vrijeme silazno</MenuItem>
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

          <InputLabel id="vrijeme-label">Vrijeme</InputLabel>
          <Select
            name="vrijeme"
            value={filter.vrijeme}
            onChange={handleChange}
            labelId="vrijeme-label"
            size="small"
            sx={{ marginBottom: 1 }}
          >
            <MenuItem value="1">24 sata</MenuItem>
            <MenuItem value="7">7 dana</MenuItem>
            <MenuItem value="30">30 dana</MenuItem>
          </Select>

          <InputLabel id="vrsta-label">Vrsta</InputLabel>
          <Select
            name="vrsta"
            value={filter.vrsta}
            onChange={handleChange}
            labelId="vrsta-label"
            size="small"
            sx={{ marginBottom: 1 }}
          >
            {vrste.map((vrsta) => (
              <MenuItem key={vrsta} value={vrsta}>
                {vrsta}
              </MenuItem>
            ))}
          </Select>

          <InputLabel id="zavrseno-label">Zavrseno</InputLabel>
          <Select
            name="zavrseno"
            value={filter.zavrseno}
            onChange={handleChange}
            labelId="zavrseno-label"
            size="small"
            sx={{ marginBottom: 1 }}
          >
            <MenuItem value="1">Da</MenuItem>
            <MenuItem value="0">Ne</MenuItem>
          </Select>

          <InputLabel id="placanje-label">Cijena</InputLabel>
          <Select
            name="placanje"
            value={filter.placanje}
            onChange={handleChange}
            labelId="placanje-label"
            size="small"
            sx={{ marginBottom: 1 }}
          >
            <MenuItem value="1">Placa se</MenuItem>
            <MenuItem value="0">Besplatno</MenuItem>
          </Select>

          <Button
            variant="contained"
            color="primary"
            onClick={() => {
              props.setQuery(getQuery());
              console.log(getQuery());
            }}
          >
            Filtriraj
          </Button>
        </Box>
      </ResponsiveDrawerButton>
    </>
  );
}

export default Filter;
