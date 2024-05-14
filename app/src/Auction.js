
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import Header from './Header';
import './style.css';

function Auction() {
    const { auctionId } = useParams();
    const [auction, setAuction] = useState(null);
    const [currentImage, setCurrentImage] = useState(0);


    function fetchAuction() {
        fetch(`http://localhost:8080/auction/${auctionId}`)
            .then(response => response.json())
            .then(data => setAuction(data));
    }

    useEffect(fetchAuction, [auctionId]);

    function nextImage() {
        setCurrentImage((currentImage + 1) % auction.images.length);
    }

    function prevImage() {
        setCurrentImage((currentImage + auction.images.length - 1) % auction.images.length);
    }

    if (!auction) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <Header />
            <div className="Auction">
                <div className="AuctionTitle">{auction.title}</div>
                <div className="AuctionImage">
                    <img src={auction.images[currentImage]} />
                    <br />
                    <button onClick={prevImage}> {"<<"} </button>
                    <button onClick={nextImage}> {">>"} </button>
                </div>
                <div className="AuctionPrice">{auction.price} zł</div>
                <div className="AuctionDescription">
                    <div className="DescriptionHeader">Opis</div>
                    <p>{auction.description}</p></div>
            </div>
        </div>
    );
};

export default Auction;
