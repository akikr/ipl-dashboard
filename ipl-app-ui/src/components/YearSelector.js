import { React } from "react";
import { Link } from "react-router-dom";
import { APP_DATA_START_YEAR, APP_DATA_END_YEAR } from "../utils/configHelper";
import "./YearSelector.scss";

export const YearSelector = ({ teamName }) => {
  let years = [];

  for (let i = APP_DATA_START_YEAR; i <= APP_DATA_END_YEAR; i++) {
    years.push(i);
  }

  return (
    <ol className="YearSelector">
      {years.map((year) => (
        <li key={year}>
          <Link key={year} to={`/teams/${teamName}/matches/${year}`}>
            {year}
          </Link>
        </li>
      ))}
    </ol>
  );
};
