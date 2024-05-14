import React, { useState, useEffect } from 'react';

import './style.css';

function FilterForm({setFilterData}) {
    const [formData, setFormData] = useState({
        search: '',
        category: '0',
        subcategory: '0',
        minPrice: '0',
        maxPrice: '0',
        location: '',
    });

    useEffect(() => {setFilterData(formData);}, []);

    function handleChange(e) {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setFilterData(formData);
        console.log(formData);
    }



    const [categories, setCategories] = useState([]);
    function updateCategories() {
        fetch('http://localhost:8080/categories')
            .then((response) => response.json())
            .then(categories => {
                setCategories(categories);
            })
            .catch((error) => {
                console.error('Błąd podczas pobierania kategorii:', error);
            });
    }
    useEffect(updateCategories, []);


    const [subcategories, setSubcategories] = useState([]);
    function updateSubcategories() {
         fetch('http://localhost:8080/subcategories/' + formData.category)
              .then((response) => response.json())
              .then(subcategories => {
                  setSubcategories(subcategories);
              })
              .catch((error) => {
                  console.error('Błąd podczas pobierania podkategorii:', error);
              });
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