// JavaScript Document

//键盘回车会执行.block类的点击事件
	$(document).keyup(function(event){
	  if(event.keyCode ==13){
		$("#login").trigger("click");
	  }
	});


	$("#login").click(function(){
		var name = $.trim($("[name = 'username']").val());
		if(name == ""){
			layer.tips('账号未填写!','[name = "username"]',{tips:[2,"#3595CC"]});
			$("[name = 'username']").focus();
			return false;
		}
		var password = $.trim($("[name = 'password']").val());
		if(password == ""){
			layer.tips('密码未填写!','[name = "password"]',{tips:[2,"#3595CC"]});
			$("[name = 'password']").focus();
			return false;
		}
		$.post("/user/loginregister",{
			username:name,
			password:password,
			cmCode:"login"
			},
			function(data) {
			if(data.status == "703"){
				location.href=data.data;
				return;
			}
			if(data.status != "200") {
				layer.msg(data.msg);
				$("[name='name']").focus();
			} else {
				layer.msg("登录成功,开始跳转。。。");
				//0.5秒后进行跳转 为了第一次注册的时候 留一点时间创建欢迎笔记
				setTimeout(function(){
					location.href=data.data;
					},500);
				
			}
			
		});
		
		
	});
	
	//点击忘记密码修改密码
	$("#updatepassword").click(function(){
		
		layer.open({
			  type: 2,
			  title: '',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['600px', '450px'],
			  content: "/modifypassword.html"
		}); 
		
	});
	
	
