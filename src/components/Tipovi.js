import { useState, useEffect } from "react";
import { Button, Table } from "@mui/joy";

import Navbar from "./Navbar";
function Tip() {
    const [tipovi, setTipovi] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/tip/all")
            .then((response) => response.json())
            .then((data) => {
                setTipovi(data);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);

    const deleteTip = async (id) => {
        await fetch(`http://localhost:8080/api/tip/${id}`, { method: "DELETE" });
        setTipovi(
            tipovi.filter((tip) => {
                return tip.id !== id;
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
                        {tipovi.map((t) => {
                            return (
                                <tr key={t.id}>
                                    <td className="film-title">{t.id}</td>
                                    <td>{t.tip}</td>
                                    <td className="Button">
                                        <Button color="danger" onClick={() => deleteTip(t.ime)}>
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

export default Tip;
