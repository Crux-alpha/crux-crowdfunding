
/**
 * 更新表单项的状态
 * @param state 状态: boolean
 * @param message 提示消息
 * @param input input标签
 */
function updateFormGroup(state, message, input){
	var formGroup = input.closest(".form-group");
	if(!state){
		formGroup.removeClass("has-success");
		formGroup.addClass("has-error");
		formGroup.children("p").text(message);
	}else{
		formGroup.removeClass("has-error");
		formGroup.addClass("has-success");
		formGroup.children("p").empty();
	}
}

/**
 * 检查表单数据
 * @param form 表单标签
 * @returns {boolean} 如果全部成功，返回true
 */
function checkFormData(form){
	var formGroup = form.children("div[class*=form-group]");
	formGroup.find("input").blur();
	var hasSuccess = form.children("div[class*=has-success]");

	// 如果表单项与成功项数量不一致，阻止提交
	return formGroup.length === hasSuccess.length;
}

/**
 * 通过指定url获取一个实体json
 * @param id 实体id
 * @param url 请求地址
 */
function getOne(id, url){
	return $.ajax({
		url: url + "/" + id,
		type: "GET",
		dataType: "json",
		async: false
	});
}

function getList(ids, url){
	return $.ajax({
		url: url,
		type: "GET",
		data: {ids: ids},
		dataType: "json",
		async: false
	});
}

function defaultErrorCallback(response){
	layer.msg(response.responseJSON.message);
}