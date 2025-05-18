import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import Navbar from "./Navbar";
import { useNavigate } from "react-router-dom";

function JednaProjekcija() {
    const [details, setDetails] = useState([]);
    const params = useParams();
    const id = params.id;
    const navigate = useNavigate();

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

    const updateDvorana = async () => {
        navigate(`/projekcije/edit/${id}`);
    };

    return (
        <div className="App">
            <Navbar />
            <h2>Detalji o projekciji</h2>
            <div>
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
                            <td className="film-body">{details.imeDvorane}</td>
                            <td>{details.idFilm}</td>
                            <td>{details.datum}</td>
                            <td>{details.vrijemePoc}</td>
                            <td>{details.trajanjeMin}</td>
                            <td>{details.slobodnaMjesta}</td>
                            <td>{details.unioProjekcija}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <button onClick={returnToDvorane}>Povratak</button>
            <button onClick={updateDvorana}>Uredi informacije o projekciji</button>
        </div>
    );
}

export default JednaProjekcija;
