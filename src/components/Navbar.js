import "../navbar.css";

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="navbar-center">
                <ul className="nav-links">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        <a href="/projekcije">Projekcije</a>
                    </li>
                    <li>
                        <a href="/filmovi">Filmovi</a>
                    </li>
                    <li>
                        <a href="/dvorane">Dvorane</a>
                    </li>
                    <li>
                        <a href="/redatelji">Redatelji</a>
                    </li>
                    <li>
                        <a href="/tipovi">Tipovi projekcija</a>
                    </li>
                </ul>
            </div>
        </nav>
    );
};

export default Navbar;
