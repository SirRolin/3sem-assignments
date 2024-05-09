

function Header(){
  var loggedIn = true; // temporary way

  return (
    <nav>
      <button>Home</button>
      {loggedIn ? 
        <>
          <button>Entity</button>
          <button>Logout</button>
        </>
        :
        <>
          <button>Login</button>
          <button>Register</button>
        </>
      }
      {admin ? <button>Settings</button> : <></>}
    </nav>
  )
}

export default Header