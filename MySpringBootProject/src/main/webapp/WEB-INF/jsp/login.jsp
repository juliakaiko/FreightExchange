<%--
  Created by IntelliJ IDEA.
  User: Julia
  Date: 20.10.2024
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <link href="<c:url value='static/css/mylogin_style.css'/>" rel="stylesheet"></link>
</head>
<body>
<%-- Форма, которая поддерживается Spring!!!--%>
<%-- modelAttribute - из Controller copy-paste!!!--%>
<%-- class="form-horizontal" - какие css классы применять, оформление--%>
<div class="login-form">
    <h2><b>LOGIN</b></h2>
    <h4><font color=red>${wrongdata}</font></h4>
    <%--@elvariable id="user" type=""--%>
    <form:form method="POST" modelAttribute="user" class="form-horizontal">
        <div class="row justify-content-center">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="email">User email</label>
                <div class="col-md-7">
                    <form:input type="text" path="email" id="email" placeholder="Enter your Email" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="email" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row justify-content-center">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="password">Password</label>
                <div class="col-md-7">
                    <form:input type="text" path="password" id="password" placeholder="Enter your Password" class="form-control input-sm"/>
                    <div class="has-error">
                            <%--<form:password path="password" value="^76525bvHGq" showPassword="true" /> --%>
                        <form:errors path="password" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>
        <p align="center"><a href="/registration" >Registration</a></p>

        <div class="row justify-content-center">
            <br>
            <div class="form-actions floatRight">
                <input type="submit" value ="LogIn" class="btn btn-white btn-animate" />
            </div>
        </div>

        <!--
        <a href="/authorize" class="btn">Login</a>
        <a href="/registration" class="btn">Registration</a>
        -->

    </form:form>
</div>
</body>
</html>