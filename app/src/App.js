// App.js
import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './Home';
import Auction from './Auction';

function App() {
    return (
            <BrowserRouter>
                <Routes>
                    <Route path="/" element=<Home /> />
                    <Route path="/auction/:auctionId" element=<Auction /> />
                </Routes>
            </BrowserRouter>
    );
}

export default App;
