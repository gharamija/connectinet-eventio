import * as React from "react";
import User from "./User.jsx";
import { Box } from "@mui/material";

function UserList(props) {
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
      <Box>
        <ul>
          {users.map((user) => (
            <li key={user.id}>{<User user={user} />}</li>
          ))}
        </ul>
      </Box>
    );
  } else {
    return <Box>Ne smijete vidjeti sve korisnike</Box>;
  }
}

export default UserList;
