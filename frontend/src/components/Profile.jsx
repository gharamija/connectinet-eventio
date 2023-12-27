import UserProfile from "./UserProfile";
import {RoleContext} from "../App";
import {useContext} from "react";
import OrganizatorProfile from "./OrganizatorProfile";
import AdminProfile from "./AdminProfile";

function Profile() {

    const role = useContext(RoleContext);

    return (
        <>
            {role === "ADMIN" && <AdminProfile/>}
            {role === "ORGANIZATOR" && <OrganizatorProfile/>}
            {role === "POSJETITELJ" && <UserProfile/>}
        </>
    );
}

export default Profile;
