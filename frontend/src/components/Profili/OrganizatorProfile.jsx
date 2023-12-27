import {useContext, useEffect, useState} from "react";
import {IdContext} from "../../App";
import {Alert, Box, Button, Collapse, Container, Grid, TextField, Typography} from "@mui/material";
import Editable from "../Editable";
import Notifications from "../Notifications";
import Footer from "../Footer";

const organizatorProfile = () => {

    const id = useContext(IdContext);

    const [error, setError] = useState(false);
    const [success, setSuccess] = useState(false);
    const [changed, setChanged] = useState(false);
    const [editUsername, setEditUsername] = useState(false);
    const [editPass, setEditPass] = useState(false);
    const [editEmail, setEditEmail] = useState(false);
    const [editNaziv, setEditNaziv] = useState(false);
    const [editAdresa, setEditAdresa] = useState(false);
    const [editPoveznica, setEditPoveznica] = useState(false);
    const [profile, setProfile] = useState({
        id: "",
        username: "",
        password: "",
        email: "",
        uloga: "",
        nazivOrganizacije: "",
        adresa: "",
        poveznica: "",
        članarina: "",
    });

    useEffect(() => {
        fetch("/api/organizator").then((response) => {
            response.json().then((details) => {
                setProfile(details);
                console.log(details);
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
        fetch(`/api/organizator/${id}`, options).then((response) => {
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
                        <Editable
                            edit={editNaziv}
                            editSetter={setEditNaziv}
                            value={profile.nazivOrganizacije}
                            valueSetter={(val) => setProfile({...profile, nazivOrganizacije: val})}
                            change={setChanged}
                        >
                            <TextField
                                label="naziv"
                                name="naziv"
                                value={profile.nazivOrganizacije}
                                onChange={onChange}
                                disabled={!editNaziv}
                                required
                                fullWidth
                            />
                        </Editable>
                        <Editable
                            edit={editAdresa}
                            editSetter={setEditAdresa}
                            value={profile.adresa}
                            valueSetter={(val) => setProfile({...profile, adresa: val})}
                            change={setChanged}
                        >
                            <TextField
                                label="adresa"
                                name="adresa"
                                value={profile.adresa}
                                onChange={onChange}
                                disabled={!editAdresa}
                                required
                                fullWidth
                            />
                        </Editable>
                        <Editable
                            edit={editPoveznica}
                            editSetter={setEditPoveznica}
                            value={profile.poveznica}
                            valueSetter={(val) => setProfile({...profile, poveznica: val})}
                            change={setChanged}
                        >
                            <TextField
                                label="poveznica"
                                name="poveznica"
                                value={profile.poveznica}
                                onChange={onChange}
                                disabled={!editPoveznica}
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

export default organizatorProfile;