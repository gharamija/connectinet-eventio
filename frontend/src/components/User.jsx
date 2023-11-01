function User(props) {
  const { id, name } = props.user;
    return (
        <p>{id} {name}</p>
    );
}

export default User;