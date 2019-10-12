<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Researcher's Home</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
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
    <h1>Welcome to Researcher's Home!</h1>
    <c:if test="${patientsData.size() > 0}"><h2>Patient's Data</h2></c:if>
    <c:forEach items="${patientsData}" var="patientData" varStatus="pid">
        <div style="border-bottom: 1px solid #bbb">
            <b>
                <div>${patientData.username}</div>
            </b>
            <div>Email: ${patientData.email}</div>
            <div>Lat : ${patientData.lat}</div>
            <div>Lng : ${patientData.lng}</div>
            <c:forEach items="${patientData.therapy}" var="therapy" varStatus="thid">
                <div>Therapy Name : ${therapy.therapyName}</div>
                <div>Medicine : ${therapy.medicine}</div>
                <div>Dosage : ${therapy.dosage}</div>
                Tests Information :
                <c:forEach items="${therapy.tests}" var="test" varStatus="tid">
                    <div> Time : ${test.startTime}</div>
                    <table class="sessionTable">
                        <c:forEach items="${test.testSessions}" var="testSession" varStatus="tsid">
                            <tr>
                                <td><span>${testSession.x}</span></td>
                                <td><span>${testSession.y}</span></td>
                                <td><span>${testSession.time}</span></td>
                                <c:if test="${testSession.type == 2}">
                                    <td><span>${testSession.button}</span></td>
                                    <td><span>${testSession.correct}</span></td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </table>
                </c:forEach>
            </c:forEach>
        </div>
    </c:forEach>

    <h2>Feeds</h2>
    <div><b>Title</b>: ${channels.title}</div>
    <div><b>Description</b> : ${channels.description}</div>
    <div><b>Link</b> : ${channels.link}</div>
    <div><b>Language</b>: ${channels.language}</div>
    <div><b>Creator</b> : ${channels.creator}</div>

    <c:forEach items="${channels.items}" var="item" varStatus="itemId">
        <div style="padding: 10px; border-bottom: 1px solid black;">
            <div><b>Title</b>: ${item.title}</div>
            <div><b>Description</b> : ${item.description}</div>
            <div><b>Link</b> : <a href="${item.link}">${item.link}</a></div>
            <div><b>Comments</b>: ${item.comments}</div>
            <div><b></b>Guid </b>: ${item.guid}</div>
            <div><b>Published Date </b>: ${item.pubDate}</div>
        </div>
    </c:forEach>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
