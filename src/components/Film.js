import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import Navbar from "./Navbar";
import JedanFilm from "./JedanFilm";

function Film() {
    const [filmovi, setFilmovi] = useState([]);
    const [naziv, setNaziv] = useState("");
    const [zaposl, setZaposl] = useState("");
    const [trajanje, setTrajanje] = useState(0);
    const [dob, setDob] = useState(0);
    const [cijena, setCijena] = useState(0.0);
    const [details, setDetails] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        fetch("http://localhost:8080/api/film/all")
            .then((response) => response.json())
            .then((data) => {
                setFilmovi(data);
                //console.log(data);
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

    const addFilmovi = async () => {
        let response = await fetch(`http://localhost:8080/api/film/`, {
            mode: "no-cors",
            method: "POST",
            body: JSON.stringify({
                naziv: naziv,
                trajanjeMin: trajanje,
                ulazEur: cijena,
                dobnaGranica: dob,
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
        });
        await response.json();
        let data = response.body;
        setFilmovi((filmovi) => [data, ...filmovi]);
        setNaziv("");
        setZaposl("");
        setCijena(0.0);
        setDob(0);
        setTrajanje(0);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        addFilmovi();
    };

    return (
        <div className="App">
            <Navbar />
            <p>Filmovi page</p>
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
                    <input type="text" size="70" value={naziv} placeholder="Naziv filma" onChange={(e) => setNaziv(e.target.value)} />
                    <input type="number" value={trajanje} name="" placeholder="Trajanje u minutama" onChange={(e) => setTrajanje(e.target.value)}></input>
                    <input type="number" value={cijena} min="0" max="20" step="0.01" placeholder="xx.xx" onChange={(e) => setCijena(e.target.value)}></input>
                    <input type="number" value={dob} name="" onChange={(e) => setDob(e.target.value)}></input>
                    <input type="text" value={zaposl} name="" placeholder="Korisničko ime zaposlenika" onChange={(e) => setZaposl(e.target.value)}></input>
                    <button type="submit">Dodaj film</button>
                </form>
            </div>
        </div>
    );
}

export default Film;
