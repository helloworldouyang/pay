<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pay</title>
<script type="text/javascript" src="<%=basePath%>resource/js/jquery-1.8.0.min.js"></script>

<script type="text/javascript">
var rootPath="<%=basePath%>";

function weChatPay(){
	var body=$("#orderbody").val();
	$.ajax({
       url:rootPath+'wechat/pay',		
       data:{"body":encodeURI($("#orderbody").val()),"total_fee":$("#total").val()},
       success:function(data){
    	   $("#qrcode").attr('src',rootPath+"wechat/qr_codeImg?code_url="+data.codeUrl+"");
       }
	});
}


function aliPay(){
	
	$.ajax({
		url:rootPath+'ali/pay',
		data:{"total_price":$("#total").val(),"orderbody":encodeURI($("#orderbody").val())},
		success : function(data) {  
	          $('#returnAli').html(data.sHtmlText);  
	      },  
	      error : function(da){  
	      }  
	});
}


function pay(){
	var typ=$("#paytype").val();
	if(typ=="1"){
		weChatPay();
	}else{
		aliPay();
	}
	
}

</script>


</head>
<body>

  <form action="">
   <p>orderbody：</p><input type="text" id="orderbody" name="orderbody"/>
   <p>total：</p><input type="text" id="total" name="total"/>
   <p>type：</p>
   
   <select id="paytype">
     <option value="1">wechat</option>
     <option value="2" >ali</option>
   </select>
   <input onclick="pay()" type="button" id="button_pay" value="pay"/>
  </form>
  
  
  <div><img alt="" id="qrcode" src=""></div>
  
  <div id="returnAli"></div>
</body>
</html>