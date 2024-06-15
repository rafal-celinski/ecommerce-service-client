import React, {useEffect, useState} from 'react';
import './PurchaseHistory.css';
import {Link} from "react-router-dom";

function PurchaseHistory() {
    const [history, setHistory] = useState(null);

    function fetchHistory() {
        fetch(process.env.REACT_APP_API_URL + "/cart/archive?size=50")
            .then(response => response.json())
            .then(data => setHistory(data.content));
    }

    useEffect(fetchHistory, []);

    function formatDate(stringDate) {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        const date = new Date(stringDate);
        return date.toLocaleDateString('pl-PL', options);
    }

    function formatState(state) {
        if(state === "pending"){
            return "Oczekuje na dostawę";
        }
        if(state === "delivered"){
            return "Dostarczono";
        }
        if(state === "sent"){
            return "Wysłano";
        }
        if(state === "cancelled"){
            return "Anulowano";
        }
    }

    if (!history) {
        return <div>Ładowanie...</div>;
    }

    if(history.length ===0){
        return <div>Brak historii zakupów</div>;
    }

    return (
        <div className="PurchaseHistory">
            <div className="PurchaseHistoryHeader">Historia zakupów</div>
            <div className="Products">
                {history.map((item) => (
                    <div className="Product" key={item.id}>
                        <div className="ProductImage">
                            {item.imageUrls !== null && <img src={process.env.REACT_APP_API_URL + item.imageUrl[0]} alt="" />}
                        </div>
                        <div className="ProductInfo">
                            <Link className="ProductTitle" to={`/auction/${item.id}`}>
                                {item.title}
                            </Link>

                            <div className="ProductTotalPrice">{item.price} zł</div>
                            <div className="ProductDateAndState">
                                <span>{formatDate(item.date)}</span>
                                <span className="ProductState">{formatState(item.state)}</span>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );

}

export default PurchaseHistory;