import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { TeamPage } from "./pages/TeamPage";
import { MatchPage } from "./pages/MatchPage";
import { HomePage } from "./pages/HomePage";
import { NavBar } from "./components/NavBar";
import "./App.scss";

function App() {
  return (
    <div className="App">
      <Router>
        <NavBar />
        <Routes>
          <Route
            path="/teams/:teamName/matches/:year"
            element={<MatchPage />}
          />
          <Route path="/teams/:teamName" element={<TeamPage />} />
          <Route path="/" element={<HomePage />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
