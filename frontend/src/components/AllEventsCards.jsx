import * as React from 'react';
import { useContext } from 'react';
import { Box } from "@mui/material";
import { RoleContext, IdContext } from "../App.jsx";
import { QueryContext } from "./Homepage.jsx";
import CustomCard from "./CustomCard.jsx";
import EditDelete from "./EditDelete.jsx";
import InterestCounter from "./InterestCounter.jsx";


export default function AllEventsCards(props) {
    const [events, setEvents] = React.useState([]); // ovdje se spremaju eventi za pojedinog korisnika koji ce se prikazati
    const role = useContext(RoleContext);
    const query = useContext(QueryContext);
    const id = useContext(IdContext);


    React.useEffect(() => {
        fetch(`/api/filter${query}`).then((response) => {
            if (response.ok) {
                response.json().then((events) => {
                    setEvents(events);
                });
            }
        });
    }, [query]);
    // ako je query "" onda prikazuje sve dogadjaje

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
