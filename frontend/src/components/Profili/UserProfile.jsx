import {useContext, useEffect, useState} from "react";
import {IdContext} from "../../App";
import {Alert, Box, Button, Collapse, Container, Grid, TextField, Typography} from "@mui/material";
import Editable from "../Editable";
import Notifications from "../Notifications";
import Footer from "../Footer";

const UserProfile = () => {

    const id = useContext(IdContext);

    const [error, setError] = useState(false);
    const [success, setSuccess] = useState(false);
    const [changed, setChanged] = useState(false);
    const [editUsername, setEditUsername] = useState(false);
    const [editPass, setEditPass] = useState(false);
    const [editEmail, setEditEmail] = useState(false);
    const [profile, setProfile] = useState({
        id: "",
        username: "",
        password: "",
        email: "",
        uloga: "",
    });

    useEffect(() => {
        fetch("/api/user").then((response) => {
            response.json().then((details) => {
                setProfile(details);
            });
        });
    }, []);

    function onChange(event) {
        const {name, value} = event.target;
        setProfile({...profile, [name]: value});
    }

    function submit() {
        const replacer = (key, value) => {
            if (key === "id") return undefined;
            return value;
        };
        const options = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(profile, replacer),
        };
        fetch(`/api/user/${id}`, options).then((response) => {
            if (response.status === 200) {
                setSuccess(true);
                setTimeout(() => setSuccess(false), 2000);
            } else {
                setError(true);
            }
        });
    }

    return (
        <>
            <Box sx={{marginBottom: 15}}>
                <Container maxWidth="sm" sx={{mb: 2}}>
                    <Typography variant="h4" mb={1}>
                        Podaci
                    </Typography>
                    <Grid container spacing={1} alignItems="center">
                        <Editable
                            edit={editUsername}
                            editSetter={setEditUsername}
                            value={profile.username}
                            valueSetter={(val) => setProfile({...profile, username: val})}
                            change={setChanged}
                        >
                            <TextField
                                label="username"
                                name="username"
                                value={profile.username}
                                onChange={onChange}
                                disabled={!editUsername}
                                required
                                fullWidth
                            />
                        </Editable>
                        <Editable
                            edit={editPass}
                            editSetter={setEditPass}
                            value={profile.password}
                            valueSetter={(val) => setProfile({...profile, password: val})}
                            change={setChanged}
                        >
                            <TextField
                                label="password"
                                name="password"
                                type="password"
                                value={profile.password}
                                onChange={onChange}
                                disabled={!editPass}
                                required
                                fullWidth
                            />
                        </Editable>
                        <Editable
                            edit={editEmail}
                            editSetter={setEditEmail}
                            value={profile.email}
                            valueSetter={(val) => setProfile({...profile, email: val})}
                            change={setChanged}
                        >
                            <TextField
                                label="email"
                                name="email"
                                type="email"
                                value={profile.email}
                                onChange={onChange}
                                disabled={!editEmail}
                                required
                                fullWidth
                            />
                        </Editable>
                        <Collapse in={error}>
                            <Alert severity="error" sx={{m: 1}}>
                                Došlo je do pogreške
                            </Alert>
                        </Collapse>
                        <Collapse in={success}>
                            <Alert severity="success" sx={{m: 1}}>
                                Promjena uspješna!
                            </Alert>
                        </Collapse>
                        <Grid item xs={5}>
                            <Button
                                variant="contained"
                                size="large"
                                onClick={submit}
                                fullWidth
                                disabled={!changed || editUsername || editPass || editEmail}
                            >
                                Spremi
                            </Button>
                        </Grid>
                    </Grid>
                </Container>
                <Notifications id={id}/>
                <Footer/>
            </Box>
        </>
    );
}

export default UserProfile;