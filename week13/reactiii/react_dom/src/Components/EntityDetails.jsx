import { Outlet } from 'react-router-dom'
import { useParams } from 'react-router-dom'

export function EntityDetailId(){
  let params = useParams();
  return (
    <>
      <h2>Entity details: {params.entityId}</h2>
    </>
  )
}

function EntityDetails(){


  return (
    <>
      <h1>I am EntityDetails!</h1>
      <Outlet />
    </>
  )
}

export default EntityDetails