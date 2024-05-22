import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import AuctionListElement from './AuctionListElement';
import './AuctionList.css'

function AuctionList({filterData}) {

    const [auctions, setAuctions] = useState([]);
    const [pageInfo, setPageInfo] = useState({
        first: false,
        last: false,
        pageNumber: 0,
        totalPages: 0,
    });

    const  [pageToLoad, setPageToLoad] = useState('');

    function fetchAuctions() {
        if (filterData) {
            // fetch('http://localhost:8080/auctions', {
            //     method: 'POST',
            //     headers: {
            //         'Content-Type': 'application/json',
            //     },
            //     body: JSON.stringify(filterData),
            // })
            //     .then(response => response.json())
            //     .then(auctions => {
            //         setAuctions(auctions);
            //     });

            fetch(
                "http://localhost:8080/products/search?" +
                "category=" + filterData.category +
                "&subcategory=" + filterData.subcategory +
                "&minPrice=" + filterData.minPrice +
                "&maxPrice=" + filterData.maxPrice +
                "&location=" + filterData.location +
                "&page=" + pageToLoad +
                "&size=10"
            )
                .then(response => response.json())
                .then(auctions => {
                    setAuctions(auctions.content);
                    setPageInfo({first: auctions.first, last: auctions.last,pageNumber: auctions.number, totalPages: auctions.totalPages});
                });
            console.log(auctions);

        }
    }
    
    useEffect(fetchAuctions, [filterData, pageToLoad]);

    function prevPage() {
        if (!pageInfo.first) {
            setPageToLoad(pageInfo.pageNumber - 1);
        }
    }

    function nextPage() {
        if (!pageInfo.last) {
            setPageToLoad(pageInfo.pageNumber + 1);
        }
    }

    useEffect(fetchAuctions, [pageInfo.pageNumber]);


    if (auctions && auctions.length > 0) {
        return (
            <div className="AuctionList">
                <div className="Auctions">

                    {auctions.map((auction) => (
                        <div className="ListElement" key={auction.id}><AuctionListElement auction={auction} key={auction.id} /></div>

                    ))}


                </div>
                <div className="Pagination">
                    {!pageInfo.first && <button className="prevPage" onClick={prevPage}>Poprzednia strona</button>}
                    <div className="Page">Strona: {pageInfo.pageNumber + 1} z {pageInfo.totalPages}</div>
                    {!pageInfo.last && <button className="nextPage" onClick={nextPage}>Następna strona</button>}
                </div>

            </div>
        );
    }
    else{
        return <div>Brak aukcji spełniających kryteria</div>;
    }

};

export default AuctionList;