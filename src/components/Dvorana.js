import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import Navbar from "./Navbar";

function Dvorana() {
    const [dvorane, setDvorane] = useState([]);
    const [ime, setIme] = useState("");
    const [kapacitet, setKapacitet] = useState(0);
    const [otvorena, setOtvorena] = useState(false);
    const [zaposl, setZaposl] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        fetch("http://localhost:8080/api/dvorana/all")
            .then((response) => response.json())
            .then((data) => {
                setDvorane(data);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);

    const fetchOneDvorana = async (ime) => {
        navigate(`/dvorane/${ime}`);
    };

    const deleteDvorana = async (ime) => {
        await fetch(`http://localhost:8080/api/dvorana/${ime}`, { method: "DELETE" });
        setDvorane(
            dvorane.filter((dvorana) => {
                return dvorana.ime !== ime;
            })
        );
        return;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (ime.startsWith("dvorana")) {
            const payload = {
                ime: ime,
                kapacitet: parseInt(kapacitet),
                otvorena: otvorena,
                unioZaposlenik: zaposl,
            };

            try {
                const response = await fetch("http://localhost:8080/api/dvorana", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(payload),
                });
                if (!response.ok) {
                    throw new Error("Greška - response not ok");
                } else {
                    fetch("http://localhost:8080/api/dvorana/all")
                        .then((response) => response.json())
                        .then((data) => {
                            setDvorane(data);
                        })
                        .catch((err) => {
                            console.log(err.message);
                        });
                }
            } catch (err) {
                throw new Error("Greška pri slanju podataka - nije se dogodio fetch");
            }
        } else {
            throw new Error("Ime dvorane nije u ispravnom formatu!");
        }
    };

    return (
        <div className="App">
            <Navbar />
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Ime</th>
                            <th>Detalji</th>
                            <th>Izmjene</th>
                        </tr>
                    </thead>
                    <tbody>
                        {dvorane.map((d) => {
                            return (
                                <tr key={d.ime}>
                                    <td className="film-title">{d.ime}</td>
                                    <td>
                                        <button onClick={() => fetchOneDvorana(d.ime)}> Detalji o dvorani </button>
                                    </td>
                                    <td className="button">
                                        <button className="delete-btn" onClick={() => deleteDvorana(d.ime)}>
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
                    <input type="text" size="30" value={ime} placeholder="format: dvoranaXX" required onChange={(e) => setIme(e.target.value)} />
                    <input type="number" min="0" placeholder="Broj mjesta u dvorani" required onChange={(e) => setKapacitet(e.target.value)}></input>
                    <select onChange={(e) => setOtvorena(e.target.value === "otvorena" ? true : false)} required>
                        <option>otvorena</option>
                        <option>zatvorena</option>
                    </select>
                    <input required type="text" placeholder="Korisničko ime zaposlenika" onChange={(e) => setZaposl(e.target.value)}></input>

                    <button type="submit">Dodaj dvoranu</button>
                </form>
            </div>
        </div>
    );
}

export default Dvorana;
