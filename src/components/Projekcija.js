import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Select, Option, Input, Button, Table, FormLabel } from "@mui/joy";

import Navbar from "./Navbar";
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
                <Select onChange={(e) => setCriteria(e.target.value)} placeholder="Odaberite kriterij iz padajućeg izbornika">
                    <Option value="id">ID projekcije (default)</Option>
                    <Option value="dvorana">Dvorana</Option>
                    <Option value="film">Film</Option>
                    <Option value="zaposlenik">zaposlenik</Option>
                </Select>
                <Input type="text" onChange={(e) => setValue(e.target.value)}></Input>
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
                                    <Button size="sm" color="success" onClick={() => urediOneProjekcija(p.id)}>
                                        {" "}
                                        Uredi projekciju{" "}
                                    </Button>
                                </td>
                                <td className="Button">
                                    <Button size="sm" color="danger" onClick={() => deleteProjekcija(p.id)}>
                                        Delete
                                    </Button>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </Table>
            <div style={{ marginTop: 40 + "px" }}>
                <form onSubmit={handleSubmit}>
                    <Input type="text" required placeholder="dvoranaXX" onChange={(e) => setImeDvorana(e.target.value)} />
                    <Input type="number" required placeholder="ID filma" onChange={(e) => setIdFilm(e.target.value)}></Input>
                    <Input type="text" required placeholder="yyyy-mm-dd" onChange={(e) => setDatum(e.target.value)}></Input>
                    <Input type="text" required placeholder="hh:mm" onChange={(e) => setVrijemePoc(e.target.value)}></Input>
                    <Input type="number" required placeholder="Trajanje" onChange={(e) => setTrajanjeMin(e.target.value)}></Input>
                    <Input type="number" required min="0" placeholder="Slobodna mjesta" onChange={(e) => setMjesta(e.target.value)}></Input>
                    <Input type="text" required placeholder="Korisničko ime zaposlenika" onChange={(e) => setUnioProjekcija(e.target.value)}></Input>
                    <Button size="sm" color="warning" type="submit" style={{ marginTop: 20 + "px" }}>
                        Dodaj projekciju
                    </Button>
                </form>
            </div>
        </div>
    );
}

export default Projekcija;
