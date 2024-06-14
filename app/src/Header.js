import React from 'react';
import { Link } from 'react-router-dom';

import './style.css';

function Header() {
    return (
        <div className="Header">
            <Link to="/" className="HeaderTitle">Portal aukcyjny</Link>
            <Link to="/cart">
                <button>Pokaż koszyk</button>
            </Link>
            <Link to="/profile">
                <button>Historia zakupów</button>
            </Link>
        </div>
    );
}

export default Header;