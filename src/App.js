import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./components/Home";
import Dvorana from "./components/Dvorana";
import Film from "./components/Film";
import Administrator from "./components/Administrator";
import JedanFilm from "./components/JedanFilm";

function App() {
    const root = ReactDOM.createRoot(document.getElementById("root"));
    root.render(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/filmovi" element={<Film />} />
                <Route path="/filmovi/:id" element={<JedanFilm />} />
                <Route path="/dvorane" element={<Dvorana />} />
                <Route path="/administratori" element={<Administrator />} />
            </Routes>
        </BrowserRouter>
    );
}
export default App;
