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
              var employeeTable = $("#tblEmployees");
        	  var row = $("<tr></tr>");
        	  var col = [];
        	  for (j=0;j<3;j++) col.push($("<th></th>"));
        	  col[0].text("ID");
        	  col[1].text("Name");
        	  col[2].text("Partner");
        	  col[0].prop("id", "hdrID")
        	  col[1].prop("id", "hdrName")
        	  col[2].prop("id", "hdrPartner")
        	  row.append(col[0], col[1], col[2]);
              employeeTable.empty();
        	  employeeTable.append(row);
              for (var i=0 ; i< employeeList.length ; i++) {
            	  row = $("<tr></tr>");
            	  for (j=0;j<3;j++) col[j] = $("<td></td>");
            	  col[0].text(employeeList[i].id);
            	  col[1].text(employeeList[i].name);
            	  if (employeeList[i].partner != null) {
            		  col[2].text(employeeList[i].partner.name);  
            	  }
            	  row.prop("class","rowEmployee");
            	  row.click(employeeList[i].id, seeEmployee);
            	  row.append(col[0], col[1], col[2]);
            	  employeeTable.append(row);
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

function seeEmployee(event) {
	var url = "http://localhost:8081/api/employee/find/" + event.data;
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
    var sel = $("#selectPartner");
    sel.empty();
    sel.prop("disabled", true);
    $("#btnPartnerAdd").prop("disabled", true);
    if (id > 0) {
    	var url = "http://localhost:8081/api/employee/" + id + "/possible_partners";
    	$.get(url, function(possiblePartners, status) {
    		if(status == "success") {
    			console.log(possiblePartners);
    		    sel.prop("disabled", false);
    		    $("#btnPartnerAdd").prop("disabled", false);
                for (var i=0 ; i< possiblePartners.length ; i++) {
                	var opt = $("<option></option>").val(possiblePartners[i].id);
                	opt.text(possiblePartners[i].name);
                	sel.append(opt);
                }
    		}
    	});
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
