import { useState, useEffect } from 'react'

export default function Counter({count = 0, increment = 1, cookieName = "Counter"}) {
	const [_count, setCount] = useState(count)

	useEffect(() => {
		// on loading
		if(localStorage.getItem(cookieName) === null){
			localStorage.setItem(cookieName, count)
		}
		setCount(parseInt(localStorage.getItem(cookieName)))


		// Tried making it so it only when you leave it changes the storage, but it didn't do anything...
		// javascript method (run when user leaves) (or supposed to)
		//window.addEventListener('beforeunload', () => localStorage.setItem(cookieName, _count));
		
		return () => {
			//localStorage.setItem(cookieName, _count)
		}
	}, [])

	function buttonClicked(){
		setCount((_count) => _count + increment);
		localStorage.setItem(cookieName,  _count + increment)
	}

	return (
		<>
			<div className="card">
				<button onClick={buttonClicked}>
					count is {_count}
				</button>
			</div>
		</>
	)
}
  