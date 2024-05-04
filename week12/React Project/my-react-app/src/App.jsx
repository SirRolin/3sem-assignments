import './Styles/App.css'
import PersonForm from './Components/Form.jsx'
import PersonList from './Components/List.jsx'
import DAO from './dao/DAO.js'
import { useEffect, useState } from 'react'

function App() {
  const [page, setPage] = useState("Home Page")
  const [persons, setPersons] = useState([])
  const [editPerson, setEditPerson] = useState({})
  const personDAO = DAO("http://localhost:3001/api")

  function getPersons(callback){
    personDAO.GetAll(callback)
  }

  useEffect(() => {
    getPersons((data) => {
      setPersons(data)
    })
  }, [page])

  function deletePerson(obj){
    // fjern fra api - virker kun nogle gange
    personDAO.delete(obj, () => {})
    // fjern fra server array
    setPersons([...persons.filter(p => p.id != obj.id)])
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
      person.id = parseInt(persons.reduce((p1,p2) => p1.id > p2.id ? p1.id : p2.id)) + 1
      personDAO.create(person, () => setPersons([...persons, person]))
    } else {
      personDAO.update(person, () => setPersons([...persons.filter((p) => p.id != person.id), person]))
    }
    //setPage('List')
  }

  
  return (
    <>
      <h1>Person DB</h1>
      <button onClick={() => setPage('List')}>List</button>
      <button onClick={() => showForm({})}>Add New</button>
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
