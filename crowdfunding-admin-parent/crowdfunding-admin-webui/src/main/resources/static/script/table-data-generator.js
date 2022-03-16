/**
 * 只需要调用该函数即可刷新页面数据<br/>
 * 前提是：<ul>
 * 	<li><tbody>的id为tbody-data</li>
 * 	<li>window中初始化属性：current(页码)、size(分页大小)、keyword(查询关键词)、pageURL(请求地址)</li>
 * 	<li><thead>中的"th"id为data-属性字段名</li>
 * 	<li>需要使用<div id="Pagination" class="pagination">来生成分页条</li>
 * 	</ul>
 */
function generateData(){
	var json = getJSON();
	if(json != null){
		generateTableBody(json);
		generatePagination(json.data);
	}
}

/**
 * 发送get请求，获取分页信息
 * @returns {null|*} 如果响应成功，返回得到的json数据，否则没有数据
 */
function getJSON(){
	var result = $.ajax({
		url: window.pageURL,
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
	return json;
}

/**
 * 生成tbody展示数据
 * @param json 通过{@link getJSON}得到的json数据
 */
function generateTableBody(json){
	var page = json.data.page;
	var tbody = $("#tbody-data");
	tbody.empty();
	// 取消全选框
	$("#th-check-all").children().prop("checked", false);
	var records = page.records;
	var thead_tr = tbody.prev().children();
	var thead_ths = thead_tr.children("th");
	// 将th中id为data-"属性名"的属性名从attr中提取，用来收集这些属性的键值对
	var ths$id = thead_tr.children("th[id^=data-]");

	for(var i=0; i<records.length; i++){
		var entity = records[i];

		// 根据thead中的数据生成tbody的tr
		// 获取所有参数
		var str = JSON.stringify(entity).replaceAll("\"", "");
		str = str.substring(1, str.length - 1);
		// json中得到的所有属性
		var attr = str.split(",");

		// 用以展示数据的属性
		var params = [];

		for(var a=0; a<ths$id.length; a++){
			var param = ths$id.eq(a).attr("id").substring(5);
			for(var b=0; b<attr.length; b++){
				if(attr[b].split(":")[0] === param){
					params.push(attr[b]);
					break;
				}
			}
		}

		// 如果参数个数和tr数量不一致，停止解析
		if(!i){
			if(params.length !== ths$id.length){
				layer.msg("解析数据异常！请稍后再试~");
				return;
			}
		}

		// 根据thead中的内容生成一个tr
		var tbody_tr = $("<tr></tr>");
		for(var m=0; m<thead_ths.length-1; m++){
			var td_gen = $("<td></td>");
			td_gen.html(thead_ths.eq(m).html());
			td_gen.appendTo(tbody_tr);
		}
		$("<td>" +
			"<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>\n" +
			"<button type='button' class='btn btn-primary btn-xs'><i class=' glyphicon glyphicon-pencil'></i></button>\n" +
			"<button type='button' class='btn btn-danger btn-xs'><i class=' glyphicon glyphicon-remove'></i></button>\n" +
			"</td>").appendTo(tbody_tr);

		// 将数据显示到td中
		var tds = tbody_tr.children().not(":eq(1)");
		for(var n=0; n<params.length; n++){
			var value = params[n].split(":")[1];
			var td = tds.eq(n);
			td.text(value);
		}

		// 插入到tbody中
		tbody_tr.appendTo(tbody);

	}

	// 生成消息格
	var msg_td = $("<tr><td style='text-align: center'>" + json.message + "</td></tr>");
	msg_td.children().attr("colspan", thead_tr.children("th").length);
	msg_td.appendTo(tbody);
}

/**
 * 生成分页条
 * @param data json中的响应数据
 */
function generatePagination(data){
	var page = data.page;
	window.total = page.total;
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

/**
 * pagination的回调函数
 * @param index 页码索引
 * @param jQuery
 */
function paginationCallback(index, jQuery){
	window.current = index + 1;
	generateData();
	return false;
}