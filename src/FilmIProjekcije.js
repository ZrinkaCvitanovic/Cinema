import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import Navbar from "./components/Navbar";

function FilmIProjekcije() {
    const [film, setFilm] = useState([]);
    const [projekcije, setProjekcije] = useState([]);
    const params = useParams();
    const id = params.id;
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`http://localhost:8080/api/film/${id}`)
            .then((response) => response.json())
            .then((data) => {
                setFilm(data);
            })
            .catch((err) => {
                console.log(err.message);
            });

        fetch(`http://localhost:8080/api/projekcija/film/${id}`)
            .then((response) => response.json())
            .then((data) => {
                setProjekcije(data);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, [id]);

    const urediOneProjekcija = async (id) => {
        navigate(`/projekcije/edit/${id}`);
    };

    const deleteProjekcija = async (id) => {
        await fetch(`http://localhost:8080/api/projekcija/${id}`, { method: "DELETE" });
        setProjekcije(
            projekcije.filter((p) => {
                return p.id !== id;
            })
        );
        return;
    };

    const deleteFilm = async (id) => {
        await fetch(`http://localhost:8080/api/film/${id}`, { method: "DELETE" });
        navigate(`/`);
        return;
    };

    return (
        <div>
            <Navbar />
            <h2>Naziv filma</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Naziv</th>
                        <th>Trajanje (min)</th>
                        <th>Dobna granica</th>
                        <th>Cijena ulaznice(€)</th>
                    </tr>
                </thead>
                <tbody>
                    <tr key={film.id}>
                        <td className="film-title">{film.id}</td>
                        <td className="film-title">{film.naziv}</td>
                        <td>{film.trajanjeMin}</td>
                        <td>{film.dobnaGranica}</td>
                        <td>{film.ulazEur}</td>
                        <td>
                            <button> Uredi film </button>
                        </td>
                        <td className="button">
                            <button className="delete-btn" onClick={() => deleteFilm(id)}>
                                Delete
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
            <h2>Popis projekicja za odabran film</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Dvorana</th>
                        <th>Datum</th>
                        <th>Vrijeme početka</th>
                        <th>Trajanje (u minutama)</th>
                        <th>Broj slobodnih mjesta</th>
                        <th>Unio</th>
                    </tr>
                </thead>
                <tbody>
                    {projekcije.map((p) => {
                        return (
                            <tr key={p.id}>
                                <td className="film-title">{p.id}</td>
                                <td className="film-title">{p.imeDvorana}</td>
                                <td>{p.datum}</td>
                                <td>{p.vrijemePoc}</td>
                                <td>{p.trajanjeMin}</td>
                                <td>{p.slobodnaMjesta == null ? 0 : p.slobodnaMjesta}</td>
                                <td>{p.unioProjekcija}</td>
                                <td>
                                    <button onClick={() => urediOneProjekcija(p.id)}> Uredi projekciju </button>
                                </td>
                                <td className="button">
                                    <button className="delete-btn" onClick={() => deleteProjekcija(p.id)}>
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
}
export default FilmIProjekcije;
