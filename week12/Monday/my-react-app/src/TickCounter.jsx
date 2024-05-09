import {useState, useEffect} from "react";

export default function TickCounter(){
    const [sec, setSec] = useState(0)
    const [min, setMin] = useState(5)
    const [time, setTime] = useState(0)
    const [started, setstarted] = useState(false)
    const [intervalId, setIntervalId] = useState(0)

    // I had to use this as otherwise it wouldn't update.
    let localTime = time
    let localintevalId = intervalId


	function startStop(){
        if(intervalId==0){
            if(time >= min * 60 + sec) {
                console.log("reseting")
                setTime(0)
            }
            setstarted(true)
            localintevalId = setInterval(() => {
                incrementTime()
                }, 1000)
            setIntervalId(localintevalId)
        } else {
            clearInterval(intervalId)
            setIntervalId(0)
            setstarted(false)
        }
	}

	function reset(){
        setTime(0)
        if(started){
            startStop()
        }
	}

    function inputSec(event){
        let parsed = parseInt(event.target.value)
        if(!isNaN(parsed)){
            reset()
            if(parsed >= 60){
                setMin(min + Math.floor(parsed / 60))
                event.target.value = parsed % 60
            } else if(parsed < 0) {
                setMin(min - Math.ceil(-parsed / 60))
                event.target.value = 60 - (-parsed % 60)
            }
            setSec(parseInt(event.target.value))
        }
    }

    function inputMin(event){
        reset()
        setMin(parseInt(event.target.value))
    }

    function inputPreset(event){
        reset()
        setMin(parseInt(event.target.value))
        setSec(0)
    }

    function incrementTime(){
        if(localTime >= min * 60 + sec) {
            clearInterval(localintevalId)
            setIntervalId(0)
            setstarted(false)
            // play sound
        } else {
            ++localTime
            setTime(localTime)
        }
    }

	return (
		<>
			<div className="card">
                <h1>Timer</h1>
                <div id="allTimer">
                    <div id="innerTimer" className="left">
                        <input id="input_minutes" onChange={inputMin} type="number" value={min} disabled={started} />
                        :
                        <input id="input_seconds" onChange={inputSec} type="number" value={sec} disabled={started} />
                        <p>time remaining: {String(Math.floor((min * 60 + sec - time) / 60)).padStart(2,'0')}:{String((min * 60 + sec - time) % 60).padStart(2,'0')}</p>
                        <button onClick={startStop} style={{width: 90 +'px'}}>
                            {started ? "pause" : "start"}
                        </button>
                        <button onClick={reset} style={{width: 90 +'px'}}>
                            {started ? "stop" : "reset"}
                        </button>
                    </div>
                    <div id="preset_timers" className="right">
                        <button onClick={inputPreset} value={5}>5 minutes</button>
                        <button onClick={inputPreset} value={10}>10 minutes</button>
                        <button onClick={inputPreset} value={15}>15 minutes</button>
                    </div>
                </div>
			</div>
		</>
	)
}