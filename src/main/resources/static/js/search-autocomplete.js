$(function() {
	$("#employeeName").on("keyup", function() {
		var hostUrl = "http://localhost:8080/search_employee_api/search";
		var nameParam = $(this).val();
		$.ajax({
			url : hostUrl,
			type : 'POST',
			dataType : 'json',
			data : {
				name : nameParam
			},
			async : true
		}).done(function(data) {
			console.log(data);
			console.dir(JSON.stringify(data));
			$("#employeeName").autocomplete({
				source : data.employeeNameList
			});
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	});
});