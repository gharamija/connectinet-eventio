import * as React from 'react';
import { useState } from 'react';
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import { Box } from "@mui/material";
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import AddIcon from '@mui/icons-material/Add';
import Fab from '@mui/material/Fab';
import MoreVertIcon from '@mui/icons-material/MoreVert';

const ExpandMore = styled((props) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
})(({ theme, expand }) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
}));

export default function RecipeReviewCard(props) {
    const [expanded, setExpanded] = React.useState([]);
    const [events, setEvents] = React.useState([]); // ovdje se spremaju eventi za pojedinog korisnika koji ce se prikazati
    const [currentUser, setCurrentUser] = React.useState('');;

    const handleExpandClick = (index) => {
        setExpanded((prevExpanded) => {
            const updatedExpanded = [...prevExpanded];
            updatedExpanded[index] = !updatedExpanded[index];
            return updatedExpanded;
        });
    };


    React.useEffect(() => {
        fetch("/api/user").then((response) => {  // dohvacanje trenutnog korisnika da mozemo poslat dobar get request za dogadjaje
            if (response.ok) {
                response.json().then((user) => setCurrentUser(user.id));
            }
        });
        fetch("?").then((response) => {  // provjerit s deckima sto ce slat i kako cu pitat za to
            if (response.ok) {  // umjesto upitnika ide personaliziran url u koji umecemo currentUser kako bi dohvatili dogadjaje za trenutnog usera
                response.json().then((events) => {
                    const initialExpandedState = Array(events.length).fill(false);
                    setExpanded(initialExpandedState);
                    setEvents(events);
                });
            }
        });
    }, []);

    if (props.role === "ADMIN") {
        return (
            <Box>
                <ul>
                    {events.map((event, index) => (
                        <Card sx={{ maxWidth: 345 }} key={index}>
                            <CardHeader
                                avatar={
                                    <Avatar sx={{ bgcolor: red[500] }} aria-label="event">
                                        {event.organizator}
                                    </Avatar>
                                }
                                title={event.naslov}
                                subheader={event.organizator}
                            />
                            <CardMedia
                                component="img"
                                height="194"
                                image={event.slika}
                            />
                            <CardContent>
                                <Typography variant="body2" color="text.secondary">
                                    {event.kratkiOpis}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    {event.lokacija}
                                </Typography>
                            </CardContent>
                            <CardActions disableSpacing>
                                <IconButton aria-label="edit">
                                    <EditIcon />
                                </IconButton>
                                <IconButton aria-label="delete">
                                    <DeleteIcon />
                                </IconButton>
                                <ExpandMore
                                    expand={expanded}
                                    onClick={() => handleExpandClick(index)}
                                    aria-expanded={expanded}
                                    aria-label="show more"
                                >
                                    <ExpandMoreIcon />
                                </ExpandMore>
                            </CardActions>
                            <Collapse in={expanded} timeout="auto" unmountOnExit>
                                <CardContent>
                                    <Typography paragraph>Opis:</Typography>
                                    <Typography paragraph>
                                        {event.dugiOpis}
                                    </Typography>
                                </CardContent>
                            </Collapse>
                        </Card>
                    ))}
                </ul>
            </Box>
        );
    } else if (props.role === "ORGANIZATOR") {
        return (
            <Box>
                <ul>
                    {events.map((event, index) => (
                        <Card sx={{ maxWidth: 345 }} key={index}>
                            <CardHeader
                                avatar={
                                    <Avatar sx={{ bgcolor: red[500] }} aria-label="event">
                                        {event.organizator}
                                    </Avatar>
                                }
                                title={event.naslov}
                                subheader={event.organizator}
                            />
                            <CardMedia
                                component="img"
                                height="194"
                                image={event.slika}
                            />
                            <CardContent>
                                <Typography variant="body2" color="text.secondary">
                                    {event.kratkiOpis}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    {event.lokacija}
                                </Typography>
                            </CardContent>
                            <CardActions disableSpacing>
                                <ExpandMore
                                    expand={expanded}
                                    onClick={() => handleExpandClick(index)}
                                    aria-expanded={expanded}
                                    aria-label="show more"
                                >
                                    <ExpandMoreIcon />
                                </ExpandMore>
                            </CardActions>
                            <Collapse in={expanded} timeout="auto" unmountOnExit>
                                <CardContent>
                                    <Typography paragraph>Opis:</Typography>
                                    <Typography paragraph>
                                        {event.dugiOpis}
                                    </Typography>
                                </CardContent>
                            </Collapse>
                        </Card>
                    ))}
                </ul>
                <Fab    // plutajuci gumb za dodavanje novih događaja
                    color="primary"
                    aria-label="add event"
                    onClick={handleScrollToTop}
                    style={{
                        position: 'sticky',
                        bottom: '50px', // promijeni po potrebi
                        right: '50px', // promijeni po potrebi
                        zIndex: 1000, // promijeni po potrebi
                    }}
                >
                    <AddIcon />
                </Fab>
            </Box>
        );
    } else if (props.role === "POSJETITELJ") {
        return (
            <Box>
                <ul>
                    {events.map((event, index) => (
                        <Card sx={{ maxWidth: 345 }} key={index}>
                            <CardHeader
                                avatar={
                                    <Avatar sx={{ bgcolor: red[500] }} aria-label="event">
                                        {event.organizator}
                                    </Avatar>
                                }
                                title={event.naslov}
                                subheader={event.organizator}
                            />
                            <CardMedia
                                component="img"
                                height="194"
                                image={event.slika}
                            />
                            <CardContent>
                                <Typography variant="body2" color="text.secondary">
                                    {event.kratkiOpis}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    {event.lokacija}
                                </Typography>
                            </CardContent>
                            <CardActions disableSpacing>
                                <ExpandMore
                                    expand={expanded}
                                    onClick={() => handleExpandClick(index)}
                                    aria-expanded={expanded}
                                    aria-label="show more"
                                >
                                    <ExpandMoreIcon />
                                </ExpandMore>
                            </CardActions>
                            <Collapse in={expanded} timeout="auto" unmountOnExit>
                                <CardContent>
                                    <Typography paragraph>Opis:</Typography>
                                    <Typography paragraph>
                                        {event.dugiOpis}
                                    </Typography>
                                </CardContent>
                            </Collapse>
                        </Card>
                    ))}
                </ul>
            </Box>
        );
    }
}
// .naslov .organizator .slika .kratkiOpis .dugiOpis .lokacija
// ovo sve trebam dobit kad fetch-am listu dogadjaja za pojedinacnog korisnika
