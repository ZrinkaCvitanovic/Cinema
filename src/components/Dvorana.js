import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Table, Input, Select, Option, Button } from "@mui/joy";

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
                    throw new Error("Greška - neočekivan odgovor poslužitelja");
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
                throw new Error("Greška pri slanju podataka - podatci nisu u ispravnom formatu");
            }
        } else {
            throw new Error("Ime dvorane nije u ispravnom formatu!");
        }
    };

    return (
        <div className="App">
            <Navbar />
            <div>
                <Table sx={{ textAlign: "left" }} variant="soft">
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
                                        <Button variant="soft" onClick={() => fetchOneDvorana(d.ime)}>
                                            {" "}
                                            Detalji o dvorani{" "}
                                        </Button>
                                    </td>
                                    <td className="Button">
                                        <Button color="danger" onClick={() => deleteDvorana(d.ime)}>
                                            Obriši
                                        </Button>
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </Table>
            </div>
            <div className="add-post-container">
                <form onSubmit={handleSubmit}>
                    <Input type="text" value={ime} placeholder="format: dvoranaXX" required onChange={(e) => setIme(e.target.value)} />
                    <Input type="number" min="0" placeholder="Broj mjesta u dvorani" required onChange={(e) => setKapacitet(e.target.value)}></Input>
                    <Select placeholder="otvorena/zatvorena" onChange={(e) => setOtvorena(e.target.value === "otvorena" ? true : false)} required>
                        <Option>otvorena</Option>
                        <Option>zatvorena</Option>
                    </Select>
                    <Input required type="text" placeholder="Korisničko ime zaposlenika" onChange={(e) => setZaposl(e.target.value)}></Input>

                    <Button color="warning" type="submit" style={{ marginTop: 20 + "px" }}>
                        Dodaj dvoranu
                    </Button>
                </form>
            </div>
        </div>
    );
}

export default Dvorana;
