import { useState, useEffect } from "react";

import Navbar from "./Navbar";

function Film() {
    const [filmovi, setFilmovi] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/film/all")
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                setFilmovi(data);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);

    return (
        <div className="App">
            <Navbar />
            <p>Filmovi page</p>
            <div>
                <table>
                    <th>ID</th>
                    <th>naziv</th>
                    <th>Trajanje (min)</th>
                    <th>Cijena ulaznice (€)</th>
                    <th>Unio</th>
                    <th>Izmjene</th>
                    {filmovi.map((post) => {
                        return (
                            <tr key={post.id}>
                                <td className="post-title">{post.id}</td>
                                <td className="post-title">{post.naziv}</td>
                                <td className="post-body">{post.trajanjeMin}</td>
                                <td>{post.ulazEur}</td>
                                <td>{post.unioZaposlenik}</td>
                                <td>
                                    <button className="delete-btn">Delete</button>
                                </td>
                            </tr>
                        );
                    })}
                </table>
            </div>
        </div>
    );
}

export default Film;
