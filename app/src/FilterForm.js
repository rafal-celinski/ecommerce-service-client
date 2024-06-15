import React, { useState, useEffect } from 'react';

import './style.css';

function FilterForm({setFilterData}) {
    const [categories, setCategories] = useState([]);
    const [formData, setFormData] = useState({
        search: '',

        category: '',
        subcategory: '',
        minPrice: '',
        maxPrice: '',
        location: '',
    });



    function handleChange(e) {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setFilterData(formData);
        console.log(formData);
    }


    function updateCategories() {
        fetch(process.env.REACT_APP_API_URL + "/categories")
            .then((response) => response.json())
            .then(categories => {

                setCategories([{id: '', name: "Wszystkie"}, ...categories]);
            })
            .catch((error) => {
                console.error('Błąd podczas pobierania kategorii:', error);
                setCategories([{id: '', name: "Wszystkie"}]);
            });

        setFormData({ ...formData, category: '' });

    }
    useEffect(updateCategories, []);
    useEffect(() => {setFilterData(formData);}, []);


    const [subcategories, setSubcategories] = useState([]);
    function updateSubcategories() {

        if (formData.category !== '') {
            fetch(process.env.REACT_APP_API_URL + "/categories/" + formData.category + "/subcategories")
                .then((response) => response.json())
                .then(subcategories => {
                    setSubcategories([{id: '', name: "Wszystkie"}, ...subcategories]);
                })
                .catch((error) => {
                    console.error('Błąd podczas pobierania podkategorii:', error);
                    setSubcategories([{id: '', name: "Wszystkie"}]);
                });
        }
        else {
            setSubcategories([{id: '', name: "Wszystkie"}]);
        }

        setFormData({ ...formData, subcategory: '' });

    }
    useEffect(updateSubcategories, [formData.category]);

    return (
        <div className='FilterForm'>
            <form onSubmit={handleSubmit}>
                <input
                    name='search'
                    value={formData.search}
                    onChange={handleChange}
                    placeholder='Wyszukaj przedmiot'
                />
                Kategoria
                <select
                    name="category"
                    value={formData.category}
                    onChange={handleChange}
                >
                    {categories.map(category => (
                        <option key={category.id} value={category.id}>
                            {category.name}
                        </option>
                        )
                    )}
                </select>
                Podkategoria
                <select
                    name="subcategory"
                    value={formData.subcategory}
                    onChange={handleChange}
                >
                    {subcategories.map(subcategory => (
                            <option key={subcategory.id} value={subcategory.id}>
                                {subcategory.name}
                            </option>
                        )
                    )}
                </select>
                Cena od:
                <input
                    type="number"
                    name="minPrice"
                    value={formData.minPrice}
                    onChange={handleChange}
                />
                do:
                <input
                    type="number"
                    name="maxPrice"
                    value={formData.maxPrice}
                    onChange={handleChange}
                />
                Lokalizacja
                <input
                    type="text"
                    name="location"
                    value={formData.location}
                    onChange={handleChange}
                />
                <button type="submit">Szukaj</button>
            </form>
        </div>
    );
};

export default FilterForm;