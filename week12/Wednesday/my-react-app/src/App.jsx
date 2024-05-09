import './Styles/App.css'
import PersonForm from './Components/PersonForm.jsx'
import PersonList from './Components/PersonList.jsx'
import { fetchData } from './util/persistence.jsx'
import { useEffect, useState } from 'react'

function App() {
  const [page, setPage] = useState("Home Page")
  const [persons, setPersons] = useState([])
  const [editPerson, setEditPerson] = useState({})
  const APIURL = "http://localhost:3000/api";

  function getPersons(callback){
    fetchData(APIURL, callback)
  }

  useEffect(() => {
    getPersons((data) => {
      setPersons(data)
    })
  }, [])

  function deletePerson(id){
    //fjern fra api - virker kun nogle gange
    fetchData(`${APIURL}/${id}`, () => {
      //fjern fra server array - hvis successful
      setPersons([...persons.filter(p => p.id != id)])
    }, "DELETE")
    
  }

  function showForm(person = null){
    setEditPerson(person)
    setPage('Form')
  }

  function updatePeople(person){
    if(person.gender == ""){
      console.log("gender=null")
      return;
    }
    if(person.id == "" || person.id == null){
      console.log("add")
      person.id = parseInt(persons.reduce((p1,p2) => p1.id > p2.id ? p1.id : p2.id)) + 1
      fetchData(`${APIURL}`, ()=>{}, "POST", person)
    } else {
      console.log("update")
      fetchData(`${APIURL}/${person.id}`, ()=>{}, "PUT", person)
    }
  }

  
  return (
    <>
      <h1>Person DB</h1>
      <button onClick={() => setPage('List')}>List</button>
      <button onClick={() => showForm({})}>Edit</button>
      {
        {
          'List': <PersonList persons={persons} deleteCallback={deletePerson} editCallback={showForm} />,
          'Form': <PersonForm person={editPerson} updateCallback={updatePeople}/>
        }[page]
      }
    </>
  )
}

export default App
