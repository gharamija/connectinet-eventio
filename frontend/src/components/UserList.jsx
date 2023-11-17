import * as React from "react"
import User from './User.jsx'

function UserList() {
  const [users, setUsers] = React.useState([]);

  React.useEffect(() => {
    fetch('api/users')
      .then(data => data.json())
      .then(users => setUsers(users))
  }, []);

  return (
    <div>
      {
        users.map(user =>
          <User key={user.id}
                user={user} />)
      }
    </div>
  );
}

export default UserList;
