import upper, {text1,text2, text3} from "./file1";
import person, {males, females} from "./file2.js";
import MultiWelcome from "./file3.jsx";
import JokeComponent from "./jokes.jsx";

let {firstName, lastName, gender, email} = person;
let extra = {
  ...person,
  phone: 22282131,
  friends: [...males, ...females]
};


export default function ex1(){
  console.log([...males, ...females]) // 3.2(.1?)
  console.log(extra) // 3.2(.2?)
  return (
    <>
    {/* 2 spread operator of React 0 */}
    <div>
      <h2>Ex 1</h2>
      <p>{upper}</p>
      <p>{text1}</p>
      <p>{text2}</p>
      <p>{text3}</p>
    </div>
    {/* 3 spread operator of React 0 */}
    <div>
      <h2>Ex2</h2>
      <p>{firstName}: {email}</p>
    </div>
    {/* props-1 of React 1 */}
    <div>
      <h2>Ex3</h2>
      <MultiWelcome/>
    </div>
    {/* 1.1 - Fetch and display jokes of React 2 */}
    <JokeComponent/>
    </>
  )
}