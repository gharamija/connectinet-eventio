import { useState } from "react";
import { IconButton, Grid } from "@mui/material";
import { Check, Close, Edit } from "@mui/icons-material";

function Editable({ edit, editSetter, value, valueSetter, change, children }) {
  const [oldValue, setOldValue] = useState("");

  return (
    <Grid
      item
      container
      spacing={1}
      alignItems="center"
      component="form"
      onSubmit={(e) => {
        e.preventDefault();
        editSetter(false);
        change(true);
      }}
    >
      <Grid item xs={10}>
        {children}
      </Grid>
      {!edit && (
        <Grid item xs={1}>
          <IconButton
            onClick={() => {
              editSetter(true);
              setOldValue(value);
            }}
            sx={{ p: 0 }}
          >
            <Edit />
          </IconButton>
        </Grid>
      )}
      {edit && (
        <Grid item xs={1}>
          <IconButton type="submit" sx={{ p: 0 }}>
            <Check />
          </IconButton>
        </Grid>
      )}
      {edit && (
        <Grid item xs={1}>
          <IconButton
            onClick={() => {
              valueSetter(oldValue);
              editSetter(false);
            }}
            sx={{ p: 0 }}
          >
            <Close />
          </IconButton>
        </Grid>
      )}
    </Grid>
  );
}

export default Editable;
