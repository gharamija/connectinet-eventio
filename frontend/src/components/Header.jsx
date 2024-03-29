import {AppBar, Box, Button, Container, Menu, MenuItem, Toolbar, Tooltip, Typography,} from "@mui/material";
import React, {useContext} from "react";
import {useNavigate} from "react-router-dom";
import {RoleContext, UsernameContext} from "../App";

function Header(props) {
    const role = useContext(RoleContext);

    const navigate = useNavigate();

    function logout() {
        props.onLogout();
        navigate("/");
    }

    function getMojiDogadaji() {
        console.log("Dohvacanje mojih dogadaja"); // trenutno samo ispis sto se treba desiti
    }

    const pagesPiO = [
        // PiO = Posjetitelj i Organizator
        {label: "Moji događaji", onClick: () => navigate("/moji")},
        {label: "Svi događaji", onClick: () => navigate("/")},
    ];
    const pagesAdmin = [
        {label: "Svi korisnici", onClick: () => navigate("/korisnici")},
        {label: "Svi događaji", onClick: () => navigate("/")},
    ];
    const settings = [
        {label: "Profil", onClick: () => navigate("/profil")},
        {label: "Odjava", onClick: logout},
    ];

    const pages = role === "ADMIN" ? pagesAdmin : pagesPiO;

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
                            display: {xs: "none", md: "flex"},
                            fontFamily: "monospace",
                            fontWeight: 700,
                            letterSpacing: ".3rem",
                            color: "inherit",
                            textDecoration: "none",
                        }}
                    >
                        EVENTIO
                    </Typography>

                    <Box sx={{flexGrow: 1, display: {xs: 'flex', md: 'flex'}}}>
                        {pages.map((page) => (
                            <Button
                                key={page.label}
                                sx={{
                                    my: 2,
                                    color: "white",
                                    fontSize: {xs: '0.75rem', md: '1rem'}, // Adjust font size for mobile and larger screens
                                    width: {xs: '100%', md: 'auto'}, // Adjust width for mobile and larger screens
                                    padding: { xs: 0, md: '8px 16px' }, // Adjust padding for mobile and larger screens
                                }}
                                onClick={page.onClick}
                            >
                                {page.label}
                            </Button>
                        ))}
                    </Box>

                    <Box sx={{flexGrow: 0}}>
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
                                    fontSize: {xs: '0.75rem', md: '1rem'},
                                    // Adjust font size for mobile and larger screens
                                    padding: { xs: '2px 8px', md: '8px 16px' }, // Adjust padding for mobile and larger screens
                                }}
                            >
                                {useContext(UsernameContext)}
                            </Button>
                        </Tooltip>
                        <Menu
                            sx={{mt: "45px"}}
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
                                    onClick={() => {
                                        setting.onClick(); 
                                        handleCloseUserMenu();
                                    }}
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
