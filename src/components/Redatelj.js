import Navbar from "./Navbar";

import { useState, useEffect } from "react";
import { Button, Table } from "@mui/joy";

function Redatelj() {
    const [redatelji, setRedatelji] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/redatelj/all")
            .then((response) => response.json())
            .then((data) => {
                setRedatelji(data);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);

    const deleteRedatelj = async (id) => {
        await fetch(`http://localhost:8080/api/redatelj/${id}`, { method: "DELETE" });
        setRedatelji(
            redatelji.filter((red) => {
                return red.id !== id;
            })
        );
        return;
    };

    return (
        <div className="App">
            <Navbar />
            <div>
                <Table sx={{ textAlign: "left" }} variant="soft">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Ime</th>
                            <th>Prezime</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {redatelji.map((r) => {
                            return (
                                <tr key={r.id}>
                                    <td className="film-title">{r.id}</td>
                                    <td>{r.name}</td>
                                    <td>{r.surname}</td>
                                    <td className="Button">
                                        <Button color="danger" onClick={() => deleteRedatelj(r.ime)}>
                                            Obriši
                                        </Button>
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </Table>
            </div>
        </div>
    );
}

export default Redatelj;
