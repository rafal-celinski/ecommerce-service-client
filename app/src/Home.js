import React, { useState, useEffect } from 'react';
import FilterForm from './FilterForm';
import Header from './Header';
import AuctionList from './AuctionList';


function Home() {
    const [filterData, setFilterData] = useState();

    return (
        <div>
            <Header />
            <FilterForm setFilterData={setFilterData}/>
            <AuctionList filterData={filterData}/>
        </div>
    );
};

export default Home;