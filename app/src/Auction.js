
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import Header from './Header';
import './Auction.css';


function Auction() {
    const { auctionId } = useParams();
    const [auction, setAuction] = useState(null);
    const [currentImage, setCurrentImage] = useState(0);


    function fetchAuction() {

        fetch(process.env.REACT_APP_API_URL + `/products/${auctionId}`)
            .then(response => response.json())
            .then(data => setAuction(data))
            .catch(() => {});
    }


    useEffect(fetchAuction, [auctionId]);

    function nextImage() {

        setCurrentImage((currentImage + 1) % auction.imageUrls.length);
    }

    function prevImage() {
        setCurrentImage((currentImage + auction.imageUrls.length - 1) % auction.imageUrls.length);
    }

    function addToCart() {
        fetch(process.env.REACT_APP_API_URL + "/cart/add/" + auction.id)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Could not add item to cart');
                }
                else {
                    alert('Dodano do koszyka');
                }

            })
            .catch((error) => {
                console.error('Błąd podczas dodawania do koszyka:', error);
            });

    }


    if (!auction) {
        return <div>Aukcja niedostępna</div>;
    }

    if (auction.imageUrls === null) {
        auction.imageUrls = [];
    }


    return (
        <div>
            <Header />
            <div className="Auction">
                <div className="AuctionTitle">{auction.title}</div>
                <div className="AuctionImage">
                    <img src={process.env.REACT_APP_API_URL + auction.imageUrls[currentImage]} alt="" />
                </div>
                <div className="ImageButtons">
                    <button className="prevImage" onClick={prevImage}> Poprzednie zdjęcie </button>
                    <button className="nextImage" onClick={nextImage}> Następne zdjęcie </button>

                </div>
                <div className="AuctionPrice">{auction.price} zł</div>
                <div className="AuctionDescription">
                    <div className="DescriptionHeader">Opis</div>
                    <p>{auction.description}</p>
                </div>
                <div className="AuctionAddToCart">
                    <button onClick={addToCart}>Dodaj do koszyka</button>
                </div>
             </div>
        </div>
    );
}

export default Auction;
