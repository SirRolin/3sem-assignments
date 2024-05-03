import { useState } from 'react'
import Counter from './counter'
import TickCounter from './TickCounter'
import ListDemo from './ListDemo.jsx'

import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Counter count={5} increment={2}/>
      <ListDemo />
      <TickCounter />
    </>
  )
}

export default App
