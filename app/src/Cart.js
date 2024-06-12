import React, { useState, useEffect } from 'react';
import Header from "./Header";
import './Cart.css';
import {Link} from "react-router-dom";

function Cart() {
    const [cart, setCart] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchCart();
    }, []);

    async function fetchCart() {
            //fetch(process.env.REACT_APP_API_URL + `/cart`)
            fetch(process.env.REACT_APP_API_URL + "/cart")
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Could not fetch cart');
                    }
                    return response.json();
                })
                .then(data => setCart(data))
                .catch(caught_error => setCart([]))
                .finally(() => setLoading(false));
    }

    async function updateCart(updatedCart){
        fetch(process.env.REACT_APP_API_URL + "/cart", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updatedCart)
        })
            .catch((error) => {
                console.error('Błąd podczas aktualizacji koszyka: ', error);
            });

    }

    function handleQuantityChange(productId, quantity) {
        const updatedCart = cart.map(item =>
            item.id === productId ? { ...item, quantity } : item
        );
        setCart(updatedCart);
        updateCart(updatedCart);
    }

    function handleRemoveItem(productId) {
        const updatedCart = cart.filter(item => item.id !== productId);
        setCart(updatedCart);
        updateCart(updatedCart);
    }

    if (loading) {
        return <div>Loading...</div>;
    }

    if (cart.length === 0) {
        return (
            <div>
                <Header />
                <div className="Cart">Twój koszyk jest pusty</div>
            </div>
        );
    }

    return (
        <div>
            <Header />
            <div className="Cart">
                <div className="Products">
                    {cart.map((item) => (
                        <div className="Product" key={item.id}>
                            <div className="ProductImage">
                                <img src={item.imageUrl[0]} alt="" />
                            </div>
                            <div className="ProductInfo">
                                <Link className="ProductTitle" to={`/auction/${item.id}`}>
                                    {item.title}
                                </Link>
                                <div className="ProductQuantity">
                                    <input
                                        type="number"
                                        min = "1"
                                        value={item.quantity}
                                        onChange={(e) => handleQuantityChange(item.id, parseInt(e.target.value))}
                                    />
                                </div>
                                <div className="ProductTotalPrice">{item.price * item.quantity} zł</div>
                                <button className="ProductRemove" onClick={() => handleRemoveItem(item.id)}>Usuń</button>
                            </div>
                        </div>
                    ))}
                </div>
                <div className="CartSummary">
                    <div className="CartTotalPrice">
                        Łączna kwota: {cart.reduce((acc, item) => acc + item.price * item.quantity, 0)} zł
                    </div>
                        <button className="CartCheckout">Przejdź do kasy</button>

                </div>
            </div>
        </div>
    );
};

export default Cart;
