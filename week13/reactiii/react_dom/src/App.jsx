import React, { useState, Fragment } from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Header from './Components/Header.jsx';
import HeaderRaw from './Components/Header raw.jsx';
import Entity from './Components/Entity.jsx';
import EntityCreate from './Components/EntityCreate.jsx';
import EntityDetails, { EntityDetailId } from './Components/EntityDetails.jsx';
import Login from './Components/Login.jsx';
import Register from './Components/Register.jsx';
import Home from './Components/Home.jsx';
import Settings from './Components/Settings.jsx';
import Logout from './Components/Logout.jsx';
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Router>
        <Header />
        <HeaderRaw />
        <Routes>
          <Route path="/Entity/" element={<Entity />} />
          <Route path="/EntityCreate/" element={<EntityCreate />} />
          <Route path="/EntityDetails/" element={<EntityDetails />} >
            <Route index element={<h1>Entity Detail:</h1>} />
            <Route path=":entityId" element={<EntityDetailId />} />
          </Route>
          <Route path="/Login/" element={<Login />} />
          <Route path="/Register/" element={<Register />} />
          <Route path="/" exact element={<Home />} />
          <Route path="/Settings/" element={<Settings />} />
          <Route path="/Logout/" element={<Logout />} />
        </Routes>
      </Router>
    </>
  )
}

export default App
