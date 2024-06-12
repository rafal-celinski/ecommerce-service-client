// App.js
import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './Home';
import Auction from './Auction';
import Cart from './Cart';
import UserProfile from './UserProfile';

function App() {
    return (
            <BrowserRouter>
                <Routes>
                    <Route path="/" element=<Home /> />
                    <Route path="/auction/:auctionId" element=<Auction /> />
                    <Route path="/cart" element=<Cart /> />
                    <Route path="/profile" element=<UserProfile /> />
                </Routes>
            </BrowserRouter>
    );
}

export default App;
