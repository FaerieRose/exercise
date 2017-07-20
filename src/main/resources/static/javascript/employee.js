window.onload = function() {
    getAllEmployees();
}

function getAllEmployees() {
    console.log("Call to retrieve all Employees");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
          if (this.status == 200) {
              console.log(this.responseText);
              var employeeList = JSON.parse(this.responseText);
              var employeeTable = document.getElementById("tblEmployees");
              employeeTable.innerHTML = "";
        	  var row = document.createElement("tr");
        	  var col1 = document.createElement("th");
        	  var col2 = document.createElement("th");
        	  col1.textContent = "ID";
        	  col2.textContent = "Name";
        	  col1.setAttribute("id", "hdrID")
        	  col2.setAttribute("id", "hdrName")
        	  row.appendChild(col1);
        	  row.appendChild(col2);
        	  employeeTable.appendChild(row);
              for (var i=0 ; i< employeeList.length ; i++) {
            	  row = document.createElement("tr");
            	  col1 = document.createElement("td");
            	  col2 = document.createElement("td");
            	  col1.textContent = employeeList[i].id;
            	  col2.textContent = employeeList[i].name;
            	  row.setAttribute("class","rowEmployee")
            	  row.setAttribute("onclick","removeEmployeeById('" + employeeList[i].id + "')")
            	  row.appendChild(col1);
            	  row.appendChild(col2);
            	  employeeTable.appendChild(row);
              }
          } else if (this.status == 204) {
              console.log("No data available");
          } else {
              console.log("Status XMLHttpRequest : " + this.status);
          }
        }
    };
    xhttp.open("GET", "http://localhost:8081/api/employee");
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

function saveEmployeeByName() {
	var newName = document.getElementById("strNewEmployee").value;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 &&  this.status == 200) {
            getAllEmployees();
        } else {
            console.log("Status XMLHttpRequest : " + this.status);
        }
    };
    xhttp.open("POST", "http://localhost:8081/api/employee/new/" + newName, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}


function removeEmployeeById(id) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 &&  this.status == 200) {
            getAllEmployees();
        } else {
            console.log("Status XMLHttpRequest : " + this.status);
        }
    };
    xhttp.open("DELETE", "http://localhost:8081/api/employee/del/" + id, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}