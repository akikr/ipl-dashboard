import { React } from "react";
import { Link } from "react-router-dom";
import "./NavBar.scss";

export const NavBar = () => {
  return (
    <div className="navbar">
      <Link to="/" className="navbar-brand">
        Teams Dashboard
      </Link>
    </div>
  );
};
