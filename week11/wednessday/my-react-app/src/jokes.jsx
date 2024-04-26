import React, { useState, useEffect } from 'react';

function JokeComponent() {
  // State to store the joke
  const [joke, setJoke] = useState('');

  function changeJoke() {
    fetch('https://api.chucknorris.io/jokes/random')
      .then(response => response.json())
      .then(data => setJoke(data.value))
      .catch(error => console.error('Error fetching joke:', error));
  };

  // useEffect to fetch the joke
  useEffect(() => {
    changeJoke() // start with a joke already

    // 1.2 userEffect part 1 - React 2
    const intervalId = setInterval(() => changeJoke(), 10000) // Every 10 seconds fetch a joke.

    // javascript method (run when user leaves)
    window.addEventListener('beforeunload', () => clearInterval(intervalId));

    // useEffect method (run on unmounting website)
    return () => {
      clearInterval(intervalId);
    }
  }, []); // Empty dependency array means this runs once on mount
  

  // Render the joke
  return (
    <div>
      <p>{joke}</p>
      <button onClick={changeJoke}>New Joke</button> {/*1.1 Click Event - React 2 */}
    </div>
  );
}

export default JokeComponent;
