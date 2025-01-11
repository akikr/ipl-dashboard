import React from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import "./NotFound.scss";

export const NotFound = ({ message }) => {
  return (
    <div className="not-found">
      <h1>404 - Page Not Found</h1>
      <h3>{message}</h3>
      <br />
      <Link to="/">Return to Home</Link>
    </div>
  );
};

NotFound.propTypes = {
  message: PropTypes.string,
};
