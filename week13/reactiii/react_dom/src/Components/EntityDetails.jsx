

export function EntityDetailId(){
  let parans = useParams();
  return (
    <>
      <h2>post = {useParams.entityId}</h2>
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