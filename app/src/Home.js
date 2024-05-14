import React, { useState, useEffect } from 'react';
import FilterForm from './FilterForm';
import Header from './Header';
import AuctionList from './AuctionList';


function Home() {
    const [filterData, setFilterData] = useState();

    return (
        <body>
            <Header />
            <FilterForm setFilterData={setFilterData}/>
            <AuctionList filterData={filterData}/>
        </body>
    );
};

export default Home;