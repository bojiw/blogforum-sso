// JavaScript Document


//loading -------------------------------
	//请求用户基本信息 赋值到个人设置中
	$.get("/setting/getBaseInfo",function(data){
		if(data.status != "200") {
			layer.msg(data.msg);
		} else {
			var baseInfo = data.data;
			$("#username").val(baseInfo.username);
			$("#email").val(baseInfo.email);
			$("#iphone").val(baseInfo.iphone);
			$("#remarks").val(baseInfo.remarks);
			$(".portraitimage").attr("src",baseInfo.image);
			jQuery.each(baseInfo.citys,function(i,item){
				$("#provinceLocation").append("<option value=" + item.name + " cityId=" + item.id + ">" + item.name + "</option>");
				$("#birthProvince").append("<option value=" + item.name + " cityId=" + item.id + ">" + item.name + "</option>");
			});
			if(baseInfo.provinceLocation != ""){
				$("#provinceLocation").val(baseInfo.provinceLocation);
				$("#cityLocation").empty();
				$("#cityLocation").append("<option value=" + baseInfo.cityLocation + ">" + baseInfo.cityLocation + "</option>");
			}
			if(baseInfo.birthProvince != ""){
				$("#birthProvince").val(baseInfo.birthProvince);
				$("#birthCity").empty();
				$("#birthCity").append("<option value=" + baseInfo.birthCity + ">" + baseInfo.birthCity + "</option>");
			
			}
		}
	});
	
	
	
	//图片裁剪部分代码
	
	//做个下简易的验证  大小 格式 
	$('#avatarInput').on('change', function(e) {
		var filemaxsize = 1024 * 5;//5M
		var target = $(e.target);
		var Size = target[0].files[0].size / 1024;
		if(Size > filemaxsize) {
			alert('图片过大，请重新选择!');
			$(".avatar-wrapper").childre().remove;
			return false;
		}
		if(!this.files[0].type.match(/image.*/)) {
			alert('请选择正确的图片!')
		} else {
			var filename = document.querySelector("#avatar-name");
			var texts = document.querySelector("#avatarInput").value;
			var teststr = texts; 
			testend = teststr.match(/[^\\]+\.[^\(]+/i); //直接完整文件名的
			filename.innerHTML = testend;
		}
	
	});

	$(".avatar-save").on("click", function() {
		var img_lg = document.getElementById('imageHead');
		
		//把img放到画布中使用html2canvas会导致位置偏移和模糊 下面代码canvas: canvas2,配置这个就会出问题 目前还没找到对应办法
//	    var width = img_lg.offsetWidth; //获取dom 宽度
//	    var height = img_lg.offsetHeight; //获取dom 高度
//	    var canvas2 = document.createElement("canvas");
		// 获取元素相对于视窗的偏移量
		//var rect = $(img_lg).get(0).getBoundingClientRect(); 
	    //canvas2.width = width * 2;
	    //canvas2.height = height * 2;
	    //canvas2.style.width = width + "px";
	    //canvas2.style.height = height + "px";
	    //var context = canvas2.getContext("2d");
	    //然后将画布缩放，将图像放大两倍画到画布上
	    //context.scale(2,2);
	    // 设置context位置, 值为相对于视窗的偏移量的负值, 实现图片复位
	    //context.translate(-rect.left,-rect.top);
//	    html2canvas(img_lg, {
//            allowTaint: true,
//            taintTest: false,
//	        canvas: canvas2,
//	        onrendered: function(canvas) {
//				canvas.id = "mycanvas";
//				//生成base64图片数据
//				//var dataUrl = canvas.toDataURL("image/jpeg");
//				//var formData = new FormData();
//				//var file = convertBase64UrlToBlob(dataUrl);
//				//formData.append('file', file);
//				//formData.append('key', currentTime() + $("#avatar-name").html());
//				//updateqiniu(formData,loading);
//				//上传图片时显示过度图标
//	            var loading = layer.load(1, {shade: [0.1,'#fff']}); //0.1透明度的白色背景
//
//	            //canvas转换成二进制blob 上传到七牛云
//                canvas.toBlob(function(blob) {
//                    //创建forme
//                    var formData = new FormData();
//    			    formData.append('file', blob);
//    			    formData.append('key', currentTime() + $("#avatar-name").html());
//    			    updateqiniu(formData,loading);
//                });
//	        }
//	    });
		
		// 截图小的显示框内的内容
		html2canvas(img_lg, {
			allowTaint: true,
			taintTest: false,
			onrendered: function(canvas) {
				canvas.id = "mycanvas";
				//生成base64图片数据
				//var dataUrl = canvas.toDataURL("image/jpeg");
				//var formData = new FormData();
				//var file = convertBase64UrlToBlob(dataUrl);
				//formData.append('file', file);
				//formData.append('key', currentTime() + $("#avatar-name").html());
				//updateqiniu(formData,loading);
				//上传图片时显示过度图标
	            var loading = layer.load(1, {shade: [0.1,'#fff']}); //0.1透明度的白色背景

	            //canvas转换成二进制blob 上传到七牛云
                canvas.toBlob(function(blob) {
                    //创建forme
                    var formData = new FormData();
    			    formData.append('file', blob);
    			    formData.append('key', currentTime() + $("#avatar-name").html());
    			    updateqiniu(formData,loading);
                });
	            
			}
		});
	})
	
	/**  
	 * 将以base64的图片url数据转换为Blob  
	 * @param urlData  
	 *            用url方式表示的base64图片数据  
	 */  
	function convertBase64UrlToBlob(urlData){  
	      
	    var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte  
	      
	    //处理异常,将ascii码小于0的转换为大于0  
	    var ab = new ArrayBuffer(bytes.length);  
	    var ia = new Uint8Array(ab);  
	    for (var i = 0; i < bytes.length; i++) {  
	        ia[i] = bytes.charCodeAt(i);  
	    }  
	  
	    return new Blob( [ab] , {type : 'image/jpeg'});  
	}  
	
	
	//上传图片到七牛云
	function updateqiniu(formData,loading){
		console.info("上传七牛云");
	    $.get('/qiniuyun/getUpToken',null,function(data){
		   	 formData.append('token',data.data);
		   	 $.ajax({
		   	     url: 'http://upload.qiniu.com/',
		   	     type: 'POST',
		   	     cache: false,
		   	     data: formData,
		   	     processData: false,
		   	     contentType: false
		   	     
		   	 }).done(function(res) {
		   		 //关闭等待图标
		   		layer.close(loading);
		   		$(".portraitimage").attr("src","http://ouqhxmwfh.bkt.clouddn.com/"+res.key);
		   	 }).fail(function(res) {
		   		layer.close(loading);
		   		layer.msg("上传失败请重试！！！");
		   	 });
		    },null,null);   
	};
	
	
	
	
	//--------------------------------------------------
	
	
	

	
//!loading ---------------------------------
	
	
	
//click ------------------------------------
	
	$('#provinceLocation').change(function () {
		//获取当前被选中的所在省份id
		var cityId = $("#provinceLocation option:selected").attr("cityId");
		if(cityId == null){
			$("#cityLocation").empty();
			$("#cityLocation").append("<option selected>--请选择--</option>");
			return;
		}
		//获取所在城市
		$.get("/setting/getCitys?cityParentId=" + cityId,function(data){
			if(data.status != "200") {
				layer.msg(data.msg);
			} else {
				var citys = data.data;
				$("#cityLocation").empty();
				jQuery.each(citys,function(i,item){
					$("#cityLocation").append("<option value=" + item.name + " cityId=" + item.id + ">" + item.name + "</option>");
				});
				
			}
		});
		
	})
	
	$('#birthProvince').change(function () {
		//获取当前被选中的所在省份id
		var cityId = $("#birthProvince option:selected").attr("cityId");
		if(cityId == null){
			$("#birthCity").empty();
			$("#birthCity").append("<option selected>--请选择--</option>");
			return;
		}
		//获取所在城市
		$.get("/setting/getCitys?cityParentId=" + cityId,function(data){
			if(data.status != "200") {
				layer.msg(data.msg);
			} else {
				var citys = data.data;
				$("#birthCity").empty();
				jQuery.each(citys,function(i,item){
					$("#birthCity").append("<option value=" + item.name + " cityId=" + item.id + ">" + item.name + "</option>");
				});
				
			}
		});
		
	})
	
	
	//更新基本用户信息
	$("#settingBaseInfo").click(function(){
		
		var username = $.trim($("#username").val());
		if(username == ""){
			layer.tips('用户名不能为空!','[name = "username"]',{tips:[2,"#3595CC"]});
			$("#username").focus();
			return;
		}
		
		var iphone = $("#iphone").val();
		if(iphone != ""){
			var isPhone=/^1[34578]\d{9}$/;
			if(!isPhone.test(iphone)){
				layer.tips('手机号填写不正确!',$("#iphone"),{tips:[2,"#3595CC"]});
				$("#iphone").focus();
				return;
			}
		}
		var email = $("#email").val();
		if(email != ""){
			var reg = /\w+[@]{1}\w+[.]\w+/;
		    if(!reg.test(email)){
				layer.tips('请输入正确的邮箱格式!',$("#email"),{tips:[2,"#3595CC"]});
				$("#email").focus();
				return;
		    }
		}

		
		var provinceLocation = $("#provinceLocation").val();
		if(provinceLocation == "--请选择--"){
			provinceLocation = "";
		}
		var cityLocation = $("#cityLocation").val();
		if(cityLocation == "--请选择--"){
			cityLocation = "";
		}
		var birthProvince = $("#birthProvince").val();
		if(birthProvince == "--请选择--"){
			birthProvince = "";
		}
		var birthCity = $("#birthCity").val();
		if(birthCity == "--请选择--"){
			birthCity = "";
		}
		
		$.post("/setting/updateBaseInfo",{
			username:username,
			email:email,
			iphone:iphone,
			provinceLocation:provinceLocation,
			cityLocation:cityLocation,
			birthProvince:birthProvince,
			birthCity:birthCity,
			remarks:$("#remarks").val(),
			image:$(".portraitimage").attr("src"),
			
			},
			function(data) {
			if(data.status != "200") {
				layer.msg(data.msg);
				$("#username").focus();
			} else {
				layer.msg("修改成功。。。");
			}
		});
		
	});
	
//!click -----------------------------------------
	
	

//function-------------------------------------------
	
	
	
//!function -----------------------------------------------

	
//common--------------------------
	
		//获取时间
	function currentTime(){
		var d = new Date(),
		 str = '';
		 str += d.getFullYear();
		 str  += d.getMonth() + 1;
		 var a = str + "/";
		 str  += d.getDate();
		 str += d.getHours(); 
		 str  += d.getMinutes(); 
		str+= d.getSeconds(); 
		return a + str;
	}
	
//!common-----------------------------------