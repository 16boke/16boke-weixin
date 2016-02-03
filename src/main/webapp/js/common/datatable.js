DataTable = Base.extend({
	datatable: null,
	pagination: null,
	pageNo: null,
	config: null,
	startIndex: 1,
	endIndex: 1,
	paginationId: null,
	constructor: function(datatable, pageNo, config) {
		this.paginationId = datatable.attr("id") + "_pagination";
		this.datatable = datatable;
		if(!pageNo && !(pageNo = $.cookie(this.datatable.attr("id") + "PageNo"))) {
			pageNo = 1;
		}
		this.pageNo = pageNo;
		this.config = config;
	},
	load: function() {
		var _this = this;
		$.cookie(_this.datatable.attr("id") + "PageNo", _this.pageNo, {  path: '/'  });
		$.ajax({
			type: "post",
			async: false,
			url: getContextPath() + _this.config.url + "?pageNo=" + _this.pageNo,
			dataType: "json",
			data: _this.config.data,
			success: function(data) {
				var page = eval(data);
				_this.decorate(page);
				if(_this.config.fnDrawCallback) {
					_this.config.fnDrawCallback();
				}
			},
			error: function() {
				alert("Loading Failed");
			}
		});
	},
	refresh: function(pageNo) {
		this.datatable.empty();
		this.pagination.empty();
		this.pageNo = pageNo;
		this.load();
		$("td").attr("style","min-width:60px");
	},
	computeIndex: function(page) {
		this.startIndex = 1;
		this.endIndex = page.totalPages;
		if ((page.pageNo - 2) > 0 && (page.pageNo + 2) <= page.totalPages) {
			this.startIndex = page.pageNo - 2;
			this.endIndex = page.pageNo + 2;
		} else if ((page.pageNo - 2) < 1) {
			this.startIndex = 1;
			if (page.totalPages <= 5) {
				this.endIndex = page.totalPages;
			} else {
				this.endIndex = 5;
			}
		} else if ((page.pageNo + 2) > page.totalPages) {
			this.endIndex = page.totalPages;
			if (page.totalPages > 5) {
				this.startIndex = page.totalPages - 4;
			} else {
				this.startIndex = 1;
			}
		}
	},
	decorate: function(page) {
		this.datatable.addClass("table table-striped");
		this.createTable(page);
		this.createPagination(page);
	},
	createTable:function(page){
		this.createTableTitle();
		this.createTableContent(page);
	},
	createTableTitle:function(){
		var titleRow = createTag("tr");
		titleRow.appendTo(this.datatable);
		for (var index = 0; index < this.config.param.title.length; index++) {
			var header = createTag("th");
			if(this.config.param.key[index] instanceof Array) {
				header.addClass("operator");
			}
			header.css("text-align","center");
			header.text(this.config.param.title[index]);
			header.appendTo(titleRow);
		}
	},
	createTableContent:function(page){
		var _this = this;
		for (var row = 0; row < page.result.length; row++) {
			var dataRow = createTag("tr");
			dataRow.attr("align","center");
			dataRow.appendTo(this.datatable);
			for (var index = 0; index < this.config.param.key.length; index++) {
				var column = createTag("td");
				column.attr("row", row);
				column.addClass(this.config.param.key[index]);
				if (this.config.param.key[index] instanceof Array) {
					column.addClass("operator");
					for (var event = 0; event < this.config.param.key[index].length; event++) {
						var button = createTag("span");
						button.addClass("glyphicon");
						if (this.config.param.key[index][event] == 'update' && this.config.fnUpdateData) {
							button.addClass("glyphicon-edit");
							button.attr("title", "update");
							button.click(function() {
								_this.config.fnUpdateData(page.result[$(this).parent().attr("row")]);
							});
						} else if (this.config.param.key[index][event] == 'delete' && this.config.fnRemoveData) {
							button.addClass("glyphicon-remove-circle");
							button.attr("title", "delete");
							button.click(function() {
								_this.config.fnRemoveData(page.result[$(this).parent().attr("row")]);
							});
						}
						button.appendTo(column);
					}
				} else {
					if (page.result[row].isAdjusted != null){
						column.attr("isadjusted", page.result[row].isAdjusted);
					}
					column.text(page.result[row][this.config.param.key[index]]);
				}
				column.appendTo(dataRow);
			}
		}
	},
	createPagination:function(page){
		var _this = this;
		var ul = createTag("div");
		ul.attr("id", this.paginationId);
		ul.addClass("pagination pull-right");
		ul.appendTo(this.datatable.parent());
		ul.append("<li><span>共" + page.totalCount + "条记录</span></li>");
		this.pagination = ul;
		var firstItem = $("<li " + (page.pageNo == 1 ? "class='disabled'": '') + "></li>");
		firstItem.appendTo(ul);
		var firstLink = $("<a href='javascript:void(0)'>首页</a>");
		firstLink.appendTo(firstItem);
		firstLink.click(function() {
			if (page.pageNo != 1) {
				_this.refresh(1);
			}
		});
		var previousItem = $("<li " + (page.pageNo == 1 ? "class='disabled'": '') + "></li>");
		previousItem.appendTo(ul);
		var previousLink = $("<a href='javascript:void(0)'>上一页</a>");
		previousLink.appendTo(previousItem);
		previousLink.click(function() {
			if (page.pageNo != 1) {
				_this.refresh(page.pageNo - 1);
			}
		});
		this.computeIndex(page);
		for (var index = this.startIndex; index <= this.endIndex; index++) {
			var indexItem = createTag("li");
			indexLink = $("<a href='javascript:void(0)'>" + index + "</a>");
			indexLink.appendTo(indexItem);
			indexLink.click(function() {
				_this.refresh($(this).text());
			});
			if (page.pageNo == index) {
				indexItem.addClass("active");
			} else {
				indexItem.removeClass("active");
			}
			indexItem.appendTo(ul);
		}
		var nextItem = $("<li " + (page.pageNo >= page.totalPages ? "class='disabled'": '') + "></li>");
		nextItem.appendTo(ul);
		var nextLink = $("<a href='javascript:void(0)'>下一页</a>");
		nextLink.appendTo(nextItem);
		nextLink.click(function() {
			if (page.pageNo < page.totalPages) {
				_this.refresh(page.pageNo + 1);
			}
		});
		var lastItem = $("<li " + (page.pageNo >= page.totalPages ? "class='disabled'": '') + "></li>");
		lastItem.appendTo(ul);
		var lastLink = $("<a href='javascript:void(0)'>末页</a>");
		lastLink.appendTo(lastItem);
		lastLink.click(function() {
			if (page.pageNo < page.totalPages) {
				_this.refresh(page.totalPages);
			}
		});
	}
});
(function($) {
	$.fn.extend({
		datatable: function(setting, pageNo) {
			var dataTable = new DataTable($(this), pageNo, setting);
			dataTable.load();
		}
	});
})(jQuery);