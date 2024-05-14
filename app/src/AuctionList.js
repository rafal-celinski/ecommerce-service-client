import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

function AuctionList({filterData}) {

    const [auctions, setAuctions] = useState([]);

    function fetchAuctions() {
        if (filterData) {
            console.log(filterData);
            fetch('http://localhost:8080/auctions', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(filterData),
            })
                .then(response => response.json())
                .then(auctions => {
                    setAuctions(auctions);
                });
        }
    }


    useEffect(fetchAuctions, [filterData]);
    useEffect(fetchAuctions, []);

    function formatDate(stringDate) {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        const date = new Date(stringDate);
        return date.toLocaleDateString('pl-PL', options);
    }


    if (auctions.length > 0) {
        return (
            <div>
                {auctions.map((auction) => (
                    <div className="AuctionSummary" key={auction.id}>
                        <div className="SummaryImage">
                            <img src={auction.image} />
                        </div>
                        <div className="SummaryInfo">
                            <Link className="SummaryTitle" to={`/auction/${auction.id}`}>
                                {auction.title}
                            </Link>
                            <div className="SummaryPrice">{auction.price} zł</div>
                            <div className="SummaryLocationDate">{auction.location}, {formatDate(auction.date)}</div>
                        </div>
                    </div>
                ))}
            </div>
        );
    }
    else{
        return <div>Brak aukcji spełniających kryteria</div>;
    }

};

export default AuctionList;