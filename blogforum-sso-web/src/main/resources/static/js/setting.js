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
			//如果不为空
			if(baseInfo.provinceLocation){
				$("#provinceLocation").val(baseInfo.provinceLocation);
				$("#cityLocation").empty();
				$("#cityLocation").append("<option value=" + baseInfo.cityLocation + ">" + baseInfo.cityLocation + "</option>");
			}
			if(baseInfo.birthProvince){
				$("#birthProvince").val(baseInfo.birthProvince);
				$("#birthCity").empty();
				$("#birthCity").append("<option value=" + baseInfo.birthCity + ">" + baseInfo.birthCity + "</option>");
			
			}
		}
	});
	
	
	
	//图片裁剪部分代码
	
    // 初始化图片上传插件
    var $image = $('#image');
    //图片文件名
    var imageName;
    $image.cropper({
        aspectRatio: '1',
        autoCropArea:0.8,
        preview: '.up-pre-after',
        
    });
	
    
    // 本地上传到网页图片
    var $inputImage = $('#inputImage');
    var URL = window.URL || window.webkitURL;
    var blobURL;

    if (URL) {
        $inputImage.change(function () {
            var files = this.files;
            var file;

            if (files && files.length) {
               file = files[0];

               if (/^image\/\w+$/.test(file.type)) {
                    blobURL = URL.createObjectURL(file);
                    $image.one('built.cropper', function () {
                        // Revoke when load complete
                       URL.revokeObjectURL(blobURL);
                    }).cropper('reset').cropper('replace', blobURL);
                    $inputImage.val('');
                } else {
                	layer.msg('请选择正确的图片!');
                }
            }

            imageName = file.name;
            // Amazi UI 上传文件显示代码
            var fileNames = '';
            $.each(this.files, function() {
                fileNames += '<span class="am-badge">' + this.name + '</span> ';
            });
            $('#file-list').html(fileNames);
        });
    } else {
        $inputImage.prop('disabled', true).parent().addClass('disabled');
    }
    
	
	

	
	
	
	
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
	
	
	
	//点击选择图片弹出div框
    $("#up-img-touch").click(function(){
		  $("#doc-modal-1").modal({width:'600px'});
    });
	
	
    //绑定图片上传七牛云事件
    $('#up-btn-ok').on('click',function(){
    	var img_src=$image.attr("src");
    	if(img_src==""){
    		layer.msg("没有选择上传的图片");
    		return false;
    	}
    	
		//上传图片时显示过度图标
		var loading = layer.load(1, {shade: [0.1,'#fff']}); //0.1透明度的白色背景
    	var url=$(this).attr("url");
    	//把image画到画布中 画布大小为260x300
    	var canvas=$("#image").cropper('getCroppedCanvas',{width: 260,height:300});
        //canvas转换成二进制blob 上传到七牛云
        canvas.toBlob(function(blob) {
            //创建forme
            var formData = new FormData();
		    formData.append('file', blob);
		    formData.append('key', currentTime() + "/" + imageName);
		    updateqiniu(formData,loading);
		    //关闭图片选择框
		    $("#doc-modal-1").modal("close");
        });
    });
	
	
	
	
//!click -----------------------------------------
	
	

//function-------------------------------------------
	
    
    //图片裁剪相关
    function rotateimgright() {
    	$("#image").cropper('rotate', 90);
    }


    function rotateimgleft() {
    	$("#image").cropper('rotate', -90);
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
	
//!common-----------------------------------