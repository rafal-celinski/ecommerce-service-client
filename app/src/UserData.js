import React, {useEffect, useState} from "react";
import './UserData.css';

function UserData() {
    const [profile, setProfile] = useState(null);

    function fetchProfile() {
        fetch("http://localhost:8000/profile.json")
            //fetch(process.env.REACT_APP_API_URL + "/profile")
            .then(response => response.json())
            .then(data => setProfile(data));
    }

    useEffect(fetchProfile, []);

    if (!profile) {
        return <div>Ładowanie...</div>;
    }

    return (
        <div className="UserData">
            <div className="UserDataHeader">Twoje Dane</div>
            <div className="Name">
                <div className="NameHeader">Imię i nazwisko</div>
                <div className="NameData">{profile.firstName} {profile.lastName}</div>
            </div>
            <div className="Email">
                <div className="EmailHeader">Email</div>
                <div className="EmailData">{profile.email}</div>
            </div>
            <div className="Address">
                <div className="AddressHeader">Adres</div>
                <div className="AddressData">
                    <span>{profile.address.street}</span>
                    <span>{profile.address.postalCode} {profile.address.city}</span>
                    <span>{profile.address.country}</span>
                </div>
            </div>
        </div>
    );
}

export default UserData;