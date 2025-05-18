import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import Navbar from "./Navbar";

function Home() {
    const [projekcije, setProjekcije] = useState([]);
    const [imeDvorana, setImeDvorana] = useState("dvorana01");
    const [idFilm, setIdFilm] = useState("");
    const [unioProjekcija, setUnioProjekcija] = useState("admin1");
    const [trajanjeMin, setTrajanjeMin] = useState(0);
    const [datum, setDatum] = useState("");
    const [vrijemePoc, setVrijemePoc] = useState("");
    const [mjesta, setMjesta] = useState(0);
    const [filmovi, setFilmovi] = useState([]);
    const [currentFilm, setCurrentFilm] = useState(0);
    const [filmData, setFilmData] = useState([]);
    const [numFilms, setNumFilms] = useState(0);
    const [ids, setids] = useState([]);
    const [dvorane, setDvorane] = useState([]);
    const [admini, setAdmini] = useState([]);

    const navigate = useNavigate();

    const allIds = [];

    useEffect(() => {
        fetch("http://localhost:8080/api/dvorana/all")
            .then((response) => response.json())
            .then((data) => {
                data.map((d) => {
                    dvorane.push(d.ime);
                });
                console.log(dvorane);
            });
        fetch("http://localhost:8080/api/film/all")
            .then((response) => response.json())
            .then((data) => {
                data.map((d) => {
                    allIds.push(d.id);
                });
                setids(allIds);
                //console.log("number of films: ", allIds.length); //3
                //setNumFilms(allIds.length);
                //console.log("numFilms: ", numFilms); //0
                fetchProjekcijeByFilm(allIds[currentFilm]);
            })
            .catch((err) => {
                console.log(err);
            });
        fetch("http://localhost:8080/api/admin/all")
            .then((response) => response.json())
            .then((data) => {
                data.map((d) => {
                    admini.push(d.korisnickoIme);
                });
                console.log(admini);
                console.log(admini);
            });
    }, [currentFilm]);

    const fetchProjekcijeByFilm = async (id) => {
        await fetch(`http://localhost:8080/api/film/${id}`)
            .then((response) => response.json())
            .then((data) => {
                setFilmData(data);
            })
            .catch((err) => {
                console.log(err);
            });
        await fetch(`http://localhost:8080/api/projekcija/film/${id}`)
            .then((response) => response.json())
            .then((data) => {
                setProjekcije(data);
            })
            .catch((err) => {
                console.log(err);
            });
    };

    const urediOneProjekcija = async (id) => {
        navigate(`/projekcije/edit/${id}`);
    };

    const deleteProjekcija = async (id) => {
        await fetch(`http://localhost:8080/api/projekcija/${id}`, { method: "DELETE" });
        setProjekcije(
            projekcije.filter((p) => {
                return p.id !== id;
            })
        );
        return;
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

    const updateFilm = async () => {
        navigate(`/filmovi/edit/${filmData.id}`);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            imeDvorana: imeDvorana,
            trajanjeMin: parseInt(trajanjeMin),
            idFilm: parseInt(filmData.id),
            slobodnaMjesta: parseInt(mjesta),
            datum: datum,
            vrijemePoc: vrijemePoc,
            unioProjekcija: unioProjekcija,
        };

        try {
            const response = await fetch("http://localhost:8080/api/projekcija", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });
            if (!response.ok) {
                throw new Error("Greška pri slanju podataka");
            } else {
                fetch(`http://localhost:8080/api/projekcija/film/${filmData.id}`)
                    .then((response) => response.json())
                    .then((data) => {
                        setProjekcije(data);
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
            <h2>Naziv filma</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Naziv</th>
                        <th>Trajanje (min)</th>
                        <th>Dobna granica</th>
                        <th>Cijena ulaznice(€)</th>
                    </tr>
                </thead>
                <tbody>
                    <tr key={filmData.id}>
                        <td className="film-title">{filmData.id}</td>
                        <td className="film-title">{filmData.naziv}</td>
                        <td>{filmData.trajanjeMin}</td>
                        <td>{filmData.dobnaGranica}</td>
                        <td>{filmData.ulazEur}</td>
                        <td>
                            <button onClick={() => updateFilm()}> Uredi film </button>
                        </td>
                        <td className="button">
                            <button className="delete-btn" onClick={() => deleteFilm(filmData.id)}>
                                Delete
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
            <h2>Projekcije filma</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Dvorana</th>
                        <th>Film (id)</th>
                        <th>Datum</th>
                        <th>Vrijeme početka</th>
                        <th>Trajanje (u minutama)</th>
                        <th>Broj slobodnih mjesta</th>
                        <th>Unio</th>
                    </tr>
                </thead>
                <tbody>
                    {projekcije.map((p) => {
                        return (
                            <tr key={p.id}>
                                <td className="film-title">{p.id}</td>
                                <td className="film-title">{p.imeDvorana}</td>
                                <td>{p.idFilm}</td>
                                <td>{p.datum}</td>
                                <td>{p.vrijemePoc}</td>
                                <td>{p.trajanjeMin}</td>
                                <td>{p.slobodnaMjesta == null ? 0 : p.slobodnaMjesta}</td>
                                <td>{p.unioProjekcija}</td>
                                <td>
                                    <button onClick={() => urediOneProjekcija(p.id)}> Uredi projekciju </button>
                                </td>
                                <td className="button">
                                    <button className="delete-btn" onClick={() => deleteProjekcija(p.id)}>
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
            <div className="add-post-container">
                <form onSubmit={handleSubmit}>
                    <select onChange={(e) => setImeDvorana(e.target.value)}>
                        {dvorane.map((d) => {
                            return <option value={d}>{d}</option>;
                        })}
                    </select>
                    <input type="number" value={filmData.id} readOnly></input>
                    <input type="text" required placeholder="yyyy-mm-dd" onChange={(e) => setDatum(e.target.value)}></input>
                    <input type="text" required placeholder="hh:mm:ss" onChange={(e) => setVrijemePoc(e.target.value)}></input>
                    <input type="number" required placeholder="Trajanje" onChange={(e) => setTrajanjeMin(e.target.value)}></input>
                    <input type="number" required min="0" placeholder="Slobodna mjesta" onChange={(e) => setMjesta(e.target.value)}></input>
                    <select onChange={(e) => setUnioProjekcija(e.target.value)}>
                        {admini.map((a) => {
                            return <option value={a}>{a}</option>;
                        })}
                    </select>
                    <button type="submit">Unesi projekciju</button>
                </form>
            </div>
            <button
                onClick={() => {
                    const previous = ids.indexOf(filmData.id) == 0 ? ids.length - 1 : ids.indexOf(filmData.id) - 1;
                    fetchProjekcijeByFilm(ids[previous]);
                }}
            >
                Prethodni
            </button>
            <button
                onClick={() => {
                    const next = (ids.indexOf(filmData.id) + 1) % ids.length;
                    fetchProjekcijeByFilm(ids[next]);
                }}
            >
                Sljedeći
            </button>
        </div>
    );
}

export default Home;
