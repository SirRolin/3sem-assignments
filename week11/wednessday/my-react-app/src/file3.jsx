import {persons} from "./file2.js"

function Welcome(props) {
  return <h1>Hello, {props.name}</h1>;
}
function MultiWelcome() {
  return (
    <div>
      {/* props-1 of React 1 */}
      <Welcome name="Sara" />
      <Welcome name="Cahal" />
      <Welcome name="Edith" />
      {/* props-2 of React 1 */}
      <WelcomePerson person={persons[0]}  />
          {persons.map((person) => <Welcome key={person.firstName} name={person.firstName} />)}
    </div>
  );
}
export default MultiWelcome;

export function WelcomePerson(props) {
  return (
    <div>
      <p>first name: {props.person.firstName}</p>
      <p>last name: {props.person.lastName}</p>
      <p>gender: {props.person.gender}</p>
      <p>email: {props.person.email}</p>
      <p>phone: {props.person.phone}</p>
    </div>
  );
}