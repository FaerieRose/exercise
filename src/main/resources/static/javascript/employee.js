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
            	  row.setAttribute("onclick","seeEmployee('" + employeeList[i].id + "')")
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
        if (this.readyState == 4 && this.status == 200) {
        	if (this.responseText > 0) {
                getAllEmployees();
                document.getElementById("pEmployeeNewFeedback").textContent = "";
                document.getElementById("strNewEmployee").value = "";
        	} else {
                document.getElementById("pEmployeeNewFeedback").textContent = "Warning: Unable to create new Employee. Name too short or already exists.";
        	}
        } else {
            console.log("Status XMLHttpRequest : " + this.status);
        }
    };
    xhttp.open("POST", "http://localhost:8081/api/employee/new/" + newName, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

function seeEmployee(id) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 &&  this.status == 200) {
            console.log(this.responseText);
            employee = JSON.parse(this.responseText);
            setSeeEmployee(employee.name, employee.id, false)
        } else {
            console.log("Status XMLHttpRequest : " + this.status);
        }
    };
    xhttp.open("GET", "http://localhost:8081/api/employee/id/" + id);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
	
}

function removeEmployee(id) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 &&  this.status == 200) {
            getAllEmployees();
            setSeeEmployee("", -1, true)
        } else {
            console.log("Status XMLHttpRequest : " + this.status);
        }
    };
    xhttp.open("DELETE", "http://localhost:8081/api/employee/del/" + id, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

function updateEmployee(id) {
	var name = document.getElementById("strEmployeeName").value;
	var data = '{"id":' + id + ',"name":"' + name + '"}';
	console.log(data);
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
        	if (this.status == 200) {
                getAllEmployees();
                document.getElementById("pEmployeeInformationFeedback").textContent = "";
        	} else if (this.status == 304) {
        		document.getElementById("pEmployeeInformationFeedback").textContent = "Warning: Unable to update Employee"
        	} else {
        		document.getElementById("pEmployeeInformationFeedback").textContent = "Error: Unable to update Employee"
        	}
        } else {
            console.log("Status XMLHttpRequest : " + this.status);
        }
    };
    xhttp.open("PUT", "http://localhost:8081/api/employee/update", true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(data);
}


function setSeeEmployee(name, id, isDisabled) {
    document.getElementById("strEmployeeName").value = name;
    document.getElementById("btnEmployeeUpdate").disabled = isDisabled;
    document.getElementById("btnEmployeeRemove").disabled = isDisabled;
    document.getElementById("pEmployeeInformationFeedback").textContent = "";
    if (id == -1) {
        document.getElementById("btnEmployeeUpdate").removeAttribute("onclick");
        document.getElementById("btnEmployeeRemove").removeAttribute("onclick");
    } else {
        document.getElementById("btnEmployeeUpdate").setAttribute("onclick","updateEmployee(" + id + ")");
        document.getElementById("btnEmployeeRemove").setAttribute("onclick","removeEmployee(" + id + ")");
    }
}
