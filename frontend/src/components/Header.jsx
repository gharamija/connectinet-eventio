import "./Header.css";

function Header(props) {

    function logout() {
        fetch("/logout").then(() => {
            props.onLogout();
        });
    }

    return (
        <header className="Header">
            <button onClick={logout}>Logout</button>
        </header>
    )
}

export default Header;
