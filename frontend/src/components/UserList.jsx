import * as React from "react";
import User from "./User.jsx";

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
      <div>
        <ul>
          {users.map((user) => (
            <li key={user.id}>{<User user={user} />}</li>
          ))}
        </ul>
      </div>
    );
  } else {
    return <div>Ne smijete vidjeti sve korisnike</div>;
  }
}

export default UserList;
