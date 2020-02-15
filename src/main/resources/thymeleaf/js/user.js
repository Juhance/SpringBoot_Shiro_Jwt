new Vue({
    el:"#app",
    data:{
        user:{
        	uid:"",
            userName:"",
            loginName:"",
            password:"",
            salt:"",
            sex:"",
            email:"",
            headerUrl:"",
            phone:"",
            status:"",
            note:"",
            loginMethod:"",
            createTime:"",
            updateTime:"",
            list:[]
        },
        userRole:{
        	id:"",
        	userId:"",
        	roleId:""
        },
        userList:[],
        userRoleList:[],
        "pages": {
			"pageUsers": {
				"list": [],
				"total": "",
				"size": "",
				"pageSize": "",
				"pageNum": "",
				"searchCount": "",
				"pages": "",
				"nextPage": "",
				"prePage": "",
				"hasPreviousPage": "",
				"hasNextPage": "",
				"firstPage": "",
				"lastPage": ""
			},
			"pageUserRoles": {
				"list": [],
				"total": "",
				"size": "",
				"pageSize": "",
				"pageNum": "",
				"searchCount": "",
				"pages": "",
				"nextPage": "",
				"prePage": "",
				"hasPreviousPage": "",
				"hasNextPage": "",
				"firstPage": "",
				"lastPage": ""
			}
		},
        managerView:false,
        userRoleView:false
    },
    methods:{
    	//获取当前页，
    	//获取当前size
    	//通过ajax或者axios，请求后端数据
    	//渲染列表
        findPage:function(start){
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
/*            var select = $("#pageSize").val();*/
            var size = $("#pageSizeU").val();//绑定客户选择页数
            var flag = 0;
            if(size == undefined){
            	size = $("#pageSizeUR").val();
            	flag++;
            }
            //console.log(size);
            if( size < 5){
            	size = 5;
            }
        	var token = $("#token").val();//身份令牌,不安全,思考中
        	var page = _this.pages;
        	if(flag == 0){
        		page = _this.pages.pageUsers;
        	}else{
        		page = _this.pages.pageUserRoles;
        	}
        	var num = start;
        	//判断为空为toindex操作
        	if(start == null){
        		start= 1;
        	}
        	//分页实现 0首页   -1上一页   1下一页  2尾页 3刷新
        	if(start == 1){
        		//console.log(page.hasNextPage);
        		if(page.hasNextPage){
        			num = page.nextPage;
        			//console.log(num);
        		}else{
        			num = page.pageNum;
        		}
        	}
        	if(start == -1){
        		if(page.hasPreviousPage){
        			num = page.prePage;
        		}else{
        			num = page.pageNum;
        		}
        	}
        	if(start == 2){
        		num =page.lastPage;
        	}
        	if(start == 3){
        		num = page.pageNum;
        	}
            axios.get('/user/findPage',{
            	headers: {'Authorization': token},
            	params : {"start": num,"size":size}
            	})
                .then(function (response) {
                	if(response.data.statusCode == 200){
                		//console.log(_this.pages.pageUsers);
                		//console.log(response.data.data);
                		_this.pages.pageUsers = response.data.data.pageUser;//响应data list数据给userList赋值
                		_this.pages.pageUserRoles = response.data.data.pageUserRole;
	                    _this.userList = _this.pages.pageUsers.list;//将user列表复制给userlist，到页面渲染。
	                    _this.userRoleList = _this.pages.pageUserRoles.list;//将user列表复制给userlist，到页面渲染。
	                    //console.log(_this.userList);
	                    //console.log(_this.userRoleList);
	                    $("#pageSize").val(_this.pages.size);//刷新条数选择器，		
            		}else{
            			alert(response.data.errorMessage);
            		}
                })
                .catch(function (error) {
                    console.log(error);
                })
           
        },
        findById:function (userid) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            var token = $("#token").val();
            axios.get('/user/findById',{
            	headers: {'Authorization': token},
            	params : {"uid": userid}
            	})
                .then(function (response) {
                	if(response.data.statusCode == 200){
	                    _this.user = response.data.user;//响应数据给userList赋值
	                    //console.log(response.data);
	                    $("#myModal").modal("show");            			
            		}else{
            			alert(response.data.errorMessage);
            		}  
                })
                .catch(function (response) {
                    console.log("error:"+response.data.errorMessage);
                })
        },
        searchUserRole:function(e){
        	var _this = this;
        	var val = e.target.value;
        	var token = $("#token").val();
        	//alert(val);
        	if(val != "" && val != null && val != undefined){
        		axios.get("/user/findUserRoleByPoint",
        				{
        					headers:{'Authorization': token},
        					params : {"id": val}
        				}
        		)
        		.then(function (response) {
        			if(response.data.statusCode == 200){       			
        				_this.userRoleList = response.data.data;//响应数据给userList赋值
        				//console.log(response.data);
        				//$("#myModal").modal("show");//显示结果用一个模态框展示
        				//_this.findPage(3);//重新渲染
            		}else{
            			alert(response.data.errorMessage);
            		}  
                })
        	}
        },
        searchUser:function(e){
        	//alert(e.target.value); 注意空值
        	//查找user,支持多种方式查找，将结果用list收集，并显示
        	var _this = this;
        	var val = e.target.value;
        	var token = $("#token").val();
        	//alert(val);
        	if(val != "" && val != null && val != undefined){
        		axios.get("/user/findUserByPoint",
        				{
        					headers:{'Authorization': token},
        					params : {"id": val}
        				}
        		)
        		.then(function (response) {
        			if(response.data.statusCode == 200){       			
        				_this.userList = response.data.data;//响应数据给userList赋值
        				//console.log(response.data);
        				//$("#myModal").modal("show");//显示结果用一个模态框展示
        				//_this.findPage(3);//重新渲染
            		}else{
            			alert(response.data.errorMessage);
            		}  
                })
        	}
        },
        update:function (user) {//post请求
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            var token = $("#token").val();
            axios.post("/user/updateUser",_this.user,
            	{
            		headers:{"Authorization": token}
            	}
            )
            .then(function (response) {
            	//bug 应独立出来，说明刷新，而不改变页
            	if(response.data.statusCode == 200){
        			_this.findPage(3);        			
        		}else{
        			alert(response.data.errorMessage);
        		}   
            })
            .catch(function (error) {
                console.log(error);
            });
        },
        deleteById:function(){
        	var _this = this;
        	var arr = new Array();
        	 $.each($(":checkbox[name='ids']"),function(){
                 console.log("选中了："+this.id);
        		 arr.unshift(this.id);
             });
        	 //console.log(list);
        	 //var data = JSON.stringify(arr);
        	 var token = $("#token").val();
        	 //执行删除
        	 if(_this.managerView == true){
        		 
        		 axios.post("/user/deleteUser",arr,
        				 {
        			 headers:{"Authorization": token},
        				 }
        		 ).then(function (response) {
        			 //bug 应独立出来，说明刷新，而不改变页
        			 if(response.data.statusCode == 200){
        				 _this.findPage(3);        			
        				 _this.allCheckd(false) //这里应该将全选关闭
        			 }else{
        				 alert(response.data.errorMessage);
        			 }
        		 })
        		 .catch(function (error) {
        			 console.log(error);
        		 });
        	 }else{
        		 axios.post("/user/deleteUserRole",arr,
        				 {
        			 headers:{"Authorization": token},
        				 }
        		 ).then(function (response) {
        			 //bug 应独立出来，说明刷新，而不改变页
        			 if(response.data.statusCode == 200){
        				 _this.findPage(3);        			
        				 _this.allCheckd(false) //这里应该将全选关闭
        			 }else{
        				 alert(response.data.errorMessage);
        			 }
        		 })
        		 .catch(function (error) {
        			 console.log(error);
        		 });
        	 }
        },
        createUserRole:function(){
        	var _this = this;
        	var token = $("#token").val();
        	var userRole = _this.userRole;
        	userRole.userId = $("#userId").val();
        	userRole.roleId = $("#roleId").val();
        	
        	axios.post("/user/createUserRole",userRole,
        			{
	            		headers:{"Authorization": token},
	            		contentType:"application/json"
            		
        			}).then(function (response) {
                 	//bug 应独立出来，说明刷新，而不改变页
            		if(response.data.statusCode == 200){
            			_this.findPage(3);        			
            		}else{
            			alert(response.data.errorMessage);
            		}        		
                })
                .catch(function (error) {
        	});
        },
        createUser:function(){
        	var _this = this;
        	var token = $("#token").val();
        	var user = _this.user;
        	 user.userName = $("#userName").val();
        	 user.loginName = $("#loginName").val();
        	 user.password = $("#password").val();
        	 user.headerUrl = $("#headerUrl").val();
        	 user.sex = $("#sex").val();
        	 //user.status = $("#status").val();
        	 user.note = $("#note").val();
        	 user.email = $("#email").val();
        	 user.phone = $("#phone").val();
        	 //console.log(user);
        	axios.post("/user/createUser",user,
        		{
        		headers:{"Authorization": token},
        		contentType:"application/json"
        	}).then(function (response) {
             	//bug 应独立出来，说明刷新，而不改变页
        		if(response.data.statusCode == 200){
        			_this.findPage(3);        			
        		}else{
        			alert(response.data.errorMessage);
        		}        		
            })
            .catch(function (error) {
            });
        },
        allCheckd:function(e){
        	//console.log("开始!");
        	//console.log(e.target.checked); 选中为true，否则false
        	//console.log(e);
        	$(":checkbox[id='selall']").prop("checked", e); 
        	if(e){
        		 $(":checkbox[name='ids']").prop("checked", e);
        		 $.each($(":checkbox[name='ids']"),function(i){
        			 var va = $(this).parent().next().html();
        			 this.id=va;	 
        		 });        		 
        	 }else{
        		 $(":checkbox[name='ids']").prop("checked", e);
        		 $.each($(":checkbox[name='ids']"),function(i){
        			 //var va = $(this).parent().next().html();
        			 this.id="ids"; 
        		 });        		 
        	 }
        	 //需要将所有id绑定为对应的值
        },
        operate:function(event,id){
        	//我们这里把用户对应id帮到checkbox上
        	var ids= event.target.id;
        	console.log("id"+ids);
        	if(ids == id){
        		event.target.id = "ids";        		
        	}else{
        		event.target.id = id;
        	}
        },
        clearModel:function(){
        	var _this = this;
            	
        	if(_this.managerView){
        		
        		_this.user.uid="";
            	_this.user.userName="";
            	_this.user.loginName="";
            	_this.user.password="";
            	_this.user.salt="";
            	_this.user.sex="";
            	_this.user.email="";
            	_this.user.headerUrl="";
            	_this.user.status="";
            	_this.user.note="";
        	}else{
        		_this.userRole.id="";
        		_this.userRole.userId="",
        		_this.userRole.roleId="";
        	}
        },
        opModel:function(){
        	//1.清除模态框；
        	this.clearModel();

        	$("#myModalCreate").modal("show"); 
        },
        changeView:function(e){
        	if(e.target.id == "managerView"){
        		//alert(this.userRoleView)
        		if(this.userRoleView == true){
        			this.userRoleView = !this.userRoleView;
        		}
        		this.managerView = !this.managerView;
        	}
        	else if(e.target.id == "userRoleView"){
        		
        		if(this.managerView == true){
        			this.managerView = false;
        		}
        		this.userRoleView = !this.userRoleView;
        	}
        	$("#search").val("");
        }
    },
    created:function() {//当我们页面加载的时候触发请求，查询所有
    	this.findPage();
    }
});