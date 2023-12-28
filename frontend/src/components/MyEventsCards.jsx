import * as React from 'react';
import { useContext } from 'react';
import { Box } from "@mui/material";
import { RoleContext, IdContext } from "../App";
import { QueryContext } from "./Homepage.jsx";
import CustomCard from "./CustomCard.jsx";
import EditDelete from "./EditDelete.jsx";


export default function MyEventsCards(props) {
    const [events, setEvents] = React.useState([]); // ovdje se spremaju eventi za pojedinog korisnika koji ce se prikazati
    const role = useContext(RoleContext);
    const id = useContext(IdContext);
    const query = useContext(QueryContext);


    React.useEffect(() => {
        if (role === "POSJETITELJ" && query === "") {
            fetch(`/api/user/${id}`).then((response) => {
                if (response.ok) {
                    response.json().then((events) => {
                        setEvents(events);
                    });
                }
            });
        } else if (role === "ORGANIZATOR" && query === "") {
            fetch(`/api/organizator/${id}`).then((response) => {
                if (response.ok) {
                    response.json().then((events) => {
                        setEvents(events);
                    });
                }
            });
        } else if (role === "ADMIN" && query === "") { // u slucaju da je admin prijavljen, dohvacamo sve dogadjaje
            fetch(`/api/filter`).then((response) => {
                if (response.ok) {
                    response.json().then((events) => {
                        setEvents(events);
                    });
                }
            });
        } else {
            console.log('Za trenutnog Aliena nema dogadjaja na Zemlji');
        }
    }, [role, id, query]);

    return (
        <Box>
            <ul>
                {events.map((event) => (
                    <InterestCounter interest={99}>
                        <CustomCard event={event}>
                            {role === "ADMIN" && <EditDelete />}
                            {role === "ORGANIZATOR" && event.organizator === id && <EditDelete />}
                        </CustomCard>
                    </InterestCounter>
                ))}
            </ul>
        </Box>
    );
}
