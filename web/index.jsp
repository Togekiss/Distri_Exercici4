<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: angel
  Date: 10/01/2021
  Time: 18:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "network.server.WebSocket" %>
<%@ page import="java.util.ArrayList" %>
<html>
  <head>
    <title>My title</title>
  </head>
  <body>
  <h1>Live view of node status</h1>
  <p>La hora del servidor es: <%= new Date() %><%response.setIntHeader("Refresh", 1);%></p>


  <p>
    <%
      String statusA1 = (String) request.getAttribute("statusA1");
    %>
    <%=statusA1%>
  </p>
  <p>
    <%
      String statusA2 = (String) request.getAttribute("statusA2");
    %>
    <%=statusA2%>
  </p>
  <p>
    <%
      String statusA3 = (String) request.getAttribute("statusA3");
    %>
    <%=statusA3%>
  </p>
  <p>
    <%
      String statusB1 = (String) request.getAttribute("statusB1");
    %>
    <%=statusB1%>
  </p>
  <p>
    <%
      String statusB2 = (String) request.getAttribute("statusB2");
    %>
    <%=statusB2%>
  </p>
  <p>
    <%
      String statusC1 = (String) request.getAttribute("statusC1");
    %>
    <%=statusC1%>
  </p>
  <p>
    <%
      String statusC2 = (String) request.getAttribute("statusC2");
    %>
    <%=statusC2%>
  </p>
<%--
  <%
    ArrayList<String> status;
    //do {
      status = (ArrayList<String>) request.getAttribute("status");
      System.out.println("status size: " + status.size());

    //} while (webSocket == null);
      for (String s : status){
  %>
  <tr>
    <td><%=s%></td><br>
  </tr>
  <%
      };
  %>
--%>
  </body>
</html>
