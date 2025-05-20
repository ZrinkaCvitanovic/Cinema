import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Table, Button } from "@mui/joy";

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
                throw new Error("Greška pri slanju podataka - neočekivan odgovor poslužitelja");
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
            throw new Error("Greška pri slanju podataka  - podatci nisu u ispravnom formatu");
        }
    };

    return (
        <div className="App">
            <Navbar />
            <Table sx={{ textAlign: "left" }} variant="soft">
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
                                    <Button variant="soft" onClick={() => fetchOneFilm(film.id)}>
                                        {" "}
                                        Detalji o filmu{" "}
                                    </Button>
                                </td>
                                <td className="Button">
                                    <Button color="danger" onClick={() => deleteFilm(film.id)}>
                                        Delete
                                    </Button>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </Table>
            <div style={{ marginTop: 70 + "px" }}>
                <form onSubmit={handleSubmit}>
                    <input type="text" required value={naziv} placeholder="Naziv filma" onChange={(e) => setNaziv(e.target.value)} />
                    <input type="number" required placeholder="Trajanje u minutama" onChange={(e) => setTrajanje(e.target.value)}></input>
                    <input type="number" required min="0" max="20" step="0.01" placeholder="xx.xx" onChange={(e) => setCijena(e.target.value)}></input>
                    <input type="number" required placeholder="Dobno ogrančenje" onChange={(e) => setDob(e.target.value)}></input>
                    <input type="text" required placeholder="Korisničko ime zaposlenika" onChange={(e) => setZaposl(e.target.value)}></input>
                    <Button color="warning" type="submit" style={{ marginTop: 20 + "px" }}>
                        Dodaj film
                    </Button>
                </form>
            </div>
        </div>
    );
}

export default Film;
