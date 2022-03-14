
/*
function generateData(){
	var json = getJSON();
	generateTableBody(json);
	generatePagination(json.data);
}

function getJSON(){
	var result = $.ajax({
		url: "role",
		type: "GET",
		data: {current: window.current, size: window.size, keyword: window.keyword},
		dataType: "json",
		async: false
	});
	var json = result.responseJSON;
	if(result.status !== 200 || !json || json.result !== "SUCCESS"){
		var message = json ? json.message : "服务器出现异常，请稍后再试~";
		layer.msg(message);
		return null;
	}
	console.log(JSON.stringify(json));
	return json;
}

function generateTableBody(json){
	var page = json.data.rolePage;
	var tbody_role = $("#tbody-role");
	tbody_role.empty();
	var records = page.records;

	for(var i=0; i<records.length; i++){
		var role = records[i];

		$("<tr>\n" +
			"<td>" + role.id + "</td>\n" +
			"<td><input type='checkbox'/></td>\n" +
			"<td>" + role.name + "</td>\n" +
			"<td>" +
				"<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>\n" +
				"<button type='button' class='btn btn-primary btn-xs'><i class=' glyphicon glyphicon-pencil'></i></button>\n" +
				"<button type='button' class='btn btn-danger btn-xs'><i class=' glyphicon glyphicon-remove'></i></button>\n" +
			"</td>\n" +
		 "</tr>").appendTo(tbody_role);
	}

	$("<tr><td colspan='4' style='align-content: center'>" + json.message + "</td></tr>").appendTo(tbody_role);
}

function generatePagination(data){
	var page = data.rolePage;
	var pagination = $("#Pagination");
	if(page.total < 1){
		pagination.empty();
		return;
	}

	var opts = {
		num_edge_entries: 1,
		num_display_entries: 5,
		callback: paginationCallback,
		items_per_page: page.size,
		current_page: page.current - 1,
		prev_text: "上一页",
		next_text: "下一页"
	}
	pagination.pagination(page.total, opts);

}

function paginationCallback(index, jQuery){
	window.current = index + 1;
	generateData();
	return false;
}

*/
