$(document).ready( function () {
    var data = [
        [
        	id:"20170527150032890",
        	balance:"-0.10  CNY",
        	sort:"提现",
            ""
        ],
        [
        	id:"20170527145824609",
            balance:"-3.00  CNY",
            sort:"冻结",
            ""
        ];

    //DataTables 初始化
    $('#table_id_example').DataTable( {
        data: data,
        columns: [
            { data: 'id' },
            { data: 'balance' },
            { data: 'sort' }
        ],
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        }
    } );
} );
/*$(document).ready(function(){
	$("#example").dataTable({
		 "bProcessing" : true, //DataTables载入数据时，是否显示‘进度’提示
		 "aLengthMenu" : [5, 10, 15], //更改显示记录数选项
		 "sPaginationType" : "full_numbers", //详细分页组，可以支持直接跳转到某页
		 "bAutoWidth" : true, //是否自适应宽度
		 //"bJQueryUI" : true,
		 "oLanguage": { //国际化配置  
                "sProcessing" : "正在获取数据，请稍后...",    
                "sLengthMenu" : "显示 _MENU_ 条",    
                "sZeroRecords" : "没有您要搜索的内容",    
                "sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",    
                "sInfoEmpty" : "记录数为0",    
                "sInfoFiltered" : "(全部记录数 _MAX_ 条)",    
                "sInfoPostFix" : "",    
                "sSearch" : "搜索",    
                "sUrl" : "",    
                "oPaginate": {    
                    "sFirst" : "第一页",    
                    "sPrevious" : "上一页",    
                    "sNext" : "下一页",    
                    "sLast" : "最后一页"    
                }  
            },  
    });
});*/