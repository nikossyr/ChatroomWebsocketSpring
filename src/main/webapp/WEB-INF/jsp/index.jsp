<%--
  Created by IntelliJ IDEA.
  User: Nikos.Syrios
  Date: 31/03/2020
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nickname</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css"
          rel="stylesheet">
</head>
<body>
<div class="container">
    <form class="col-xl-5 col-lg-6 col-md-8 col-sm-10 mx-auto text-center form p-4 pt-5"
          action="${pageContext.request.contextPath}/addNickname" method="post">
        <div class="form-group">
            <label for="nicknameInput">Please enter your nickname:</label>
            <input type="text" class="form-control" id="nicknameInput" name="nicknameInput" aria-describedby="nickname"
                   placeholder="Enter a nickname">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>
</html>
