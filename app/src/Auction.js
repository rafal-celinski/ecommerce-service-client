
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import Header from './Header';

import './Auction.css';

function Auction() {
    const { auctionId } = useParams();
    const [auction, setAuction] = useState(null);
    const [currentImage, setCurrentImage] = useState(0);


    function fetchAuction() {
        fetch(`http://localhost:8080/products/${auctionId}`)
            .then(response => response.json())
            .then(data => setAuction(data));
    }

    useEffect(fetchAuction, [auctionId]);

    function nextImage() {
        setCurrentImage((currentImage + 1) % auction.imageUrls.length);
    }

    function prevImage() {
        setCurrentImage((currentImage + auction.imageUrls.length - 1) % auction.imageUrls.length);
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
                    <img src={"http://localhost:8080" + auction.imageUrls[currentImage]} alt="" />
                </div>
                <div className="ImageButtons">
                    <button className="prevImage" onClick={prevImage}> Poprzednie zdjęcie </button>
                    <button className="nextImage" onClick={nextImage}> Następne zdjęcie </button>
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
