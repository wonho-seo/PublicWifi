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
      document.getElementById('bookmark-group-table-tbody').innerHTML = "";
      getBookmarkGroupList();
    }
  </script>
  <link rel="stylesheet" href="css/main-table.css?after" type="text/css">
    <title>BookMarkGroup</title>
</head>
<body>

<h1>북마크 그룹</h1>
<div style="font-size: medium">
  <a href="\">홈</a> |
  <a href="history-list.jsp">위치 히스토리 목록</a> |
  <a href="get_wifi_info.jsp">Open Api 와이파이 정보 가져오기</a> |
  <a href="bookmark-list.jsp">북마크 보기</a> |
  <a href="bookmark-group.jsp">북마크 그룹 관리</a>
</div><br>

<button type="button" onclick="location.href='bookmark-group-add.jsp'" >북마크 그룹 이름 추가</button>

<table id="bookmark-group-table">
  <tr>
    <th>ID</th>
    <th>북마크 이름</th>
    <th>순서</th>
    <th>등록일자</th>
    <th>수정일자</th>
    <th>비고</th>
  </tr>
  <thead>
  <tbody id="bookmark-group-table-tbody">
  </tbody>
  </thead>
</table>

<script>
  function getBookmarkGroupList(){
    httpRequest = new XMLHttpRequest();

    let url = "/bookmark-group";
    httpRequest.open("GET", url, true);
    httpRequest.setRequestHeader("Content_Type", "application/x-www-form-urlencoded");

    httpRequest.send();
    httpRequest.onreadystatechange = function () {
      if (httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200) {
        let list = JSON.parse(httpRequest.responseText);
        let tbody = document.getElementById('bookmark-group-table-tbody').innerHTML;

        for (let i = 0; i < list.length; i++) {
          let table = '<tr>';
          table += '<td>' + list[i].key + '</td>'
          table += '<td>' + list[i].bookmarkName + '</td>'
          table += '<td>' + list[i].order + '</td>'
          table += '<td>' + list[i].createTime + '</td>'
          table += '<td>';
          if (list[i].modifiedTime !== undefined){
            table += list[i].modifiedTime;
          }
          table += '</td>'
          table += '<td>' + '<a href="bookmark-group-edit.jsp?order=' + list[i].order +'&bookmark-name=' + list[i].bookmarkName + '&id=' + list[i].key + '">수정</a>'
          table += '<a href="" onclick="test(' + list[i].order +')">삭제</a>'+ '</td>'
          table += '</tr>';
          tbody += table;
          document.getElementById('bookmark-group-table-tbody').innerHTML = tbody;
        }
      }
    }
  }

  function test(order){
    httpRequest = new XMLHttpRequest();

    let url = "/bookmark-group";
    url += "?id=";
    url += order;
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
