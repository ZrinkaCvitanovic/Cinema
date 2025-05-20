import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import Navbar from "./Navbar";
import { useNavigate } from "react-router-dom";
import { Button, Table } from "@mui/joy";
import "../App.css";

function UrediDvoranu() {
    const [details, setDetails] = useState([]);
    const params = useParams();
    const ime = params.ime;
    const navigate = useNavigate();
    const [kapacitet, setKapacitet] = useState(details.kapacitet);
    const [otvorena, setOtvorena] = useState(true);
    const [zaposl, setZaposl] = useState(details.zaposl);

    useEffect(() => {
        fetch(`http://localhost:8080/api/dvorana/${ime}`)
            .then((response) => response.json())
            .then((details) => {
                setDetails(details);
                console.log(details);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, [ime]);

    const returnToDvorane = async () => {
        navigate("/dvorane");
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const payload = {
            ime: ime,
            kapacitet: parseInt(kapacitet),
            otvorena: Boolean(otvorena),
            unioZaposlenik: zaposl,
        };
        try {
            const response = await fetch("http://localhost:8080/api/dvorana", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });
            if (!response.ok) {
                throw new Error("Greška pri slanju podataka - neočekivan odgovor poslužitelja");
            } else {
                fetch(`http://localhost:8080/api/dvorana/${ime}`)
                    .then((response) => response.json())
                    .then((data) => {
                        setDetails(data);
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
            <h2>Uredi informacije o dvorani</h2>
            <Table>
                <thead>
                    <tr>
                        <th>Ime</th>
                        <th>Kapacitet</th>
                        <th>Otvorena</th>
                        <th>Unio</th>
                    </tr>
                </thead>
                <tbody>
                    <tr key={ime}>
                        <td className="film-title">{details.ime}</td>
                        <td className="film-body">{details.kapacitet}</td>
                        <td>{details.otvorena ? "da" : "ne"}</td>
                        <td>{details.unioZaposlenik}</td>
                    </tr>
                </tbody>
            </Table>
            <div className="add-post-container">
                <form onSubmit={handleSubmit}>
                    <input type="text" size="30" value={ime} placeholder="dvoranaXX" required />
                    <input type="number" min="0" placeholder="Broj mjesta u dvorani" required onChange={(e) => setKapacitet(e.target.value)}></input>
                    <select onChange={(e) => setOtvorena(e.target.value === "otvorena" ? true : false)} required>
                        <option value="otvorena">otvorena</option>
                        <option value="zatvorena">zatvorena</option>
                    </select>
                    <input type="text" required placeholder="Korisničko ime zaposlenika" onChange={(e) => setZaposl(e.target.value)}></input>

                    <Button color="success" type="submit">
                        Uredi dvoranu
                    </Button>
                </form>
            </div>
            <Button onClick={returnToDvorane}>Povratak</Button>
        </div>
    );
}

export default UrediDvoranu;
