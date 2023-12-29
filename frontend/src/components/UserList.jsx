import * as React from "react";
import User from "./User.jsx";
import {Box, Divider} from "@mui/material";
import EnhancedTable from "./TableComponents/EnhancedTable";

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

    if (isAllowed) {
        return (
            <>
                <EnhancedTable users={users} role={"POSJETITELJ"}/>

                <Box sx={{my: 2, mx: 1, marginBottom: 5, marginTop: 5}}>
                    <Divider/>
                </Box>
                <Box sx={{my: 2, mx: 1, marginBottom: 5, marginTop: 5}}>
                    <Divider/>
                </Box>

                <EnhancedTable users={users} role={"ORGANIZATOR"}/>
            </>
    )
        ;
    } else {
        return <Box>Ne smijete vidjeti sve korisnike</Box>;
    }
}

export default UserList;
