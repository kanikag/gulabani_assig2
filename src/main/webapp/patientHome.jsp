<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Patient's Home</title>

    <link href="${contextPath}/resources/css/jquery.dataTables.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>

<body>
<div style="float: right; margin: 15px;">
    <form id="logoutForm" method="POST" action="${contextPath}/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <a onclick="document.forms['logoutForm'].submit()">Logout</a>
</div>
<div class="container">
    <h1>Welcome to  ${username} Home!</h1>
    <table id="videoTable">
        <thead>
        <tr>
            <th>Video</th>
            <th>Title</th>
            <th>Description</th>
        </tr>
        </thead>
        <c:forEach items="${videos}" var="video" varStatus="id">
            <tr style="padding: 10px;">
                <td>
                    <img src="${video.snippet.thumbnails.medium.url}" width="${video.snippet.thumbnails.medium.width}"
                         height="${video.snippet.thumbnails.medium.height}"/>
                </td>
                <td>
                    <span>${video.snippet.title}</span>
                </td>
                <td>
                    <span>${video.snippet.description}</span>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/jquery.dataTables.min.js"></script>
<script>
    $(document).ready(function () {
        $('#videoTable').DataTable();
    });
</script>
</body>
</html>
