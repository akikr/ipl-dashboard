import { React, useEffect, useState } from "react";
import { TeamTile } from "../components/TeamTile";
import { APP_ROOT_URL, APP_TOTAL_TEAM_SIZE } from "../utils/configHelper";
import "./HomePage.scss";

export const HomePage = () => {
  const [teams, setTeams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchAllTeams = async () => {
      try {
        const response = await fetch(`${APP_ROOT_URL}/teams?page=0&size=${APP_TOTAL_TEAM_SIZE}`);
        if (!response.ok) {
          throw new Error("Failed to fetch");
        } else {
          const data = await response.json();
          setTeams(data.teams);
        }
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchAllTeams();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!teams) return null;

  return (
    <div className="HomePage">
      <div className="header-section">
        <h1 className="app-name">{"IPL Teams"}</h1>
      </div>
      <div className="team-grid">
        {teams
          .sort((a, b) => a.teamName.localeCompare(b.teamName))
          .map((team) => (
            <TeamTile key={team.id} teamName={team.teamName} />
          ))}
      </div>
    </div>
  );
};
