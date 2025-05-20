import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";

import { Button, Input, Table, Select, Option } from "@mui/joy";

import Navbar from "./Navbar";
import "../App.css";

function UrediFilm() {
    const [details, setDetails] = useState([]);
    const params = useParams();
    const id = params.id;
    const navigate = useNavigate();
    const [naziv, setNaziv] = useState(details.naziv);
    const [trajanje, setTrajanje] = useState(details.trajanjeMin);
    const [dob, setDob] = useState(details.dobnaGranica);
    const [cijena, setCijena] = useState(details.ulazEur);
    const [ime, setIme] = useState("");
    const [prezime, setPrezime] = useState("");
    const [redatelji, setRedatelji] = useState([]);
    const [idRedatelj, setIdRedatelj] = useState("");

    useEffect(() => {
        fetch(`http://localhost:8080/api/film/${id}`)
            .then((response) => response.json())
            .then((details) => {
                setDetails(details);
                setIme(details.redatelj.name);
                setPrezime(details.redatelj.surname);
            })
            .catch((err) => {
                console.log(err.message);
            });
        fetch("http://localhost:8080/api/redatelj/all")
            .then((response) => response.json())
            .then((data) => {
                data.map((d) => {
                    redatelji.push(d.id);
                });
                console.log(redatelji);
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
            idRedatelj: parseInt(idRedatelj),
        };
        console.log(payload);

        try {
            const response = await fetch("http://localhost:8080/api/film", {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });
            if (!response.ok) {
                throw new Error("Greška pri slanju podataka - neočekivan odgovor poslužitelja");
            } else {
                fetch(`http://localhost:8080/api/film/${id}`)
                    .then((response) => response.json())
                    .then((data) => {
                        setDetails(data);
                        setIme(data.redatelj.name);
                        setPrezime(data.redatelj.surname);
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
            <h2>Uredi informacije o filmu</h2>
            <Table sx={{ textAlign: "left" }} variant="soft">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Naziv</th>
                        <th>Trajanje (min)</th>
                        <th>Cijena ulaznice (€)</th>
                        <th>Dobna granica</th>
                        <th>Redatelj</th>
                    </tr>
                </thead>
                <tbody>
                    <tr key={id}>
                        <td className="film-title">{details.id}</td>
                        <td className="film-title">{details.naziv}</td>
                        <td className="film-body">{details.trajanjeMin}</td>
                        <td>{details.ulazEur}</td>
                        <td>{details.dobnaGranica}</td>
                        <td>
                            {ime} {prezime}
                        </td>
                    </tr>
                </tbody>
            </Table>
            <div className="add-post-container">
                <form onSubmit={handleSubmit}>
                    <Input type="text" placeholder="naziv" onClick={(e) => setNaziv(e.target.value)}></Input>
                    <Input type="number" placeholder="trajanje u min" onChange={(e) => setTrajanje(e.target.value)}></Input>
                    <Input type="number" placeholder="cijena u eurima (xx.xx)" step="0.01" onChange={(e) => setCijena(e.target.value)}></Input>
                    <Input type="number" placeholder="dobna granica" onChange={(e) => setDob(e.target.value)}></Input>
                    <Select placeholder="ID redatelja" onChange={(e) => setIdRedatelj(e.target.value)}>
                        {redatelji.map((a) => {
                            return <Option value={a}>{a}</Option>;
                        })}
                    </Select>
                    <Button type="submit" color="success">
                        Uredi film
                    </Button>
                    <Button onClick={returnToFilmovi}>Povratak</Button>
                </form>
            </div>
        </div>
    );
}

export default UrediFilm;
