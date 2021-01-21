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
<%@ page import="java.util.Arrays" %>
<html>
  <head>
    <title>Live view of node status</title>
  </head>
  <body>
  <h1>Live view of node status</h1>
  <p>La hora del servidor es: <%= new Date() %><%response.setIntHeader("Refresh", 1);%></p>

  <table style="
  width: 90%;
  border-spacing: 5px 15px;
  border: 1px solid black;
  border-collapse: separate">
    <tr>
      <%
        String statusA1 = (String) request.getAttribute("statusA1");
        if (statusA1 == null) {
          statusA1 = "A1:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]";
        }

          String [] firstSplit = statusA1.split(":");
      %>
      <th>
        <%=firstSplit[0]%>
      </th>
      <%
          firstSplit[1] = firstSplit[1].replace("[", "");
          firstSplit[1] = firstSplit[1].replace("]", "");
          String []secondSplit = firstSplit[1].split(", ");
          for (String i : secondSplit){
      %>
      <td style="text-align: center">
        <%=i%>
      </td>
      <%
          };
      %>
    </tr>

    <tr>
      <%
        String statusA2 = (String) request.getAttribute("statusA2");
        if (statusA2 == null) {
          statusA2 = "A2:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]";
        }

        firstSplit = statusA2.split(":");
      %>
      <th>
        <%=firstSplit[0]%>
      </th>
      <%
        firstSplit[1] = firstSplit[1].replace("[", "");
        firstSplit[1] = firstSplit[1].replace("]", "");
        secondSplit = firstSplit[1].split(", ");
        for (String i : secondSplit){
      %>
      <td style="text-align: center">
        <%=i%>
      </td>
      <%
        };
      %>
    </tr>

    <tr>
      <%
        String statusA3 = (String) request.getAttribute("statusA3");
        if (statusA3 == null) {
          statusA3 = "A2:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]";
        }

        firstSplit = statusA3.split(":");
      %>
      <th>
        <%=firstSplit[0]%>
      </th>
      <%
        firstSplit[1] = firstSplit[1].replace("[", "");
        firstSplit[1] = firstSplit[1].replace("]", "");
        secondSplit = firstSplit[1].split(", ");
        for (String i : secondSplit){
      %>
      <td style="text-align: center">
        <%=i%>
      </td>
      <%
        };
      %>
    </tr>

    <tr>
      <%
        String statusB1 = (String) request.getAttribute("statusB1");
        if (statusB1 == null) {
          statusB1 = "B1:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]";
        }

        firstSplit = statusB1.split(":");
      %>
      <th>
        <%=firstSplit[0]%>
      </th>
      <%
        firstSplit[1] = firstSplit[1].replace("[", "");
        firstSplit[1] = firstSplit[1].replace("]", "");
        secondSplit = firstSplit[1].split(", ");
        for (String i : secondSplit){
      %>
      <td style="text-align: center">
        <%=i%>
      </td>
      <%
        };
      %>
    </tr>

    <tr>
      <%
        String statusB2 = (String) request.getAttribute("statusB2");
        if (statusB2 == null) {
          statusB2 = "B2:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]";
        }

        firstSplit = statusB2.split(":");
      %>
      <th>
        <%=firstSplit[0]%>
      </th>
      <%
        firstSplit[1] = firstSplit[1].replace("[", "");
        firstSplit[1] = firstSplit[1].replace("]", "");
        secondSplit = firstSplit[1].split(", ");
        for (String i : secondSplit){
      %>
      <td style="text-align: center">
        <%=i%>
      </td>
      <%
        };
      %>
    </tr>

    <tr>
      <%
        String statusC1 = (String) request.getAttribute("statusC1");
        if (statusC1 == null) {
          statusC1 = "C1:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]";
        }

        firstSplit = statusC1.split(":");
      %>
      <th>
        <%=firstSplit[0]%>
      </th>
      <%
        firstSplit[1] = firstSplit[1].replace("[", "");
        firstSplit[1] = firstSplit[1].replace("]", "");
        secondSplit = firstSplit[1].split(", ");
        for (String i : secondSplit){
      %>
      <td style="text-align: center">
        <%=i%>
      </td>
      <%
        };
      %>
    </tr>

    <tr>
      <%
        String statusC2 = (String) request.getAttribute("statusC2");
        if (statusC2 == null) {
          statusC2 = "C2:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]";
        }

        firstSplit = statusC2.split(":");
      %>
      <th>
        <%=firstSplit[0]%>
      </th>
      <%
        firstSplit[1] = firstSplit[1].replace("[", "");
        firstSplit[1] = firstSplit[1].replace("]", "");
        secondSplit = firstSplit[1].split(", ");
        for (String i : secondSplit){
      %>
      <td style="text-align: center">
        <%=i%>
      </td>
      <%
        };
      %>
    </tr>
  </table>

  <br>
  <h2>History of operations</h2>

  <%
    ArrayList<String> status;
    //do {
      status = (ArrayList<String>) request.getAttribute("status");

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
