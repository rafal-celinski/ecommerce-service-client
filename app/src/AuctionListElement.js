import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import './AuctionListElement.css';

function AuctionListElement({ auction }) {

    function formatDate(stringDate) {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        const date = new Date(stringDate);
        return date.toLocaleDateString('pl-PL', options);
    }

    return (
        <div className="Element" key={auction.id}>
            <div className="Image">
                <img src={"http://localhost:8080" + auction.imageUrls[0]} />
            </div>
            <div className="Info">
                <Link className="Title" to={`/auction/${auction.id}`}>
                    {auction.title}
                </Link>
                <div className="Price">{auction.price} zł</div>
                <div className="LocationDate">{auction.location}, {formatDate(auction.date)}</div>
            </div>
        </div>
    );
}

export default AuctionListElement;