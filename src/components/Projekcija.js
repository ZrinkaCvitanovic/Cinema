import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Button, Table } from "@mui/joy";

import Navbar from "./Navbar";
import Redatelj from "./Redatelj";
import "../App.css";

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

    const handleSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            imeDvorana: imeDvorana,
            trajanjeMin: parseInt(trajanjeMin),
            idFilm: parseInt(idFilm),
            slobodnaMjesta: parseInt(mjesta),
            datum: datum,
            vrijemePoc: vrijemePoc,
        };

        try {
            const response = await fetch("http://localhost:8080/api/projekcija", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });
            if (!response.ok) {
                throw new Error("Greška pri slanju podataka - neočekivan odgovor poslužitelja");
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
            throw new Error("Greška pri slanju podataka - podatci nisu u ispravnom formatu");
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
                <select onChange={(e) => setCriteria(e.target.value)} placeholder="Odaberite kriterij iz padajućeg izbornika">
                    <option value="id">ID projekcije (default)</option>
                    <option value="dvorana">Dvorana</option>
                    <option value="film">Film</option>
                </select>
                <input type="text" onChange={(e) => setValue(e.target.value)}></input>
                <Button variant="soft" type="submit">
                    Pretraži
                </Button>
            </form>
            <Table variant="soft">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Dvorana</th>
                        <th>Film (id)</th>
                        <th>Datum</th>
                        <th>Vrijeme početka</th>
                        <th>Trajanje (min)</th>
                        <th>Broj slobodnih mjesta</th>
                        <th colSpan={2}></th>
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
                                <td>
                                    <Button size="sm" color="success" onClick={() => urediOneProjekcija(p.id)}>
                                        {" "}
                                        Uredi projekciju{" "}
                                    </Button>
                                </td>
                                <td className="Button">
                                    <Button size="sm" color="danger" onClick={() => deleteProjekcija(p.id)}>
                                        Obriši
                                    </Button>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </Table>
            <div style={{ marginTop: 40 + "px" }}>
                <form onSubmit={handleSubmit}>
                    <input type="text" required placeholder="dvoranaXX" onChange={(e) => setImeDvorana(e.target.value)} />
                    <input type="number" required placeholder="ID filma" onChange={(e) => setIdFilm(e.target.value)}></input>
                    <input type="text" required placeholder="yyyy-mm-dd" onChange={(e) => setDatum(e.target.value)}></input>
                    <input type="text" required placeholder="hh:mm" onChange={(e) => setVrijemePoc(e.target.value)}></input>
                    <input type="number" required placeholder="Trajanje" onChange={(e) => setTrajanjeMin(e.target.value)}></input>
                    <input type="number" required min="0" placeholder="Slobodna mjesta" onChange={(e) => setMjesta(e.target.value)}></input>
                    <Button size="sm" color="warning" type="submit" style={{ marginTop: 20 + "px" }}>
                        Dodaj projekciju
                    </Button>
                </form>
            </div>
        </div>
    );
}

export default Projekcija;
