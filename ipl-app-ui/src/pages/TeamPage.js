import { React, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { MatchDetailCard } from "../components/MatchDetailCard";
import { MatchSmallCard } from "../components/MatchSmallCard";
import { PieChart } from "react-minimal-pie-chart";
import { NotFound } from "../components/NotFound";
import { APP_DATA_END_YEAR, APP_ROOT_URL } from "../utils/configHelper";
import "./TeamPage.scss";

export const TeamPage = () => {
  const [team, setTeam] = useState(null);
  const { teamName } = useParams();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchMatches = async () => {
      try {
        const response = await fetch(`${APP_ROOT_URL}/team/${teamName}`);
        if (response.status === 404) {
          setError("404");
        } else if (!response.ok) {
          throw new Error("Failed to fetch");
        } else {
          const data = await response.json();
          setTeam(data);
        }
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchMatches();
  }, [teamName]);

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
  if (error) return <div>Error: {error}</div>;
  if (!team) return null;

  return (
    <div className="TeamPage">
      <div className="team-name-section">
        <h1 className="team-name">{team.teamName}</h1>
      </div>
      <div className="win-loss-section">
        {"Wins / Losses"}
        <PieChart
          data={[
            {
              title: "Losses",
              value: team.totalMatches - team.totalWins,
              color: "#973831dc",
            },
            { title: "Wins", value: team.totalWins, color: "#0b815ac9" },
          ]}
        />
      </div>
      <div className="match-detail-section">
        <h2 className="latest-matches-heading">{"Latest Matches"}</h2>
        <MatchDetailCard teamName={team.teamName} match={team.matches[0]} />
      </div>
      {team.matches.slice(1).map((match) => (
        <MatchSmallCard key={match.id} teamName={team.teamName} match={match} />
      ))}
      <div className="more-link">
        <Link
          to={`/teams/${teamName}/matches/${APP_DATA_END_YEAR}`}
        >
          {"More >"}
        </Link>
      </div>
    </div>
  );
};
