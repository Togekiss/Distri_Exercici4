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
      if (statusA1 == null) statusA1 = "Node A1 did no operations.";
    %>
    <%=statusA1%>
  </p>
  <p>
    <%
      String statusA2 = (String) request.getAttribute("statusA2");
        if (statusA2 == null) statusA2 = "Node A2 did no operations.";
    %>
    <%=statusA2%>
  </p>
  <p>
    <%
      String statusA3 = (String) request.getAttribute("statusA3");
        if (statusA3 == null) statusA3 = "Node A3 did no operations.";
    %>
    <%=statusA3%>
  </p>
  <p>
    <%
      String statusB1 = (String) request.getAttribute("statusB1");
        if (statusB1 == null) statusB1 = "Node B1 did no operations.";
    %>
    <%=statusB1%>
  </p>
  <p>
    <%
      String statusB2 = (String) request.getAttribute("statusB2");
        if (statusB2 == null) statusB2 = "Node B2 did no operations.";
    %>
    <%=statusB2%>
  </p>
  <p>
    <%
      String statusC1 = (String) request.getAttribute("statusC1");
        if (statusC1 == null) statusC1 = "Node C1 did no operations.";
    %>
    <%=statusC1%>
  </p>
  <p>
    <%
      String statusC2 = (String) request.getAttribute("statusC2");
        if (statusC2 == null) statusC2 = "Node C2 did no operations.";
    %>
    <%=statusC2%>
  </p>
  <br>
  <h2>History of operations</h2>

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

  </body>
</html>
