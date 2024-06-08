import React, { useState, useEffect } from 'react';
import Header from "./Header";
import UserData from "./UserData";
import './UserProfile.css';


function UserProfile() {

    return (
        <div>
            <Header />
            <div className="UserProfile">
                <UserData />
            </div>
        </div>
    );
}

export default UserProfile;