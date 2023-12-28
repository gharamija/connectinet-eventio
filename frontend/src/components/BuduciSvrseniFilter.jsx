import React, { useState } from "react";
import {
  Select,
  MenuItem,
  Box,
  InputLabel,
  Button,
  FormControl,
} from "@mui/material";
import ResponsiveDrawerButton from "./ResponsiveDrawerButton";

function BuduciSvrseniFilter(props) {
  const [filter, setFilter] = useState({
    zavrseno: "",
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
    if (filter.zavrseno) query += `?zavrseno=${filter.zavrseno}`;
    return query;
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

export default BuduciSvrseniFilter;
