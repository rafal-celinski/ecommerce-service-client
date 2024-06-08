import React from 'react';
import { Link } from 'react-router-dom';

import './style.css';

function Header() {
    return (
        <div className="Header">
            <Link to="/" className="HeaderTitle">Portal aukcyjny</Link>
            {/*<Link to="...">*/}
            {/*    <button>...</button>*/}
            {/*</Link>*/}
        </div>
    );
}

export default Header;