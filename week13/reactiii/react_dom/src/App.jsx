import { useState } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import Header from './Components/Header.jsx';
import Entity from './Components/Entity.jsx';
import EntityCreate from './Components/EntityCreate.jsx';
import EntityDetails from './Components/EntityDetails.jsx';
import Login from './Components/Login.jsx';
import Register from './Components/Register.jsx';
import Home from './Components/Home.jsx';
import Settings from './Components/Settings.jsx';
import Logout from './Components/Logout.jsx';

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Header />
      <Router>
        <Route path="/Entity/" content={Entity} />
        <Route path="/EntityCreate/" content={EntityCreate} />
        <Route path="/EntityDetails/" content={EntityDetails} />
        <Route path="/Login/" content={Login} />
        <Route path="/Register/" content={Register} />
        <Route path="/Home/" content={Home} />
        <Route path="/Settings/" content={Settings} />
        <Route path="/Logout/" content={Logout} />
      </Router>
    </>
  )
}

export default App
