window.onload = function() {
    getAllEmployees();
}

function getAllEmployees() {
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
        	  var col3 = document.createElement("th");
        	  col1.textContent = "ID";
        	  col2.textContent = "Name";
        	  col3.textContent = "Partner";
        	  col1.setAttribute("id", "hdrID")
        	  col2.setAttribute("id", "hdrName")
        	  col3.setAttribute("id", "hdrPartner")
        	  row.appendChild(col1);
        	  row.appendChild(col2);
        	  row.appendChild(col3);
        	  employeeTable.appendChild(row);
              for (var i=0 ; i< employeeList.length ; i++) {
            	  row = document.createElement("tr");
            	  col1 = document.createElement("td");
            	  col2 = document.createElement("td");
            	  col3 = document.createElement("td");
            	  col1.textContent = employeeList[i].id;
            	  col2.textContent = employeeList[i].name;
            	  if (employeeList[i].partner != null) {
            		  col3.textContent = employeeList[i].partner.name;  
            	  }
            	  row.setAttribute("class","rowEmployee")
            	  row.setAttribute("onclick","seeEmployee('" + employeeList[i].id + "')")
            	  row.appendChild(col1);
            	  row.appendChild(col2);
            	  row.appendChild(col3);
            	  employeeTable.appendChild(row);
              }
          } else if (this.status == 204) {
              console.log("No data available");
          }
        }
    };
    xhttp.open("GET", "http://localhost:8081/api/employee/findall");
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

function saveEmployeeByName() {
	var newName = $("#strNewEmployee").val();
	var url = "http://localhost:8081/api/employee/new?name=" + newName;
	var data = '';
	$.post(url, data, function(response, status){
		console.log(response);
		if(status == "success") {
			if (response > 0) {
				getAllEmployees();
				$("#pEmployeeNewFeedback").text("");
				$("#strNewEmployee").val("");
			} else {
				$("#pEmployeeNewFeedback").text("Warning: Unable to create new Employee. Name too short or already exists.");
			}
		}
	},"text");
}

function seeEmployee(id) {
	var url = "http://localhost:8081/api/employee/find/" + id;
	$.get(url, function(employee, status) {
		if(status == "success") {
			console.log(employee);
            if (employee.partner != null) {
                setSeeEmployee(employee.name, employee.partner.name, employee.id, false)
            } else {
                setSeeEmployee(employee.name, "", employee.id, false)
                getPartnerList(employee.id);
            }
		}
		
	});
}

function changeEmployee(id, param, data) {
	console.log(data);
	$.ajax({
		url: "http://localhost:8081/api/employee/" + id + param,
		type: 'PUT',
		contentType: "application/json",
		data: data,
		success: function(response, status) {
			console.log(status);
        	if (status == "success") {
                getAllEmployees();
                seeEmployee(id);
                $("#pEmployeeInformationFeedback").text("");
        	} else if (status == "notmodified") {
        		$("#pEmployeeInformationFeedback").text("Warning: Unable to update Employee");
        	} else {
        		$("#pEmployeeInformationFeedback").text("Error: Unable to update Employee");
        	}
		} 
	});
}

function getPartnerList(id) {
    var sel = document.getElementById("selectPartner");
    sel.innerHTML = "";
    sel.disabled = true;
    document.getElementById("btnPartnerAdd").disabled = true;
    if (id > 0) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 &&  this.status == 200) {
                console.log(this.responseText);
                partners = JSON.parse(this.responseText);
                sel.disabled = false;
                document.getElementById("btnPartnerAdd").disabled = false;
                for (var i=0 ; i< partners.length ; i++) {
                	var opt = document.createElement("option");
                	opt.value = partners[i].id;
                	opt.textContent = partners[i].name;
                	sel.appendChild(opt);
                }
            }
        };
        xhttp.open("GET", "http://localhost:8081/api/employee/" + id + "/possible_partners");
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.send();
    }
}

function setSeeEmployee(name, partner, id, isDisabled) {
    getPartnerList(-1);
    $("#strEmployeeName").val(name);
    $("#strEmployeePartner").val(partner);
    $(".btnEmployeeInfo").prop("disabled", isDisabled);
    if (partner == "") {
        $("#btnPartnerRemove").prop("disabled", true);
    }
    $("#pEmployeeInformationFeedback").text("");
    $(".btnEmployeeInfo").off();
    if (id != -1) {
        $("#btnEmployeeUpdate").click(function() {
        	var name = $("#strEmployeeName").val();
        	var data = '{"id":' + id + ',"name":"' + name + '"}';
        	var param = "/update"
            changeEmployee(id, param, data);
        });
        $("#btnEmployeeRemove").click(function() {
        	$.ajax({
        		url: "http://localhost:8081/api/employee/" + id + "/del",
        		type: 'DELETE',
        		success: function(response, status) {
                	if (status == "success") {
                        getAllEmployees();
	                    setSeeEmployee("", "", -1, true);
                	}
        		}
        	});
        });
        $("#btnPartnerRemove").click(function() {
            var param = "/partner/remove";
            changeEmployee(id, param, '');
        });
        $("#btnPartnerAdd").click(function() {
            var idPartner = $("#selectPartner").val();
            var param = "/partner/add?id_partner=" + idPartner;
            changeEmployee(id, param, '');
        });
    }
}
