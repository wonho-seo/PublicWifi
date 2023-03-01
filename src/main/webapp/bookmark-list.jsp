<%--
  Created by IntelliJ IDEA.
  User: sjdlr
  Date: 2023-02-28
  Time: 오전 1:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script>
        window.onpageshow = function (){
            document.getElementById('bookmark-table-tbody').innerHTML = "";
            getBookmarkList();
        }
    </script>
    <link rel="stylesheet" href="css/main-table.css?after" type="text/css">
    <title>BookMarkList</title>
</head>
<body>
<h1>북마크 목록</h1>
<div style="font-size: medium">
    <a href="\">홈</a> |
    <a href="history-list.jsp">위치 히스토리 목록</a> |
    <a href="get_wifi_info.jsp">Open Api 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">북마크 보기</a> |
    <a href="bookmark-group.jsp">북마크 그룹 관리</a>
</div><br>

<table id="bookmark-table">
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>와이파이명</th>
        <th>등록일자</th>
        <th>비고</th>
    </tr>
    <thead>
    <tbody id="bookmark-table-tbody">
    </tbody>
    </thead>
</table>

<script>
    function getBookmarkList(){
        httpRequest = new XMLHttpRequest();

        let url = "/bookmark";
        httpRequest.open("GET", url, true);
        httpRequest.setRequestHeader("Content_Type", "application/x-www-form-urlencoded");

        httpRequest.send();
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200) {
                let list = JSON.parse(httpRequest.responseText);
                let tbody = document.getElementById('bookmark-table-tbody').innerHTML;

                for (let i = 0; i < list.length; i++) {
                    let table = '<tr>';
                    table += '<td>' + list[i].id + '</td>'
                    table += '<td>' + list[i].bookmarkName + '</td>'
                    table += '<td>' + list[i].wifiName+ '</td>'
                    table += '<td>' + list[i].createTime + '</td>'
                    table += '<td>' + '<a href="bookmark-remove.jsp?bookmark-name=' + list[i].bookmarkName +'&wifi-name=' + list[i].wifiName + '&time=' + list[i].createTime +'&id=' + list[i].id + '">삭제</a>'
                    table += '</tr>';
                    tbody += table;
                    document.getElementById('bookmark-table-tbody').innerHTML = tbody;
                }
            }
        }
    }
</script>
</body>
</html>
