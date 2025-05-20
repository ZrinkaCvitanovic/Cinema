import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import Navbar from "./Navbar";
import { useNavigate } from "react-router-dom";
import { Button, Box, Table } from "@mui/joy";

function JedanFilm() {
    const [details, setDetails] = useState([]);
    const params = useParams();
    const id = params.id;
    const [ime, setIme] = useState("");
    const [prezime, setPrezime] = useState("");
    const navigate = useNavigate();

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
    }, [id]);

    const returnToFilmovi = async () => {
        navigate("/filmovi");
    };

    const updateFilm = async () => {
        navigate(`/filmovi/edit/${id}`);
    };

    return (
        <div className="App">
            <Navbar />
            <h2>Detalji o filmu</h2>
            <Table sx={{ textAlign: "left" }} variant="soft">
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
                        <td>
                            {ime} {prezime}{" "}
                        </td>
                    </tr>
                </tbody>
            </Table>
            <Box>
                <Button onClick={returnToFilmovi}>Povratak</Button>
                <Button color="success" onClick={updateFilm}>
                    Uredi informacije o filmu
                </Button>
            </Box>
        </div>
    );
}

export default JedanFilm;
