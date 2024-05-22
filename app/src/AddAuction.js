import React, {useEffect, useState} from 'react';

import Header from './Header';

import './AddAuction.css';

function AddAuction() {
    const [images, setImages] = useState([]);

    const [formData, setFormData] = useState({
        title: '',
        price: 0,
        location: '',
        category: '',
        subcategory: '',
        description: ''
    });

    async function postAuction() {
        fetch('http://localhost:8080/products/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(auction => {
                for (let i = 0; i < images.length; i++) {
                    var name = "img" + i + "_" + auction.id;
                    postImage(auction.id, name, images[i]);
                }
            })

            .catch((error) => {
                console.error('Błąd podczas dodawania produktu: ', error);
        });


    }

    function postImage(auctionID, name, imageFile) {
        const data = new FormData();
        data.append('productId', auctionID);
        data.append('name', name);
        data.append('image', imageFile);

        fetch('http://localhost:8080/images/add', {
            method: 'POST',
            body: data
        }).catch((error) => {
            console.error('Błąd podczas dodawania obrazu: ', error);
        });
    }


    function handleSubmit(event) {
        event.preventDefault();
        console.log(formData);
        postAuction();



    };

    function handleChange(e) {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    }

    function handleImageChange(event) {
        setImages([...event.target.files]);




    };

    const [categories, setCategories] = useState([]);
    function updateCategories() {
        fetch('http://localhost:8080/categories')
            .then((response) => response.json())
            .then(categories => {
                setCategories([{id: '', name: "Wybierz..."}, ...categories]);
            })
            .catch((error) => {
                console.error('Błąd podczas pobierania kategorii:', error);
                setCategories([{id: '', name: "Wybierz..."}]);
            });

        setFormData({ ...formData, category: '' });
    }
    useEffect(updateCategories, []);


    const [subcategories, setSubcategories] = useState([]);
    function updateSubcategories() {
        if (formData.category !== '') {
            fetch('http://localhost:8080/subcategories/category?categoryId=' + formData.category)
                .then((response) => response.json())
                .then(subcategories => {
                    setSubcategories([{id: '', name: "Wybierz..."}, ...subcategories]);
                })
                .catch((error) => {
                    console.error('Błąd podczas pobierania podkategorii:', error);
                    setSubcategories([{id: '', name: "Wybierz..."}]);
                });
        }
        else {
            setSubcategories([{id: '', name: "Wybierz..."}]);
        }

        setFormData({ ...formData, subcategory: '' });

    }
    useEffect(updateSubcategories, [formData.category]);

    return (
        <div>

                <Header />

            <div className="AddAuctionForm">
                <div className="FormTitle">Dodaj aukcję </div>
                <form onSubmit={handleSubmit}>
                    <label>
                        Zdjęcie:
                        <input
                            type="file"
                            name="image"
                            onChange={handleImageChange}
                            accept="image/jpeg, image/png"
                            multiple />
                    </label>

                    <label>
                        Tytuł:
                        <input
                            type="text"
                            name="title"
                            value={formData.title}
                            onChange={handleChange} />
                    </label>
                    <label>
                        Cena:
                        <input
                            type="number"
                            name="price"
                            value={formData.price}
                            onChange={handleChange} />
                    </label>
                    <label>
                        Lokalizacja:
                        <input
                            type="text"
                            name="location"
                            value={formData.location}
                            onChange={handleChange} />
                    </label>
                    <label>
                        Kategoria:
                        <select
                            value={formData.category}
                            name="category"
                            onChange={handleChange} >
                                {categories.map(category => (
                                    <option key={category.id} value={category.id}>
                                        {category.name}
                                    </option>
                                )
                            )}
                         </select>
                    </label>
                    <label>
                        Podkategoria:
                        <select
                            value={formData.subcategory}
                            name="subcategory"
                            onChange={handleChange} >
                                {subcategories.map(subcategory => (
                                    <option key={subcategory.id} value={subcategory.id}>
                                        {subcategory.name}
                                    </option>
                                )
                            )}
                        </select>
                    </label>
                    <label>
                        Opis:
                        <textarea
                            value={formData.description}
                            name="description"
                            onChange={handleChange} />
                    </label>
                    <button type="submit">Zatwierdź</button>
                </form>
            </div>
        </div>
    );
}

export default AddAuction;