import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import Navbar from "./Navbar";
import { useNavigate } from "react-router-dom";

function UrediFilm() {
    const [details, setDetails] = useState([]);
    const params = useParams();
    const id = params.id;
    const navigate = useNavigate();
    const [naziv, setNaziv] = useState(details.naziv);
    const [zaposl, setZaposl] = useState(details.unioZaposlenik);
    const [trajanje, setTrajanje] = useState(details.trajanjeMin);
    const [dob, setDob] = useState(details.dobnaGranica);
    const [cijena, setCijena] = useState(details.ulazEur);

    useEffect(() => {
        fetch(`http://localhost:8080/api/film/${id}`)
            .then((response) => response.json())
            .then((details) => {
                setDetails(details);
                console.log(details);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, [id]);

    const returnToFilmovi = async () => {
        navigate("/");
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const payload = {
            id: id,
            naziv: naziv,
            trajanjeMin: parseInt(trajanje),
            ulazEur: parseFloat(cijena),
            dobnaGranica: parseInt(dob),
            unioZaposlenik: zaposl,
        };
        console.log(payload);

        try {
            const response = await fetch("http://localhost:8080/api/film", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });
            if (!response.ok) {
                throw new Error("Greška pri slanju podataka");
            } else {
                fetch(`http://localhost:8080/api/film/${id}`)
                    .then((response) => response.json())
                    .then((data) => {
                        setDetails(data);
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
            <h2>Uredi informacije o filmu</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Naziv</th>
                        <th>Trajanje (min)</th>
                        <th>Cijena ulaznice (€)</th>
                        <th>Dobna granica</th>
                        <th>Unio</th>
                    </tr>
                </thead>
                <tbody>
                    <tr key={id}>
                        <td className="film-title">{details.id}</td>
                        <td className="film-title">{details.naziv}</td>
                        <td className="film-body">{details.trajanjeMin}</td>
                        <td>{details.ulazEur}</td>
                        <td>{details.dobnaGranica}</td>
                        <td>{details.unioZaposlenik}</td>
                    </tr>
                </tbody>
            </table>
            <div className="add-post-container">
                <form onSubmit={handleSubmit}>
                    <input type="text" size="70" placeholder="naziv" onClick={(e) => setNaziv(e.target.value)}></input>
                    <input type="number" placeholder="trajanje u min" onChange={(e) => setTrajanje(e.target.value)}></input>
                    <input type="number" placeholder="cijena" min="0" max="20" step="0.01" onChange={(e) => setCijena(e.target.value)}></input>
                    <input type="number" placeholder="dobna granica" onChange={(e) => setDob(e.target.value)}></input>
                    <input type="text" placeholder="Korisničko ime zaposlenika" onChange={(e) => setZaposl(e.target.value)}></input>
                    <button type="submit">Uredi film</button>
                </form>
            </div>
            <button onClick={returnToFilmovi}>Povratak</button>
        </div>
    );
}

export default UrediFilm;
