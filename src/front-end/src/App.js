import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import { TeamPage } from './pages/TeamPage';

function App() {
  return (
    <div className="App">
      <Router>
        <Route path="/teams/:teamName">
          <TeamPage />
        </Route>
      </Router>
    </div>
  );
}

export default App;
