import {useState} from "react";

function MemberTable({ members }) {
  return (
    <table>
      <thead>
        <tr><td>name</td><td>age</td></tr>
      </thead>
      <tbody>
        {/* for some reason react insists that each tr needs it's own key, otherwise it throws an error. */}
        {members.map(element => <tr key={crypto.randomUUID()}><td>{element.name}</td><td>{element.age}</td></tr>
        )}
      </tbody>
    </table>
  );
}

function MemberDemo(props) {
  return (
    <div>
      <h4>All Members</h4>
      <MemberTable members={props.members}/>
    </div>
  );
}

export default function ListDemo() {

  const initialMembers = [{name : "Peter", age: 18},
                          {name : "Hanne", age: 35},
                          {name : "Janne", age: 25},
                          {name : "Holger", age: 22}];

  const [members,setMembers] = useState(initialMembers);
  
  return (<MemberDemo members={members} />);
}