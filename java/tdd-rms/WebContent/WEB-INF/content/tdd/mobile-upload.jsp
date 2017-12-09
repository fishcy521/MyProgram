<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
	<title>桃多多接口管理</title>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/nogrid.jsp" %>
 <style type="text/css">
   body{margin: 0px; background:#f2f2f0;}
   p{margin:0px;}
   .title{color:#FFFF00; background:#000000; text-align:center; font-size:24px; line-height:50px; font-weight:bold;}
   .file{position:absolute; width:100%; font-size:90px;}
   .filebtn{display:block; position:relative; height:110px; color:#FFFFFF; background:#06980e; font-size:48px; line-height:110px; text-align:center; cursor:pointer; border: 3px solid #cccccc;}
   .filebtn:hover{background:#04bc0d;}
   .showimg{margin:10px auto 10px auto; text-align:center;}
 </style>

  <script type="text/javascript">
      window.onload = function () {
          document.getElementById('img').onchange = function () {

              var img = event.target.files[0];

              if (!img) {
            	  alert("haha");
                  return;
              }

           
              var reader = new FileReader();
              reader.readAsDataURL(img);
              // alert(reader);
              reader.onload = function (e) { // reader onload start
                  // 上传
                  $.post("${ctx_tdd}/upload!uploadImage.action", { "filename": img.name, img: e.target.result }, function (ret) {
                      if (ret.img != '') {
                          alert('success');
                          $('#showimg').html('<img src="' + ret.img + '">');
                      } else {
                          alert('upload fail');
                      }
                  }, 'json');
              } // reader onload end
          }
      }
  </script>

 </head>

 <body>
  <p><input type="file" class="file" name="img" id="img"><label class="filebtn" for="img" title="JPG,GIF,PNG">请选择图片</label></p>
  <p class="showimg" id="showimg"></p>
 </body>  
</html>