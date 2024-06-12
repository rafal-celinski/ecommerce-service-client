import React, { useState, useEffect } from 'react';
import Header from "./Header";
import UserData from "./UserData";
import './UserProfile.css';
import PurchaseHistory from "./PurchaseHistory";


function UserProfile() {

    return (
        <div>
            <Header />
            <div className="UserProfile">
                <UserData />
                <PurchaseHistory />
            </div>
        </div>
    );
}

export default UserProfile;