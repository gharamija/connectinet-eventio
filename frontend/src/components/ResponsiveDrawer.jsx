import React from "react";
import { Box, Drawer, SwipeableDrawer } from "@mui/material";
import { ArrowForwardIosRounded } from "@mui/icons-material";
import { Global } from "@emotion/react";

function ResponsiveDrawer(props) {
  const [mobileOpen, setMobileOpen] = React.useState(false);

  const toggleDrawer = (newOpen) => () => {
    setMobileOpen(newOpen);
  };

  return (
    <Box sx={{ position: "relative" }}>
      <Global
        styles={{
          ".MuiDrawer-root > .MuiPaper-root": {
            overflow: "visible",
          },
        }}
      />
      <SwipeableDrawer
        open={mobileOpen}
        onOpen={toggleDrawer(true)}
        onClose={toggleDrawer(false)}
        anchor="left"
        disableSwipeToOpen={false}
        swipeAreaWidth={50}
        ModalProps={{
          keepMounted: true, // Better open performance on mobile.
        }}
        sx={{
          display: { xs: "block", sm: "none" },
          "& .MuiDrawer-root": { position: "absolute" },
          "& .MuiPaper-root": { position: "absolute" },
          "& .MuiDrawer-paper": { boxSizing: "border-box", width: "auto" },
        }}
      >
        <Box
          sx={{
            position: "absolute",
            right: -20,
            top: 0,
            bottom: 0,
            visibility: "visible",
            display: "flex",
            alignItems: "center",
          }}
        >
          <ArrowForwardIosRounded />
        </Box>
        {props.stuff}
      </SwipeableDrawer>
      <Drawer
        variant="persistent"
        sx={{
          display: { xs: "none", sm: "block" },
          width: 250,
          "& .MuiDrawer-root": { position: "absolute" },
          "& .MuiPaper-root": { position: "absolute" },
          "& .MuiDrawer-paper": { boxSizing: "border-box", width: "auto" },
        }}
        open
      >
        {props.stuff}
      </Drawer>
    </Box>
  );
}

export default ResponsiveDrawer;
