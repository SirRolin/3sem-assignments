import { fetchData } from './../util/persistence.jsx'

function DAO(url){
  return {
    update(object, callback){
      fetchData(`${url}/${object.id}`, callback, "PUT", object)
    },
    create(object, callback){
      fetchData(`${url}`, callback, "POST", object)
    },
    delete(object, callback){
      fetchData(`${url}/${object.id}`, callback, "DELETE")
    },
    GetAll(callback){
      fetchData(`${url}`, callback, "GET")
    }
  }
}

export default DAO