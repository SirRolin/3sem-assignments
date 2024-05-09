import {useState} from 'react'
import { NavLink, Link } from "react-router-dom";

function Header(){
  const [loggedIn, setLoggedIn] = useState(false);
  const [admin, setAdmin] = useState(false);

  return (
    <nav id="nav">
      <ul>
        <li>Nav Link:</li>
        <li><NavLink to="/" activeClassName="active">Home</NavLink></li>
        {loggedIn ? 
          <>
            <li><NavLink to="/Entity" activeClassName="active">Entity</NavLink></li>
            <li><NavLink to="/EntityCreate/" activeClassName="active">EntityCreate</NavLink></li>
            <li><NavLink to="/EntityDetails/" activeClassName="active">EntityDetails</NavLink></li>
            <li><NavLink to="/Logout" activeClassName="active" onClick={setLoggedIn(false)}>Logout</NavLink></li>
          </>
          :
          <>
            <li><NavLink to="/Login" activeClassName="active" onClick={setLoggedIn(true)}>Login</NavLink></li>
            <li><NavLink to="/Register" activeClassName="active">Register</NavLink></li>
          </>
        }
        {admin ? <li><NavLink to="/Settings" activeClassName="active">Settings</NavLink></li> : <></>}
      </ul>
    </nav>
  )
}

export default Header