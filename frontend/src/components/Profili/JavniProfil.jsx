import { useState, useEffect } from "react";
import { Box, Container, TextField, Typography } from "@mui/material";
import { useParams } from "react-router-dom";
import CustomCard from "../CustomCard";

function JavniProfil() {
  let { orgId } = useParams();

  const [profile, setProfile] = useState();
  const [events, setEvents] = useState();

  useEffect(() => {
    fetch("/api/organizator/" + orgId).then((response) => {
      if (response.status === 200) {
        response.json().then((details) => {
          setProfile(details);
        });
      }
    });
    fetch("/api/dogadaj/organizator/" + orgId).then((response) => {
      if (response.ok) {
        response.json().then((events) => {
          setEvents(events);
        });
      }
    });
  }, []);

  if (!profile || !events) {
    return (
      <Typography variant="h4" mb={1}>
        Nije moguće pronaći organizatora
      </Typography>
    );
  }

  //provjerava ako je datum unutar prošle dvije godine
  function dvijeGodine(dat) {
    let beforeTwoYears = new Date();
    beforeTwoYears.setFullYear(beforeTwoYears.getFullYear() - 2);
    return beforeTwoYears < dat && dat < Date.now();
  }

  return (
    <>
      <Container maxWidth="sm" sx={{ mb: 15 }}>
        <Typography variant="h4" mb={1}>
          Podaci organizatora
        </Typography>
        <TextField
          label="Korisničko ime"
          name="username"
          value={profile.username}
          fullWidth
          margin="dense"
          size="small"
        />
        <TextField
          label="Email"
          name="email"
          type="email"
          value={profile.email}
          fullWidth
          margin="dense"
          size="small"
        />
        <TextField
          label="Naziv organizacije"
          name="naziv"
          value={profile.nazivOrganizacije}
          fullWidth
          margin="dense"
          size="small"
        />
        <TextField
          label="Adresa"
          name="adresa"
          value={profile.adresa}
          fullWidth
          margin="dense"
          size="small"
        />
        <TextField
          label="Poveznica"
          name="poveznica"
          value={profile.poveznica}
          fullWidth
          margin="dense"
          size="small"
        />
        <Typography variant="h4" mb={1} mt={2}>
          Događaji u zadnje 2 godine
        </Typography>
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            gap: 3,
          }}
        >
          {events.map(
            (event) =>
              dvijeGodine(new Date(event.vrijemePocetka)) && (
                <CustomCard key={event.dogadajId} event={event} />
              )
          )}
        </Box>
      </Container>
    </>
  );
}

export default JavniProfil;
