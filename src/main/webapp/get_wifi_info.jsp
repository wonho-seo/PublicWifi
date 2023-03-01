<%--
  Created by IntelliJ IDEA.
  User: sjdlr
  Date: 2023-02-16
  Time: 오후 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        window.onload = function () {
            getInfo();
        };
    </script>
</head>
<body>
<div align="center">
<h1 class="get-wifi-text" align="center" > </h1>
<a href="findWifiMain.jsp" style="font-size:8px">홈 으로 가기</a>
</div>

<script>
    function getInfo(){
        let httpRequest = new XMLHttpRequest();

        let url = "/open-api"
        httpRequest.open("POST", url, true);
        httpRequest.setRequestHeader("Content_Type", "application/x-www-form-urlencoded");

        httpRequest.send();
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState == XMLHttpRequest.DONE && httpRequest.status == 200) {

                let text = httpRequest.responseText + "개의 WIFI 정보를 정상적으로 저장하였습니다.";
                document.getElementsByClassName('get-wifi-text').item(0).innerHTML = text;
            }
        }
    }
</script>
</body>
</html>
