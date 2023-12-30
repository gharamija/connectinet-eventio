import React from "react";
import { Box, Drawer, Button } from "@mui/material";
import { Close, FilterList } from "@mui/icons-material";

function ResponsiveDrawerButton(props) {
  const [mobileOpen, setMobileOpen] = React.useState(false);

  const toggleDrawer = (newOpen) => () => {
    setMobileOpen(newOpen);
  };

  return (
    <Box>
      <Box sx={{ display: { xs: "block", md: "none" }, mb: 2 }} open>
        <Button
          fullWidth
          variant="outlined"
          startIcon={<FilterList />}
          color="primary"
          size="small"
          onClick={toggleDrawer(true)}
        >
          Filtiraj
        </Button>
        <Drawer variant="persistent" open={mobileOpen} anchor="bottom">
          <Button
            variant="text"
            startIcon={<Close />}
            size="small"
            onClick={toggleDrawer(false)}
          >
            Zatvori
          </Button>
          {props.children}
        </Drawer>
      </Box>

      <Box sx={{ display: { xs: "none", md: "block" } }} open>
        {props.children}
      </Box>
    </Box>
  );
}

export default ResponsiveDrawerButton;
