function User(props) {
  const { id, username } = props.user;
    return (
        <p>{id} {username}</p>
    );
}

export default User;