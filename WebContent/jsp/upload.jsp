<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form name="Form2" action="/mvc4/upload/multipartUpload" method="post"
		enctype="multipart/form-data">
		<h1>使用spring mvc提供的类的方法上传文件</h1>
		<input type="file" name="file" multiple="multiple"> 
		<input type="submit" value="upload" />
	</form>
</body>
</html>