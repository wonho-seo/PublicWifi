<%--
  Created by IntelliJ IDEA.
  User: sjdlr
  Date: 2023-02-15
  Time: 오후 1:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script>
        window.onload{
            getBookmarkGroupName();
        }
    </script>
    <link rel="stylesheet" href="css/main-table.css?after" type="text/css">
    <title>Title</title>

</head>
<body>

<h1>와이파이 정보 구하기</h1>
<div style="font-size: medium">
    <a href="\">홈</a> |
    <a href="history-list.jsp">위치 히스토리 목록</a> |
    <a href="get_wifi_info.jsp">Open Api 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">북마크 보기</a> |
    <a href="bookmark-group.jsp">북마크 그룹 관리</a>
</div>
<br>

<div id="main-table">

<form style="font-size: medium" action="/find-wifi"  method="get">
    LAT: <input type="text" name="lat">
    ,LNT: <input type="text" name="lnt">
    <input type="button" onclick="getLocation()" value="내 위치 가져오기">
    <button type="button" onclick="getWifiInfo(lat, lnt)" >근처 WIFI정보 보기</button>
</form>

<table id="wifi-info-table">
    <tr>
        <th>거리(Km)</th>
        <th>관리번호</th>
        <th>자치구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내외구분</th>
        <th>WIFI접속환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </tr>
    <thead>
    <tbody id="tbody">
    </tbody>
    </thead>
</table>
</div>

<div id="detail-table" style="display: none">
    <form name="form" style="font-size: medium">
        <select id="bookmark-group-name-list">
        </select>
        <input type="button" onclick="addBookmark()" value="북마크 추가하기">
    </form>
    <table id="detail-wifi-info-table">
        <thead>
        <tbody id="detail-wifi-info-table-tbody">
        </tbody>
        </thead>
    </table>
</div>

<script>
    function getLocation() {
        navigator.geolocation.getCurrentPosition((pos) => {
            document.getElementsByName("lnt").item(0).value = String(pos.coords.longitude);
            document.getElementsByName("lat").item(0).value = String(pos.coords.latitude);
        })
    }

    function getWifiInfo(lat, lnt) {
        let parseLat = Number.parseFloat(lat.value);
        let parseLnt = Number.parseFloat(lnt.value);
        if (Number.isNaN(parseLat) || Number.isNaN(parseLnt)) {
            alert("위도혹은 경도값이 비어있거나 유효하지 않습니다.");
            return;
        }
        httpRequest = new XMLHttpRequest();

        let url = "/find-wifi";
        url += "?lat=";
        url += lat.value;
        url += "&lnt=";
        url += lnt.value;
        httpRequest.open("GET", url, true);
        httpRequest.setRequestHeader("Content_Type", "application/x-www-form-urlencoded");

        httpRequest.send();
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200) {
                var test = JSON.parse(httpRequest.responseText);
                let tbody = "";
                for (let i = 0; i < test.length; i++) {
                    let table = '<tr>';
                    table += '<td>' + test[i].distance + '</td>'
                    table += '<td>' + test[i].managementNo + '</td>'
                    table += '<td>' + test[i].wardOffice + '</td>'
                    table += '<td>' + '<a href="#" onclick="setDetailWifiInfoTable()">' + test[i].mainNumber + '</a>' + '</td>'
                    table += '<td>' + test[i].address1 + '</td>'
                    table += '<td>' + test[i].address2 + '</td>'
                    table += '<td>' + test[i].installFloor + '</td>'
                    table += '<td>' + test[i].installType + '</td>'
                    table += '<td>' + test[i].installMainBody + '</td>'
                    table += '<td>' + test[i].service + '</td>'
                    table += '<td>' + test[i].communicationNetwork + '</td>'
                    table += '<td>' + test[i].constructionYear + '</td>'
                    table += '<td>' + test[i].inOutDoor + '</td>'
                    table += '<td>' + test[i].wifiConnectionSituation + '</td>'
                    table += '<td>' + test[i].lnt + '</td>'
                    table += '<td>' + test[i].lat + '</td>'
                    table += '<td>' + test[i].workDttm + '</td>'
                    table += '</tr>';
                    tbody += table;
                    document.getElementById('tbody').innerHTML = tbody;
                }
            }
        }
    }

    function setDetailWifiInfoTable() {
        let infoTable = document.getElementById("wifi-info-table");
        for (let i = 1; i < infoTable.rows.length; i++) {
            infoTable.rows[i].cells[3].onclick = function () {
                let tbody = document.getElementById("detail-wifi-info-table-tbody").innerHTML;
                for (let j = 0; j <infoTable.rows[i].cells.length; j++){
                    let table = '<tr>';
                    table += '<th>' + infoTable.rows[0].cells[j].innerText + '</th>'
                    table += '<td>' + infoTable.rows[i].cells[j].innerHTML + '</td>'
                    table += '</tr>';
                    tbody += table;
                }
                document.getElementById("detail-wifi-info-table-tbody").innerHTML = tbody;
                getBookmarkGroupName();
                document.getElementById("main-table").style.display="none";
                document.getElementById("detail-table").style.display="block";
            }
        }
    }

    function addBookmark(){
        httpRequest = new XMLHttpRequest();
        let bookmarkGroupName = document.getElementById("bookmark-group-name-list").value;
        let mgrNo = document.getElementById("detail-wifi-info-table").rows[1].cells[1].innerText;
        let url = "/bookmark?bookmarkGroupName=" + bookmarkGroupName;
        url += "&MGR_NO=" + mgrNo;

        httpRequest.open("POST", url, true);
        httpRequest.setRequestHeader("Content_Type", "application/x-www-form-urlencoded");

        httpRequest.send();
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200) {
                alert("추가되었습니다.");
            } else if(httpRequest.readyState == XMLHttpRequest.DONE){
                alert("실패했습니다.");
            }
        }
    }


    function getBookmarkGroupName(){
        httpRequest = new XMLHttpRequest();

        let url = "/bookmark-group";
        httpRequest.open("GET", url, true);
        httpRequest.setRequestHeader("Content_Type", "application/x-www-form-urlencoded");

        httpRequest.send();
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200) {
                let bookmarkGroupList = JSON.parse(httpRequest.responseText);
                let selectList = document.getElementById("bookmark-group-name-list");
                for (let i = 0; i < bookmarkGroupList.length; i++){
                    let tmp = document.createElement("option");
                    tmp.value = bookmarkGroupList[i].bookmarkName;
                    tmp.text = bookmarkGroupList[i].bookmarkName;
                    selectList.add(tmp);
                }
            }
        }
    }

    function returnMainTable(){
        document.getElementById("detail-table").style.display="none";
        document.getElementById("main-table").style.display="block";
    }
</script>
</body>

</html>
