import UserProfile from "./Profili/UserProfile";
import {RoleContext} from "../App";
import {useContext} from "react";
import OrganizatorProfile from "./Profili/OrganizatorProfile";
import AdminProfile from "./Profili/AdminProfile";

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
