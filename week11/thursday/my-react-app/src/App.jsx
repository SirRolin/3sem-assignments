import { useState, useEffect } from 'react';
import SvgComponent2 from './SvgComponent.jsx';
import './App.css';

function App() {
  const [currentCountryCode, setCountry] = useState('');
  const [originalFill, setOriginalFill] = useState('');

  // task 4 - 5.1, task 5 - 5.2 & task 5 - 5.3

  function selectCountry(event){
    // If it has a fill (is not the ocean) and it doesn't have a white background (large bodies of water).
    // I choose to do it this way because we could later add more to the map that's not supposed to be clicked on either.

    /*// without the use of useState
    if(event.target.getAttribute("fill") != "null" && event.target.getAttribute("fill") != "#fff"){
      //console.log(event.target.id) // debugging
      const svg = document.getElementById("svg2")

      const paths = svg.getElementsByTagName("path")
      for(const path of paths){
        if(path.id != event.target.id){
          if(path.getAttribute("fill") == "red"){
            path.setAttribute("fill", "silver")
          }
        } else { 
          path.setAttribute("fill", "red")
        }
      };
    }
    */

    // With the use of useState
    // This also lets us unselect and lets us reselect the colout it was to begin with.
    if(currentCountryCode != ''){
      document.getElementById("svg2").getElementById(currentCountryCode).setAttribute("fill", originalFill)
    }
    if( event.target.id == "svg2" ){
      setCountry('')
      setOriginalFill('')
    } else {
      setCountry(event.target.id)
      let path = document.getElementById("svg2").getElementById(event.target.id);
      setOriginalFill(path.getAttribute("fill"))
      path.setAttribute("fill", "red")
    }
  }

  // task 4 - 5.2
  const [dataDumb, setdataDumb] = useState(null);
  const translatesvgCodeToCode = [["ru-main", "ru"], ["gb-gbn", "gb"], ["gb-nir", "irl"] /* apparently there's 2 irelands, but restcountries don't believe so */, ["Large masses of water", "-1"], ["svg2",""]]
  const lakes = [{
    "name": {
      "common": "lakes"
    },
    "population":0,
    "area":"varying",
    "borders":["varying"]
  }]

  function fetchDataFromRestCounties(event) {
    let code = event.target.id
    translatesvgCodeToCode.filter(x=> x[0] == code).forEach(x=> code = x[1])
    if(code == ''){
      setdataDumb(null)
    } else if(code == "-1") {
      setdataDumb(lakes)
    } else {
      fetch("https://restcountries.com/v3.1/alpha/" + code)
      .then(response => response.json())
      .then(data => {setdataDumb(data); console.log(data[0])})
      .catch(error => {
        setdataDumb(null)
        console.error('Error fetching country:', error)
      });
    }
  }

  useEffect(() => {
    document.getElementById("svg2").addEventListener('click', clickEvent)
    return () => {
      document.getElementById("svg2").removeEventListener('click', clickEvent);
    }
  })

  // I use this instead of two of click events, cause that cause it to require two clicks for the second event
  function clickEvent(event){
    selectCountry(event);
    fetchDataFromRestCounties(event);
  }




  return (
    <>
    <SvgComponent2 />
    {dataDumb && (
      <div id="countryInfoDumb">
        <p>Country: {dataDumb[0].name.common}</p>
        <p>Population: {dataDumb[0].population}</p>
        <p>Area: {dataDumb[0].area}</p>
        <p>Borders: {dataDumb[0].borders ? dataDumb[0].borders.join(", ") : "no one"}</p>
      </div>
      )}
    </>
  )
}

export default App
