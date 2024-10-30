<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 20.10.2024
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@ taglib prefix="c" uri="jakarta.tags.core" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <link href="<c:url value='css/table_style.css' />" rel="stylesheet"></link>
</head>

<body>
<h4><font color=red> ${novoting}</font> </h4>
<h2>My Profile</h2>
<table border="1" BGCOLOR="#FFFAFA"  cellspacing="0" cellpadding="5"
       var="orders" items="${orders}"  >
    <tr>
        <td><b>  ID  </b></td>
        <td><b>  Order Name </b></td>
        <td><b>  Start Point </b></td>
        <td><b>  Finish Point  </b></td>
        <td><b>  Cargo  </b></td>
        <td><b>  Freight </b></td>
        <td><b>  Valid </b></td>
        <td><b>  Take Order  </b></td>
    </tr>
   <c:forEach var="orders" items="${orders}">
    <tr>
        <td><b>${orders.id}</b></td>
        <td>${orders.orderName}</td>
        <td>${orders.startPoint}</td>
        <td>${orders.finishPoint}</td>
        <td>${orders.cargo}</td>
        <td>${orders.freight}</td>
        <td>${orders.valid}</td>
        <%--<td>${orders.voted}</td>--%>
        <td align="center">
            <form action="/orders/take_order/${orders.orderName}" method="get">
                <input type="submit" formaction="/orders/take_order/${orders.orderName}" value ="TAKE ORDER" class="btn btn-white btn-animate" />
            </form>
        </td>
    </tr>
    </c:forEach>
</table>
<br><br>
<%--<a href="/orders" class="btn btn-white btn-animate">FREE ORDERS</a>--%>
</body>
</html>
