import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import Navbar from "./Navbar";
import { useNavigate } from "react-router-dom";

function JednaDvorana() {
    const [details, setDetails] = useState([]);
    const params = useParams();
    const ime = params.ime;
    const navigate = useNavigate();

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

    const updateDvorana = async () => {
        navigate(`/dvorane/edit/${ime}`);
    };

    return (
        <div className="App">
            <Navbar />
            <h2>Detalji o dvorani</h2>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Naziv</th>
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
                </table>
            </div>
            <button onClick={returnToDvorane}>Povratak</button>
            <button onClick={updateDvorana}>Uredi informacije o dvorani</button>
        </div>
    );
}

export default JednaDvorana;
