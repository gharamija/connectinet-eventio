import {
  AppBar,
  Box,
  Button,
  Container,
  Menu,
  MenuItem,
  Toolbar,
  Tooltip,
  Typography,
} from "@mui/material";
import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { RoleContext } from "../App";

function Header(props) {
  const role = useContext(RoleContext);

  const navigate = useNavigate();

  function logout() {
    fetch("/logout").then(() => {
      props.onLogout();
    });
  }

  function getMojiDogadaji() {
    console.log("Dohvacanje mojih dogadaja"); // trenutno samo ispis sto se treba desiti
  }

  function getDogadaji() {
    console.log("Dohvacanje svih dogadaja"); // trenutno samo ispis sto se treba desiti
  }

  function getProfil() {
    console.log("Dohvacanje profila"); // trenutno samo ispis sto se treba desiti
  }

  function getKorisnici() {
    console.log("Dohvacanje korisnika"); // trenutno samo ispis sto se treba desiti
  }

  const pagesPiO = [
    { label: "Moji događaji", onclick: getMojiDogadaji },
    {
      label: "Svi događaji",
      onclick: getDogadaji,
    },
  ];
  const pagesAdmin = [
    { label: "Svi korisnici", onclick: getKorisnici },
    { label: "Svi događaji", onclick: getDogadaji },
  ];
  const settings = [
    { label: "Profil", onClick: () => navigate("/profil") },
    { label: "Odjava", onClick: logout },
  ];

  const pages = role === "Admin" ? pagesAdmin : pagesPiO;

  const [anchorElUser, setAnchorElUser] = React.useState(null);

  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  return (
    <AppBar
      position="static"
      sx={{
        marginBottom: "20px",
        backgroundColor:
          role === "POSJETITELJ"
            ? "#2196F3" // Blue for Posjetitelj
            : role === "ORGANIZATOR"
            ? "#4CAF50" // Green for Organizator
            : role === "ADMIN"
            ? "#FF0000" // Red for Admin
            : "#000000",
      }}
    >
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <Typography
            variant="h4"
            noWrap
            component="a"
            sx={{
              mr: 2,
              display: { xs: "none", sm: "flex" },
              fontFamily: "monospace",
              fontWeight: 700,
              letterSpacing: ".3rem",
              color: "inherit",
              textDecoration: "none",
            }}
          >
            EVENTIO
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: "none", sm: "flex" } }}>
            {pages.map((page) => (
              <Button
                key={page.label}
                sx={{ my: 2, color: "white", display: "block" }}
                onClick={page.onclick}
              >
                {page.label}
              </Button>
            ))}
          </Box>

          <Box sx={{ flexGrow: 0 }}>
            <Tooltip title="Open settings">
              <Button
                size="medium"
                variant="contained"
                onClick={handleOpenUserMenu}
                sx={{
                  backgroundColor: "inherit",
                  "&:hover": {
                    backgroundColor: "inherit", // Add the same color for hover state
                  },
                }}
              >
                {role}
              </Button>
            </Tooltip>
            <Menu
              sx={{ mt: "45px" }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
            >
              {settings.map((setting) => (
                <MenuItem
                  key={setting.label}
                  onClick={setting.onClick || handleCloseUserMenu}
                >
                  <Typography textAlign="center">{setting.label}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
}

export default Header;
