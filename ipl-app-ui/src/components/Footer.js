import React from "react";
import "./Footer.scss";

export const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-content">
        <div className="footer-copyright">
          <p>&copy; {new Date().getFullYear()} IPL Dashboard</p>
        </div>
        <div className="footer-links">
          <a href="https://github.com/akikr" target="_blank">GitHub</a>
        </div>
      </div>
    </footer>
  );
};
