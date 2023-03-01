<%--
  Created by IntelliJ IDEA.
  User: sjdlr
  Date: 2023-03-01
  Time: 오전 3:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/main-table.css?after" type="text/css">
    <title>Title</title>
</head>
<body>

<h1>북마크 삭제</h1>
<div style="font-size: medium">
    <a href="\">홈</a> |
    <a href="history-list.jsp">위치 히스토리 목록</a> |
    <a href="get_wifi_info.jsp">Open Api 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">북마크 보기</a> |
    <a href="bookmark-group.jsp">북마크 그룹 관리</a>
</div><br>

<div>북마크를 삭제하시겠습니까?</div>

<div>
    <table>
        <tr>
            <th>북마크 이름</th>
            <td><%=request.getParameter("bookmark-name")%></td>
        </tr>
        <tr>
            <th>와이파이명</th>
            <td><%=request.getParameter("wifi-name")%></td>
        </tr>
        <tr>
            <th>등록일자</th>
            <td><%=request.getParameter("time")%></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <a href="javascript:history.back()">돌아가기</a>|
                <button type="button" onclick="deleteBookmark()">삭제</button>
            </td>
        </tr>
    </table>
</div>

<script>
    function deleteBookmark() {
        httpRequest = new XMLHttpRequest();
        let key = <%=request.getParameter("id")%>;
        let url = "/bookmark?id=" + key;
        httpRequest.open("DELETE", url, true);
        httpRequest.setRequestHeader("Content_Type", "application/x-www-form-urlencoded");

        httpRequest.send();
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200) {
                alert("성공하였습니다.");
                history.back();
            } else if (httpRequest.readyState == XMLHttpRequest.DONE){
                alert("실패하였습니다.");
            }
        }
    }
</script>
</body>
</html>
