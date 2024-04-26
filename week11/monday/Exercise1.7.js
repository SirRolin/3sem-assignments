    
    // global variables
    let display
    let buttons

    function onLoad(){
        // loading global variables, here as normally you load scripts in the header, but that means the DOm hasn't been fully loaded yet.
        display = document.getElementById("display")
        buttons = document.getElementById("buttons")
        buttons.addEventListener('click', calculateClick);
        //Array.from(buttons.children).forEach(x=> x.addEventListener('click', calculateClick))
    }
    document.addEventListener("DOMContentLoaded", onLoad)

    function calculateClick(event){
        // avoiding bugs depending on browser and version
        e = event || window.event; // ie <9
        var targ = e.target || e.srcElement || e;
        if (targ.nodeType == 3) targ = targ.parentNode; // Safari
        
        if(targ.innerText === "="){
            display.innerText = calculate(display.innerText)
        } else if(targ.innerText.length == 1){
            display.innerText = display.innerText + targ.innerText
        } else if(targ.innerText == "clear"){
            display.innerText = ""
        }
    }

    function calculate(text){
        //text = eval(text) // the easy solution

        // a harder solution (later found out it doesn't always work)
        /*
        let plusTexts = text.split("+")
        for(let plusIte = 0; plusIte < plusTexts.length; plusIte++){
            let minusTexts = plusTexts[plusIte].split("-")
            for(let minusIte = 0; minusIte < minusTexts.length; minusIte++){
                let divideTexts = minusTexts[minusIte].split("/")
                for(let divideIte = 0; divideIte < divideTexts.length; divideIte++){
                    let timesTexts = divideTexts[divideIte].split("*")
                    console.log(timesTexts)
                    divideTexts[divideIte] = timesTexts.reduce((o1, o2) => o1*o2)
                }
                minusTexts[minusIte] = divideTexts.reduce((o1, o2) => o1/o2)
            }
            plusTexts[plusIte] = minusTexts.reduce((o1, o2) => o1-o2)
        }
        text = plusTexts.reduce((o1, o2) => parseFloat(o1)+parseFloat(o2))*/

        // new solution - includes parentheses, parentheses shorts and the four normal operators
        // known bugs: doesn't understand left-empty + operators (not having something to add on the left side)
        // which is actually the same as eval
        // remove whitespace
        text = text.replaceAll(" ", "")

        // Prepare Parentheses
        const parentheseRegex = "(\\d|\\))(\\()"
        let shorts = text.match(parentheseRegex);
        while(shorts){
            text = text.replace(shorts[0], shorts[1]  + "*" + shorts[2])
            shorts = text.match(parentheseRegex);
        }

        // parentheses
        let indexOfStartParentheses = text.indexOf("(") + 1
        while(indexOfStartParentheses > 0) {
            let indexOfEndParentheses = text.indexOf(")")
            let deeps = 0
            let match = text.substring(indexOfStartParentheses, indexOfEndParentheses).match("\\(")
            while(match && match.length > deeps){
                ++deeps
                indexOfEndParentheses+=text.substring(indexOfEndParentheses).indexOf(")") + 1
                match = text.substring(indexOfStartParentheses, indexOfEndParentheses).match("\\(")
            }
            text = text.replace(text.substring(indexOfStartParentheses - 1, indexOfEndParentheses + 1), calculate(text.substring(indexOfStartParentheses, indexOfEndParentheses)))
            indexOfStartParentheses = text.indexOf("(") + 1
        }

        // multiplying
        const multRegex = "((?:(?<!\\d>)-?)\\d+\\.?\\d?)\\*(-?\\d+\\.?\\d?)"
        let mults = text.match(multRegex);
        while(mults){
            //console.log("mults is: ")
            //console.log(mults)
            text = text.replace(mults[0], parseFloat(mults[1]) * parseFloat(mults[2]))
            mults = text.match(multRegex);
        }

        // dividing
        const divideRegex = "((?:(?<!\\d>)-?)\\d+\\.?\\d?)\\/(-?\\d+\\.?\\d?)"
        let divides = text.match(divideRegex);
        while(divides){
            //console.log("divides is: ")
            //console.log(divides)
            text = text.replace(divides[0], parseFloat(divides[1]) / parseFloat(divides[2]))
            divides = text.match(divideRegex);
        }

        // subtracting
        const minusRegex = "((?:(?<!\\d>)-?)\\d+\\.?\\d?)\\-(-?\\d+\\.?\\d?)"
        let minuses = text.match(minusRegex);
        while(minuses){
            //console.log("minuses is: ")
            //console.log(minuses)
            text = text.replace(minuses[0], parseFloat(minuses[1]) - parseFloat(minuses[2]))
            minuses = text.match(minusRegex);
        }
        
        // addition
        const plusRegex = "((?:(?<!\\d>)-?)\\d+\\.?\\d?)\\+(-?\\d+\\.?\\d?)"
        let pluses = text.match(plusRegex);
        while(pluses){
            //console.log("pluses is: ")
            //console.log(pluses)
            text = text.replace(pluses[0], parseFloat(pluses[1]) + parseFloat(pluses[2]))
            pluses = text.match(plusRegex);
        }

        // returns the result
        return text
    }

