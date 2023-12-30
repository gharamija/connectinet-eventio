import * as React from "react";
import {Box, Button, Grid} from "@mui/material";
import EnhancedTable from "./TableComponents/EnhancedTable";
import {useState} from "react";

function UserList() {
    const [isAllowed, setIsAllowed] = React.useState(false);
    const [users, setUsers] = React.useState([]);

    React.useEffect(() => {
        fetch("api/user/all").then((response) => {
            if (response.ok) {
                setIsAllowed(true);
                response.json().then((users) => setUsers(users));
            }
        });
    }, []);

    const [posjetiteljiButton, setPosjetiteljiButton] = useState(true);
    const [organizatoriButton, setOrganizatoriButton] = useState(false);

    const handleButtonClick = (button) => {
        if (button === "posjetitelji") {
            setPosjetiteljiButton(true);
            setOrganizatoriButton(false);
        } else if (button === "organizatori") {
            setPosjetiteljiButton(false);
            setOrganizatoriButton(true);
        }
    };

    if (isAllowed) {
        return (
            <>
                <Box sx={{my: 2, mx: 1, marginBottom: 5, marginTop: 5}}></Box>
                <div>
                    <Grid container spacing={2} justifyContent="center">
                        <Grid item>
                            <Button variant={posjetiteljiButton ? "contained" : "outlined"} color="primary"
                                    onClick={() => handleButtonClick("posjetitelji")}>
                                Posjetitelji
                            </Button>
                        </Grid>
                        <Grid item>
                            <Button variant={organizatoriButton ? "contained" : "outlined"} color="primary"
                                    onClick={() => handleButtonClick("organizatori")}>
                                Organizatori
                            </Button>
                        </Grid>
                    </Grid>
                </div>

                {posjetiteljiButton && <EnhancedTable users={users} role={"POSJETITELJ"}/>}
                {organizatoriButton && <EnhancedTable users={users} role={"ORGANIZATOR"}/>}
            </>
    )
        ;
    } else {
        return <Box>Ne smijete vidjeti sve korisnike</Box>;
    }
}

export default UserList;
