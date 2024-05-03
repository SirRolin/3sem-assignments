import { useEffect, useState } from 'react'

function PersonForm({person = null, updateCallback}){
	const [formData, setFormData] = useState({
    id: '',
    name: '',
    age: '',
    email: '',
    gender: ''
  });

	useEffect(() => {
		setFormData({
			id: person.id || '',
			name: person.name || '',
			age: person.age || '',
			email: person.email || '',
			gender: person.gender || ''
		});
	}, [person])

	function changeValue(event){
		setFormData({...formData, [event.target.id]: event.target.value})
	}
		
	return (
		<div>
			<h1>Edit Person</h1>
			<form>
				<label htmlFor="id">Id</label>
				<input id="id" type="number" readOnly placeholder="id" value={formData.id}/>
				<label htmlFor="name">Name</label>
				<input id="name" type="text" placeholder="name" value={formData.name} onChange={changeValue} />
				<label htmlFor="age">Age</label>
				<input id="age" type="number" min="1" max="120" placeholder="age" value={formData.age} onChange={changeValue} />
				<label htmlFor="email">Email</label>
				<input id="email" type="email" placeholder="email" value={formData.email} onChange={changeValue} />
				<label htmlFor="gender">Gender</label>
				<select id="gender" value={formData.gender} onChange={changeValue} required>
					<option defaultValue value="">Select Gender</option>
					<option value="male">Male</option>
					<option value="female">Female</option>
					<option value="other">Other</option>
				</select>
				<button onClick={updateCallback(formData)}>{formData.id == null ? "add": "update"}</button>
			</form>
		</div>
	)
}

export default PersonForm