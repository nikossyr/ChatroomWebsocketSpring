<%--
    Document   : index
    Created on : Mar 20, 2020, 7:52:30 PM
    Author     : Nikos Syrios
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container pt-4">
    <h1 class="text-center">Insert your Credentials</h1>
    <div class="row justify-content-md-center">
        <div class="col-4">
            <h4 class="text-center pt-2">Login</h4>
            <div class="card">
                <div class="card-body">
                    <form action="login" method="post">
                        <div class="form-group">
                            <label for="loginUsername">Username</label>
                            <input type="text" class="form-control" id="loginUsername" name="username"
                                   aria-describedby="usernmaelHelp">
                        </div>
                        <div class="form-group">
                            <label for="loginPassword">Password</label>
                            <input type="password" class="form-control" id="loginPassword" name="password"
                                   placeholder="Password">
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form>
                </div>
            </div>
            <br>
            <a href="<c:url value='/preregister' />">Click here to register</a>
        </div>
    </div>
</div>
</body>
</html>
