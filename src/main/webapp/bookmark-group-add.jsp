<%--
  Created by IntelliJ IDEA.
  User: sjdlr
  Date: 2023-02-28
  Time: 오후 6:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/main-table.css?after" type="text/css">
    <title>Title</title>
</head>
<body>

<h1>북마크 그룹 추가</h1>
<div style="font-size: medium">
    <a href="\">홈</a> |
    <a href="history-list.jsp">위치 히스토리 목록</a> |
    <a href="get_wifi_info.jsp">Open Api 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">북마크 보기</a> |
    <a href="bookmark-group.jsp">북마크 그룹 관리</a>
</div><br>
<table id="bookmark-group-edit-table">
    <tr>
        <th>북마크 이름</th>
        <td><input type="text" id="bookmark-group-add-name"> </td>
    </tr>
    <tr>
        <th>순서</th>
        <td><input type="text" id="bookmark-group-add-order"> </td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: center">
            <button type="button" onclick="postBookmarkGroup()">추가</button>
        </td>
    </tr>
</table>


<script>
    function postBookmarkGroup(){
        httpRequest = new XMLHttpRequest();
        let name = document.getElementById("bookmark-group-add-name").value;
        let order = document.getElementById("bookmark-group-add-order").value;

        if (name == "" || order == ""){
            alert("입력값이 비어있습니다");
            return;
        }
        let url = "/bookmark-group";
        url += "?bookmark-name=";
        url += name;
        url += "&order=";
        url += order;
        httpRequest.open("POST", url, true);
        httpRequest.setRequestHeader("Content_Type", "application/x-www-form-urlencoded");

        httpRequest.send();
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200) {
                history.back();
            } else if (httpRequest.readyState == XMLHttpRequest.DONE){
                alert("실패하였습니다.");
            }
        }
    }
</script>
</body>
</html>
