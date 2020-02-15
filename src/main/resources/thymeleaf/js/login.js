function Login(){
	var account = $('#username').val();
	var password = $('#password').val();
	//alert("acc:"+account+"ss");
	if(account == null || account == ""||password == null || password == ""){
		alert("请输入有效登录名或密码！");
		return;
	}
	  $.ajax({
		url:"/user/login",
		type:"post",
		async:"false",
		contentType:"application/json;charset=UTF-8",
		data:JSON.stringify({loginName:account,password:password}),
		dataType:'json',
		success : function(data){
			if(data.statusCode == 200){
				//这里采用href跳转，ajax只能获取数据。
				console.log(data.user);
				//var user = data.user;
				window.location.href="/user/toindex?token="+data.data;
			}else{
				alert("登录失败！\n"+"失败原因："+data.errorMessage);
			}
		},
		error : function(data){
			/*发送失败*/
		}
		}); 
};