function User(props) {
    const {id, username, email, uloga, nazivOrganizacije, adresa, poveznica, clanarina} = props.user;
    return (
        <>
            {(uloga === "POSJETITELJ" || uloga === "ADMIN") && <div>{id} {username} {email}</div>}
            {uloga === "ORGANIZATOR" &&
                <div>{id} {username} {email} {nazivOrganizacije} {adresa} {poveznica} {clanarina}</div>}
        </>
    );
}

export default User;