/**
 * 生成树形结构
 */
function generateTree(){
	// 获取tree设置项
	var settings = window.treeSettings;

	// 发送请求，获取根节点
	$.ajax({
		url: "menu/whole_tree",
		dataType: "json",
		success: function(json){
			var root = json.data.root;
			var nodes = [root];
			$.fn.zTree.init($("#treeDemo"), settings, nodes);
		},
		error: function(response){
			layer.msg(response.responseJSON.error);
		}
	})
}

/**
 * 自定义节点样式
 * @param treeId 树形结构的ul标签的id
 * @param treeNode 当前节点json数据
 */
function addDiyDomCallback(treeId, treeNode){
	/* ------------设置图标样式----------- */
	// 找到treeNode的span标签的前一个span标签
	var spanId = treeNode.tId + "_ico";
	// 将它的class设置为treeNode已保存的icon信息
	$("#" + spanId).attr("class", treeNode.icon);
}

/**
 * 鼠标移入节点时添加按钮组用以显示
 * @param treeId 树形结构的ul标签的id
 * @param treeNode 当前节点json数据
 */
function addHoverDomCallback(treeId, treeNode){
	// 按钮位置位于节点超链接标签后。找到超链接标签id
	var aId = treeNode.tId + "_a";
	// 创建按钮组id
	var btnId = treeNode.tId + "_btn_group";
	// 如果已经有按钮组，不再添加
	if($("#" + btnId).length > 0) return;

	// 创建初始的按钮组
	var groupBtn = $("<span id='"+ btnId +"'></span>");
	// 创建编辑按钮、新增按钮、删除按钮
	var editBtn = $("<a class='btn btn-info dropdown-toggle btn-xs btn-node-edit' style='margin-left:10px;padding-top:0;' href='#' title='编辑节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg'></i></a>");
	var addBtn = $("<a class='btn btn-info dropdown-toggle btn-xs btn-node-add' style='margin-left:10px;padding-top:0;' href='#' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg'></i></a>");
	var removeBtn = $("<a class='btn btn-info dropdown-toggle btn-xs btn-node-remove' style='margin-left:10px;padding-top:0;' href='#' title='删除节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg'></i></a>");

	// 节点有一个level属性，表示当前节点的级别。0为根节点
	var level = treeNode.level;
	// 根据节点级别判断该节点可以执行什么操作。
	switch(level){
		// 分支节点可以添加子节点、修改节点；如果没有子节点，可以删除分支节点
		case 1: {
			editBtn.appendTo(groupBtn);
			if(!treeNode.children.length) removeBtn.appendTo(groupBtn);
		}
		// 根节点只能添加子节点。分支节点也需要添加子节点，因此可以case穿透
		case 0: addBtn.prependTo(groupBtn);break;
		// 叶子节点可以修改节点、删除节点
		case 2: editBtn.add(removeBtn).appendTo(groupBtn);break;
	}

	//将按钮组附加到该标签后边
	groupBtn.insertAfter($("#" + aId));
}

/**
 * 鼠标移出节点时删除按钮组。
 */
function removeHoverDomCallback(treeId, treeNode){
	var btnId = treeNode.tId + "_btn_group";
	$("#" + btnId).remove();
}