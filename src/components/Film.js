import { useState, useEffect } from "react";

import Navbar from "./Navbar";

function Film() {
    const [filmovi, setFilmovi] = useState([]);

    useEffect(() => {
        fetch("http://localhost:5432/kino/film")
            .then((resp) => resp.json())
            .then((data) => setFilmovi(data)); // set data to state
    }, []);

    return (
        <div className="App">
            <Navbar />
            <p>Filmovi page</p>
        </div>
    );
}

export default Film;
