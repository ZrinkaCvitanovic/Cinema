import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import Navbar from "./Navbar";
import { useNavigate } from "react-router-dom";

function UrediProjekciju() {
    const [details, setDetails] = useState([]);
    const params = useParams();
    const id = params.id;
    const navigate = useNavigate();
    const [imeDvorana, setImeDvorana] = useState("");
    const [idFilm, setIdFilm] = useState("");
    const [unioProjekcija, setUnioProjekcija] = useState("");
    const [trajanjeMin, setTrajanjeMin] = useState(0);
    const [datum, setDatum] = useState("");
    const [vrijemePoc, setVrijemePoc] = useState("");
    const [mjesta, setMjesta] = useState(0);

    useEffect(() => {
        fetch(`http://localhost:8080/api/projekcija/${id}`)
            .then((response) => response.json())
            .then((details) => {
                setDetails(details);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, [id]);

    const returnToDvorane = async () => {
        navigate("/");
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const payload = {
            id: id,
            imeDvorana: imeDvorana,
            idFilm: idFilm,
            datum: datum,
            trajanjeMin: trajanjeMin,
            vrijemePoc: vrijemePoc,
            slobodnaMjesta: parseInt(mjesta),
            unioProjekcija: unioProjekcija,
        };
        try {
            const response = await fetch("http://localhost:8080/api/projekcija", {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });
            if (!response.ok) {
                throw new Error("Greška pri slanju podataka - neočekivan odgovor poslužitelja");
            } else {
                navigate("/");
                fetch(`http://localhost:8080/api/projekcija/${id}`)
                    .then((response) => response.json())
                    .then((data) => {
                        setDetails(data);
                        console.log(details);
                    })
                    .catch((err) => {
                        console.log(err.message);
                    });
            }
        } catch (err) {
            throw new Error("Greška pri slanju podataka - podatci nisu u ispravnom formatu");
        }
    };

    return (
        <div className="App">
            <Navbar />
            <h2>Uredi informacije o projekciji</h2>
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
                    <tr key={id}>
                        <td className="film-title">{details.id}</td>
                        <td className="film-body">{details.imeDvorana}</td>
                        <td className="film-body">{details.idFilm}</td>
                        <td className="film-body">{details.datum}</td>
                        <td className="film-body">{details.vrijemePoc}</td>
                        <td>{details.trajanjeMin}</td>
                        <td>{details.slobodnaMjesta}</td>
                        <td>{details.unioProjekcija}</td>
                    </tr>
                </tbody>
            </table>
            <div className="add-post-container">
                <form onSubmit={handleSubmit}>
                    <input type="text" size="30" placeholder="dvoranaXX" required onChange={(e) => setImeDvorana(e.target.value)} />
                    <input type="number" placeholder="idFilm" required onChange={(e) => setIdFilm(e.target.value)}></input>
                    <input type="text" required placeholder="yyyy-mm-dd" onChange={(e) => setDatum(e.target.value)}></input>
                    <input type="text" required placeholder="hh:mm:ss" onChange={(e) => setVrijemePoc(e.target.value)}></input>
                    <input type="number" min="0" required placeholder="Trajanje filma" onChange={(e) => setTrajanjeMin(e.target.value)}></input>
                    <input type="number" min="0" required placeholder="Broj slobodnih mjesta" onChange={(e) => setMjesta(e.target.value)}></input>
                    <input type="text" required placeholder="Korisničko ime zaposlenika" onChange={(e) => setUnioProjekcija(e.target.value)}></input>
                    <button type="submit">Uredi projekciju</button>
                </form>
            </div>
            <button onClick={returnToDvorane}>Povratak</button>
        </div>
    );
}

export default UrediProjekciju;
