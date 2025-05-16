import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import Navbar from "./Navbar";

function Film() {
    const [filmovi, setFilmovi] = useState([]);
    const [naziv, setNaziv] = useState("");
    const [zaposl, setZaposl] = useState("");
    const [trajanje, setTrajanje] = useState(0);
    const [dob, setDob] = useState(0);
    const [cijena, setCijena] = useState(0.0);
    const navigate = useNavigate();

    useEffect(() => {
        fetch("http://localhost:8080/api/film/all")
            .then((response) => response.json())
            .then((data) => {
                setFilmovi(data);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);

    const fetchOneFilm = async (id) => {
        navigate(`/filmovi/${id}`);
    };

    const deleteFilm = async (id) => {
        await fetch(`http://localhost:8080/api/film/${id}`, { method: "DELETE" });
        setFilmovi(
            filmovi.filter((film) => {
                return film.id !== id;
            })
        );
        return;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            naziv: naziv,
            trajanjeMin: parseInt(trajanje),
            ulazEur: parseFloat(cijena),
            dobnaGranica: parseInt(dob),
            unioZaposlenik: zaposl,
        };

        try {
            const response = await fetch("http://localhost:8080/api/film", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });
            if (!response.ok) {
                throw new Error("Greška pri slanju podataka");
            } else {
                fetch("http://localhost:8080/api/film/all")
                    .then((response) => response.json())
                    .then((data) => {
                        setFilmovi(data);
                    })
                    .catch((err) => {
                        console.log(err.message);
                    });
            }
        } catch (err) {
            throw new Error("Greška pri slanju podataka22");
        }
    };

    return (
        <div className="App">
            <Navbar />
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Naziv</th>
                            <th>Detalji</th>
                            <th>Izmjene</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filmovi.map((film) => {
                            return (
                                <tr key={film.id}>
                                    <td className="film-title">{film.id}</td>
                                    <td className="film-title">{film.naziv}</td>
                                    <td>
                                        <button onClick={() => fetchOneFilm(film.id)}> Detalji o filmu </button>
                                    </td>
                                    <td className="button">
                                        <button className="delete-btn" onClick={() => deleteFilm(film.id)}>
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
            <div className="add-post-container">
                <form onSubmit={handleSubmit}>
                    <input type="text" required size="70" value={naziv} placeholder="Naziv filma" onChange={(e) => setNaziv(e.target.value)} />
                    <input type="number" required placeholder="Trajanje u minutama" onChange={(e) => setTrajanje(e.target.value)}></input>
                    <input type="number" required min="0" max="20" step="0.01" placeholder="xx.xx" onChange={(e) => setCijena(e.target.value)}></input>
                    <input type="number" required placeholder="Dobno ogrančenje" onChange={(e) => setDob(e.target.value)}></input>
                    <input type="text" required placeholder="Korisničko ime zaposlenika" onChange={(e) => setZaposl(e.target.value)}></input>
                    <button type="submit">Dodaj film</button>
                </form>
            </div>
        </div>
    );
}

export default Film;
