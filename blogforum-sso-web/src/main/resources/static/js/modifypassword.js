// JavaScript Document


//loading -------------------------------

	
	
	//--------------------------------------------------
	
	
	

	
//!loading ---------------------------------
	
	
	
//click ------------------------------------
	//手机注册码
	$("#iphoneverification").click(function(){
		
		//验证密码信息

		var flag = checkIphoneInfo();
		if(!flag){
			return false;
		}
		var iphone = $("#iphone").val();
		var val = $("#iphoneverification");
		$.post("/modify/sendIphoneVerification",{
			iphone:iphone
			},
			function(data) {
			if(data.status != "200") {
				layer.msg(data.msg);
				$("#iphonecheckpassword").focus();
			} else {
				layer.msg("验证码已经发送");
				$("[name='iphoneverificationCode']").focus();
				//设置验证码60秒才可以点击
				time(val);
			}
		});
	});
	
	//手机修改密码
	$("#updateiphonepassword").click(function(){
		var flag = checkIphoneInfo();
		if(!flag){
			return false;
		}
		var verfication = $("[name='iphoneverificationCode']").val();
		var password = $("#iphonepassword").val();
		var iphone = $("#iphone").val();
		
		$.post("/modify/updateIphonePassword",{
			iphone:iphone,
			verfication:verfication,
			password:password
			},
			function(data) {
			if(data.status != "200") {
				layer.msg(data.msg);
				$("#iphonecheckpassword").focus();
			} else {
				layer.msg("修改成功");
			}
		});
		
		
	});
	
	
	//邮箱注册码
	$("#emailverification").click(function(){
		
		//验证密码信息
		var flag = checkEmailInfo();
		if(!flag){
			return false;
		}
		var email = $("#email").val();
		var val = $("#emailverification");

		$.post("/modify/sendEmailVerification",{
			email:email
			},
			function(data) {
			if(data.status != "200") {
				layer.msg(data.msg);
				$("#emailcheckpassword").focus();
			} else {
				layer.msg("验证码已经发送");
				$("[name='emailverificationCode']").focus();
				//设置验证码60秒才可以点击
				time(val);
			}
		});

	});
	
	//邮箱修改密码
	$("#updateemailpassword").click(function(){
		var flag = checkEmailInfo();
		if(!flag){
			return false;
		}
		var verfication = $("[name='emailverificationCode']").val();
		var password = $("#emailpassword").val();
		var email = $("#email").val();
		
		$.post("/modify/updateEmailPassword",{
			email:email,
			verfication:verfication,
			password:password
			},
			function(data) {
			if(data.status != "200") {
				layer.msg(data.msg);
				$("#emailcheckpassword").focus();
			} else {
				layer.msg("修改成功");
			}
		});
		
		
	});
	
	
	
//!click -----------------------------------------
	
	

//function-------------------------------------------
	var wait=60;
	//设置验证码60秒才可以点击
	function time(o) {
	        if (wait == 0) {
	            o.html("获取验证码").removeAttr("disabled");
	            wait = 60;
	        } else {
	            o.attr("disabled", true);
	            o.html("等待" + wait + " 秒重新点击发送!");
	            wait--;
	            setTimeout(function() {
	                time(o)
	            },
	            1000)
	        }
	    }
	
	//效验手机页面信息
	function checkIphoneInfo(){
		var iphonepassword = $.trim($("#iphonepassword").val());
		if(!iphonepassword){
			layer.tips('密码未填写!','#iphonepassword',{tips:[2,"#3595CC"]});
			$("#iphonepassword").focus();
			return false;
		}
		var iphonecheckpassword = $.trim($("#iphonecheckpassword").val());
		if(!iphonecheckpassword){
			layer.tips('确认密码未填写!','#iphonecheckpassword',{tips:[2,"#3595CC"]});
			$("#iphonecheckpassword").focus();
			return false;
		}
		if(iphonecheckpassword != iphonepassword){
			layer.tips('两次密码填写不一致!','#iphonepassword',{tips:[2,"#3595CC"]});
			$("#iphonepassword").focus();
			return false;
		}
		
		
		var iphone = $.trim($("#iphone").val());
		if(iphone == ""){
			layer.tips('手机号未填写!','#iphone',{tips:[2,"#3595CC"]});
			$("#iphone").focus();
			return false;
		}
		//var isMobile=/^(?:13\d|15\d)\d{5}(\d{3}|\*{3})$/;   
		//var isPhone=/^((0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
		var isPhone=/^1[34578]\d{9}$/;
		if(!isPhone.test(iphone)){
			layer.tips('手机号填写不正确!','#iphone',{tips:[2,"#3595CC"]});
			$("#iphone").focus();
			return false;
		}
		return true;
		
		
	}
	
	
	//效验邮箱信息
	function checkEmailInfo(){
		
		var emailpassword = $.trim($("#emailpassword").val());
		if(!emailpassword){
			layer.tips('密码未填写!','#emailpassword',{tips:[2,"#3595CC"]});
			$("#emailpassword").focus();
			return false;
		}
		var emailcheckpassword = $.trim($("#emailcheckpassword").val());
		if(!emailcheckpassword){
			layer.tips('确认密码未填写!','#emailcheckpassword',{tips:[2,"#3595CC"]});
			$("#emailcheckpassword").focus();
			return false;
		}
		if(emailcheckpassword != emailpassword){
			layer.tips('两次密码填写不一致!','#emailpassword',{tips:[2,"#3595CC"]});
			$("#emailpassword").focus();
			return false;
		}
		var email = $("#email").val();
		if(!email){
			layer.tips('邮箱未填写!','#email',{tips:[2,"#3595CC"]});
			$("#email").focus();
			return false;
		}
		var reg = /\w+[@]{1}\w+[.]\w+/;
	    if(!reg.test(email)){
			layer.tips('请输入正确的邮箱格式!','#eamil',{tips:[2,"#3595CC"]});
			$("#email").focus();
			return false;
	    }
	    return true;
		
	}
	
	
    
//!function -----------------------------------------------

	
//common--------------------------
	
//!common-----------------------------------