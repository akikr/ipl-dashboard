import { React, useEffect, useState } from "react";
import { useParams } from "react-router";
import { MatchDetailCard } from "../components/MatchDetailCard";
import { YearSelector } from "../components/YearSelector";
import { NotFound } from "../components/NotFound";
import { APP_ROOT_URL } from "../utils/configHelper";
import "./MatchPage.scss";

export const MatchPage = () => {
  const [matches, setMatches] = useState(null);
  const { teamName, year } = useParams();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchMatches = async () => {
      try {
        const response = await fetch(`${APP_ROOT_URL}/team/${teamName}/matches?year=${year}`);
        if (response.status === 404) {
          setError("404");
        } else if (response.status === 400) {
          setError("400");
        } else if (!response.ok) {
          throw new Error("Failed to fetch");
        } else {
          const data = await response.json();
          setMatches(data.matches);
        }
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchMatches();
  }, [teamName, year]);

  if (loading) return <div>Loading...</div>;
  if (error === "404")
    return (
      <NotFound
        message={
          <>
            Team <span className="highlight-team">{teamName}</span> not found in
            IPL database !!
          </>
        }
      />
    );
  if (error === "400")
    return (
      <NotFound
        message={
          <>
            Data for year <span className="highlight-team">{year}</span> not
            found in IPL database !!
          </>
        }
      />
    );
  if (error) return <div>Error: {error}</div>;
  if (!matches) return null;

  return (
    <div className="MatchPage">
      <div className="year-selector">
        <h3>{"Select Year"}</h3>
        <YearSelector teamName={teamName} />
      </div>
      <div>
        <h1 className="page-heading">
          {teamName}
          {" matches in "}
          {year}
        </h1>
        {matches.length <= 0 ? (
          <h1 className="team-info">
            {teamName}
            {" didn't played this year !!"}
          </h1>
        ) : (
          matches.map((match) => (
            <MatchDetailCard key={match.id} teamName={teamName} match={match} />
          ))
        )}
      </div>
    </div>
  );
};
