<%--
  Created by IntelliJ IDEA.
  User: sjdlr
  Date: 2023-02-27
  Time: 오후 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="css/main-table.css?after" type="text/css">
    <title>HistoryList</title>
  <script>
    window.onload = function () {
      document
      getHistory();
    };
  </script>
</head>
<body>
<h1>위치 히스토리 목록</h1>

<div style="font-size: medium">
  <a href="\">홈</a> |
  <a href="history-list.jsp">위치 히스토리 목록</a> |
  <a href="get_wifi_info.jsp">Open Api 와이파이 정보 가져오기</a> |
  <a href="bookmark-list.jsp">북마크 보기</a> |
  <a href="bookmark-group.jsp">북마크 그룹 관리</a>
</div>


<table id="history-table">
  <tr>
    <th>ID</th>
    <th>X좌표</th>
    <th>Y좌표</th>
    <th>조회일자</th>
    <th>비고</th>
  </tr>
  <thead>
  <tbody id="history-table-body">
  </tbody>
  </thead>
</table>


<script>
  function getHistory() {
    httpRequest = new XMLHttpRequest();
    let url = "/find-history";
    httpRequest.open("GET", url, true);
    httpRequest.setRequestHeader("Content_Type", "application/x-www-form-urlencoded");

    httpRequest.send();
    httpRequest.onreadystatechange = function () {
      if (httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200) {
        var test = JSON.parse(httpRequest.responseText);
        let tbody = document.getElementById('history-table-body').innerHTML;
        for (let i = 0; i < test.length; i++) {
          test2 = test[i];
          let table = '<tr>';
          table += '<td>' + test[i].key + '</td>'
          table += '<td>' + test[i].lnt + '</td>'
          table += '<td>' + test[i].lat + '</td>'
          table += '<td>' + test[i].aDttm + '</td>'
          table += '<td>' + '<button class="history-delete-button" type="button" onclick="deleteHistory();">삭제</button>' + '</td>'
          table += '</tr>';
          tbody += table;
        }
        document.getElementById('history-table-body').innerHTML = tbody;
      }
    }

  }

  function deleteHistory(){
    let historyList = document.getElementById("history-table");
    for (let i = 1; i < historyList.rows.length; i++){
      historyList.rows[i].cells[4].onclick = function () {
        let historyKey = historyList.rows[i].cells[0].innerText;

        httpRequest = new XMLHttpRequest();

        let url = "/find-history";
        url += "?id=";
        url += historyKey;
        httpRequest.open("DELETE", url, true);
        httpRequest.setRequestHeader("Content_Type", "application/x-www-form-urlencoded");

        httpRequest.send();
        httpRequest.onreadystatechange = function () {
          if (httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200) {
          }
        }

        document.getElementById('history-table-body').innerHTML = null;
        getHistory();
      }
    }
  }
</script>

</body>
</html>
