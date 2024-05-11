import {useState} from 'react'
import { NavLink, Link } from "react-router-dom";

function HeaderRaw(){
  const [loggedIn, setLoggedIn] = useState(false);
  const [admin, setAdmin] = useState(false);

  return (
    <nav id="nav">
      <ul>
        <li>Raw Link:</li>
        <li><Link to="/">Home</Link></li>
        {loggedIn ? 
          <>
            <li><Link to="/Entity">Entity</Link></li>
            <li><Link to="/EntityCreate/">EntityCreate</Link></li>
            <li><Link to="/EntityDetails/">EntityDetails</Link></li>
            <li><Link to="/Logout" onClick={() => setLoggedIn(false)}>Logout</Link></li>
          </>
          :
          <>
            <li><Link to="/Login" onClick={() => setLoggedIn(true)}>Login</Link></li>
            <li><Link to="/Register">Register</Link></li>
          </>
        }
        {admin ? <li><Link to="/Settings">Settings</Link></li> : <></>}
      </ul>
    </nav>
  )
}

export default HeaderRaw