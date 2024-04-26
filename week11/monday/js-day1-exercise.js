// 1.1.1
let names = ["Lars", "Peter", "Jan", "Bo"]

// 1.1.2
let myFilter = function(arr, predic){
    return arr.filter(predic)
}
console.log(myFilter(names, x => x.length > 3)) // output (2) ['Lars', 'Peter']

// 1.1.3
let myMap = function(arr, func){
    return arr.map(func)
}
console.log(myMap(names, x => x.substring(1,0))) // output (4) ['L', 'P', 'J', 'B']

// 1.2
Array.prototype.myFilter = function(predic){
    return this.filter(predic)
}
Array.prototype.myMap = function(func){
    return this.map(func)
}

names = ["Lars", "Peter", "Jan", "Bo"]
let newArray = names.myFilter(function(name) {
    return name.length > 3
})
console.log(newArray) // output (2) ['Lars', 'Peter']
console.log(names.myMap( x => x.length)) // (4) [4, 5, 3, 2]


// 1.3.1
// I am specifically doing this cause we were asked to make a JS file, and if you use document when running a JS file without a html it throws errors
if(typeof document !== 'undefined'){
    document.addEventListener("DOMContentLoaded", function() {
        console.log("document loaded, task 1.3 and on will continue")
        let divs = document.getElementsByTagName("div")
        console.log(divs)
        for(const element of divs){
            element.style.backgroundColor = "yellow"
        };
    })
} else {
    console.log("document not loaded")
}

// 1.3.2
let colors = ["Red", "White", "Cyan", "Silver", "Blue", "Gray", "DarkBlue", "Black", "LightBlue", "Orange", "Purple", "Brown", "Yellow", "Maroon", "Lime", "Green", "Magenta", "Olive", "Pink", "Aquamarine"]
function randomiseColors(){
    let theseColors = [...colors]
    let divs = document.getElementsByTagName("div")
    for(const element of divs){
        let colorindex = Math.floor(Math.random() * theseColors.length)
        let color = theseColors[colorindex]
        delete theseColors[colorindex]
        element.style.backgroundColor = color
    };
}

// 1.4
function announce(e){
    e = e || window.event;
    var targ = e.target || e.srcElement || e;
    if (targ.nodeType == 3) targ = targ.parentNode; // defeat Safari bug
    console.log("Hi I am from " + targ.id)
    const outputparagraph = document.getElementById("output_of_announce")
    outputparagraph.innerText = "Hi I am from " + targ.id
}
if(typeof document !== 'undefined'){
    document.addEventListener("DOMContentLoaded", function() {
        this.getElementById("outer").addEventListener("click", (event) => announce(event))
    })
}