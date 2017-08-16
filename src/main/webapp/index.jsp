<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<html>
<script type="text/javascript" src="<%=basePath%>resource/js/jquery-1.8.0.min.js"></script>


<script type="text/javascript">
   var rootPath="<%=basePath%>";
   
   function  tset(){
	   $.ajax({
		  url:rootPath+'user/applicationtest',
	      success:function(data){
	    	  alert(JSON.stringify(data));
	      }  
	   });
	   
   }


    $(function(){
    	init();
    });

   function init(){
	   
	   $("#test").click(
			function(){
				 $.ajax({
					  url:rootPath+'user/applicationtest',
				      success:function(data){
				    	  alert(JSON.stringify(data));
				      }  
				   });
			}   
	   );
   }

</script>

<body>


 <form action="<%=basePath%>user/toAliPay">
  <input type="hidden" id="id" value="1" name="id">
  <input type="text" id="page" name="page" value="pay">
   <input type="submit" value="submit">
   
   
   <input type="button" id="test" value="test">
 </form>
 
<h3 id="id1"></h3>



</body>
</html>
