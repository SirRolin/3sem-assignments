    
    var cars = [
        { id: 1, year: 1997, make: 'Ford', model: 'E350', price: 3000 },
        { id: 2, year: 1999, make: 'Chevy', model: 'Venture', price: 4900 },
        { id: 3, year: 2000, make: 'Chevy', model: 'Venture', price: 5000 },
        { id: 4, year: 1996, make: 'Jeep', model: 'Grand Cherokee', price: 4799 },
        { id: 5, year: 2005, make: 'Volvo', model: 'V70', price: 44799 }
    ];

    let table

    function tablefyCars(arr){
        let header = "<th>id</th><th>year</th><th>make</th><th>model</th><th>price</th>"
        if(arr.length === 0){
            return header
        }
        return header + "<tr>" + arr.map(car => "<td>" + car.id + "</td><td>" + car.year + "</td><td>" + car.make + "</td><td>" + car.model + "</td><td>" + car.price + "</td>").join("</tr><tr>")
    }
    
    function onLoad(){
        table = document.getElementById("carsTable")
        table.innerHTML = tablefyCars(cars)
    }
    document.addEventListener("DOMContentLoaded", onLoad)

    function filterPriceLowerThan(){
        let input = document.getElementById("inputlessPriceThan")
        if(/^\d*\.*\d+$/.test(input.value)){
            table.innerHTML = tablefyCars(cars.filter(x=>x.price < input.value))
        } else {
            table.innerHTML = tablefyCars(cars)
        }
    }

