import { useState, useEffect } from "react";

import Navbar from "./Navbar";

function Dvorana() {
    const [dvorana, setDvorana] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/dvorana/all")
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                setDvorana(data);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);

    return (
        <div className="App">
            <Navbar />
            <p>Dvorana page</p>
            <div>
                <table>
                    <th>naziv</th>
                    <th>kapacitet</th>
                    <th>Otvorena</th>
                    {dvorana.map((post) => {
                        return (
                            <tr key={post.id}>
                                <td className="post-title">{post.ime}</td>
                                <td className="post-title">{post.kapacitet}</td>
                                <td className="post-body">{post.otvorena ? "da" : "ne"}</td>
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

export default Dvorana;
