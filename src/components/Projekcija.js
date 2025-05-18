import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import Navbar from "./Navbar";

function Projekcija() {
    const [projekcije, setProjekcije] = useState([]);
    const [imeDvorana, setImeDvorana] = useState("");
    const [idFilm, setIdFilm] = useState("");
    const [unioProjekcija, setUnioProjekcija] = useState("");
    const [trajanjeMin, setTrajanjeMin] = useState(0);
    const [datum, setDatum] = useState("");
    const [vrijemePoc, setVrijemePoc] = useState("");
    const [mjesta, setMjesta] = useState(0);
    const [criteria, setCriteria] = useState("id");
    const [value, setValue] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        fetch("http://localhost:8080/api/projekcija/all")
            .then((response) => response.json())
            .then((data) => {
                setProjekcije(data);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);

    const fetchOneProjekcija = async (id) => {
        navigate(`/projekcije/${id}`);
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

    const handleSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            imeDvorana: imeDvorana,
            trajanjeMin: parseInt(trajanjeMin),
            idFilm: parseInt(idFilm),
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
                fetch("http://localhost:8080/api/projekcija/all")
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

    const handleSearch = async (e) => {
        e.preventDefault();

        if (value === "") {
            fetch("http://localhost:8080/api/projekcija/all")
                .then((response) => response.json())
                .then((data) => {
                    setProjekcije(data);
                })
                .catch((err) => {
                    console.log(err.message);
                });
        } else {
            fetch(`http://localhost:8080/api/projekcija/${criteria}/${value}`)
                .then((response) => response.json())
                .then((data) => {
                    setProjekcije(data);
                    setValue("");
                })
                .catch((err) => {
                    console.log(err.message);
                });
        }
    };

    return (
        <div className="App">
            <Navbar />
            <form onSubmit={handleSearch}>
                <label>Odaberite kriterij za pretraživanje projekcija</label>
                <select onChange={(e) => setCriteria(e.target.value)}>
                    <option value="id">ID projekcije (default)</option>
                    <option value="dvorana">Dvorana</option>
                    <option value="film">Film</option>
                    <option value="zaposlenik">zaposlenik</option>
                </select>
                <input type="text" onChange={(e) => setValue(e.target.value)}></input>
                <button type="submit">Pretraži</button>
            </form>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Dvorana</th>
                            <th>Film</th>
                            <th>Detalji</th>
                        </tr>
                    </thead>
                    <tbody>
                        {projekcije.map((p) => {
                            return (
                                <tr key={p.id}>
                                    <td className="film-title">{p.id}</td>
                                    <td className="film-title">{p.imeDvorana}</td>
                                    <td>{p.idFilm}</td>
                                    <td>
                                        <button onClick={() => fetchOneProjekcija(p.id)}> Detalji o projekciji </button>
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
            </div>
            <div className="add-post-container">
                <form onSubmit={handleSubmit}>
                    <input type="text" required size="30" placeholder="dvoranaXX" onChange={(e) => setImeDvorana(e.target.value)} />
                    <input type="number" required placeholder="ID filma" onChange={(e) => setIdFilm(e.target.value)}></input>
                    <input type="text" required placeholder="yyyy-mm-dd" onChange={(e) => setDatum(e.target.value)}></input>
                    <input type="text" required placeholder="hh:mm:ss" onChange={(e) => setVrijemePoc(e.target.value)}></input>
                    <input type="number" required placeholder="Trajanje" onChange={(e) => setTrajanjeMin(e.target.value)}></input>
                    <input type="number" required min="0" placeholder="Slobodna mjesta" onChange={(e) => setMjesta(e.target.value)}></input>
                    <input type="text" required placeholder="Korisničko ime zaposlenika" onChange={(e) => setUnioProjekcija(e.target.value)}></input>
                    <button type="submit">Dodaj projekciju</button>
                </form>
            </div>
        </div>
    );
}

export default Projekcija;
