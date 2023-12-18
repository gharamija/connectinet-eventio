import React from "react";
import { Box, Drawer, SwipeableDrawer } from "@mui/material";
import { Global } from "@emotion/react";
import { FilterList, SwipeRightOutlined } from "@mui/icons-material";

function ResponsiveDrawer(props) {
  const [mobileOpen, setMobileOpen] = React.useState(false);

  const toggleDrawer = (newOpen) => () => {
    setMobileOpen(newOpen);
  };

  return (
    <Box>
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
            right: -25,
            top: 0,
            bottom: 0,
            visibility: "visible",
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
          }}
        >
          <FilterList sx={{ marginBottom: 1 }} />
          <SwipeRightOutlined />
        </Box>
        <Box sx={{ marginTop: 3 }}>{props.stuff}</Box>
      </SwipeableDrawer>

      <Box sx={{ display: { xs: "none", sm: "block" } }} open>
        {props.stuff}
      </Box>
    </Box>
  );
}

export default ResponsiveDrawer;
