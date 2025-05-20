import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Dvorana from "./components/Dvorana";
import Film from "./components/Film";
import Redatelj from "./components/Redatelj";
import JedanFilm from "./components/JedanFilm";
import UrediFilm from "./components/UrediFilm";
import JednaDvorana from "./components/JednaDvorana";
import UrediDvoranu from "./components/UrediDvoranu";
import Projekcija from "./components/Projekcija";
import UrediProjekciju from "./components/UrediProjekciju";
import Home from "./components/Home";
import Tip from "./components/Tipovi";

function App() {
    const root = ReactDOM.createRoot(document.getElementById("root"));
    root.render(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/projekcije" element={<Projekcija />} />
                <Route path="/projekcije/edit/:id" element={<UrediProjekciju />} />
                <Route path="/filmovi" element={<Film />} />
                <Route path="/filmovi/:id" element={<JedanFilm />} />
                <Route path="/filmovi/edit/:id" element={<UrediFilm />} />
                <Route path="/dvorane" element={<Dvorana />} />
                <Route path="/dvorane/:ime" element={<JednaDvorana />} />
                <Route path="/dvorane/edit/:ime" element={<UrediDvoranu />} />
                <Route path="/redatelji" element={<Redatelj />} />
                <Route path="/tipovi" element={<Tip />} />
            </Routes>
        </BrowserRouter>
    );
}
export default App;
