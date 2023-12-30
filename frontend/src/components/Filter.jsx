import React, { useState } from "react";
import {
  Select,
  MenuItem,
  Box,
  InputLabel,
  Button,
  FormControl,
  FormLabel,
  FormHelperText,
} from "@mui/material";
import lokacije from "./Lokacije";
import vrste from "./Vrste";
import ResponsiveDrawerButton from "./ResponsiveDrawerButton";

function Filter(props) {
  const [filter, setFilter] = useState({
    sort: "vrijeme-uzlazno",
    lokacija: "",
    vrijeme: "",
    vrsta: "",
    zavrseno: "",
    placanje: "",
  });

  function handleChange(event) {
    event.stopPropagation();
    const { name, value } = event.target;
    setFilter((oldFilter) => ({
      ...oldFilter,
      [name]: value === "clear" ? "" : value,
    }));
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
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            margin: 2,
            minWidth: 250,
          }}
        >
          <FormControl sx={{ mb: 1 }} size="small">
            <InputLabel id="sort-label">Sortiraj</InputLabel>
            <Select
              labelId="sort-label"
              label="sortiraj"
              name="sort"
              value={filter.sort}
              onChange={handleChange}
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
          </FormControl>

          <FormControl sx={{ mb: 1 }} size="small">
            <InputLabel id="lokacija-label">Lokacija</InputLabel>
            <Select
              labelId="lokacija-label"
              label="lokacija"
              name="lokacija"
              value={filter.lokacija}
              onChange={handleChange}
            >
              <MenuItem key={"clear"} value={"clear"}>
                --
              </MenuItem>
              {lokacije.map((lok) => (
                <MenuItem key={lok} value={lok}>
                  {lok}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl sx={{ mb: 1 }} size="small">
            <InputLabel id="vrijeme-label">Vrijeme</InputLabel>
            <Select
              labelId="vrijeme-label"
              label="vrijeme"
              name="vrijeme"
              value={filter.vrijeme}
              onChange={handleChange}
            >
              <MenuItem key={"clear"} value={"clear"}>
                --
              </MenuItem>
              <MenuItem value="1">24 sata</MenuItem>
              <MenuItem value="7">7 dana</MenuItem>
              <MenuItem value="30">30 dana</MenuItem>
            </Select>
          </FormControl>

          <FormControl sx={{ mb: 1 }} size="small">
            <InputLabel id="vrsta-label">Vrsta</InputLabel>
            <Select
              labelId="vrsta-label"
              label="vrsta"
              name="vrsta"
              value={filter.vrsta}
              onChange={handleChange}
            >
              <MenuItem key={"clear"} value={"clear"}>
                --
              </MenuItem>
              {vrste.map((vrsta) => (
                <MenuItem key={vrsta} value={vrsta}>
                  {vrsta}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl sx={{ mb: 1 }} size="small">
            <InputLabel id="zavrseno-label">Zavrseno</InputLabel>
            <Select
              labelId="zavrseno-label"
              label="zavrseno"
              name="zavrseno"
              value={filter.zavrseno}
              onChange={handleChange}
            >
              <MenuItem key={"clear"} value={"clear"}>
                --
              </MenuItem>
              <MenuItem value="1">Da</MenuItem>
              <MenuItem value="0">Ne</MenuItem>
            </Select>
          </FormControl>

          <FormControl sx={{ mb: 1 }} size="small">
            <InputLabel id="placanje-label">Cijena</InputLabel>
            <Select
              labelId="placanje-label"
              label="placanje"
              name="placanje"
              value={filter.placanje}
              onChange={handleChange}
            >
              <MenuItem key={"clear"} value={"clear"}>
                --
              </MenuItem>
              <MenuItem value="1">Placa se</MenuItem>
              <MenuItem value="0">Besplatno</MenuItem>
            </Select>
          </FormControl>

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
