<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Doctor's Home</title>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
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
    <h1>Welcome to Dr. ${username} Home!</h1>
    <h2>Patient's Data</h2>
    <div id="tabs">
        <ul>
            <c:forEach items="${patientsData}" var="patientData" varStatus="pid">
                <li><a href="#tabs-${pid.index}">${patientData.username}</a></li>
            </c:forEach>
        </ul>
        <c:forEach items="${patientsData}" var="patientData" varStatus="pid">
            <div id="tabs-${pid.index}" style="border-bottom: 1px solid #bbb">

                <div>${patientData.username}</div>
                </b>
                <div><b>Email</b>: ${patientData.email}</div>
                <div><b>Lat </b>: ${patientData.lat}</div>
                <div><b>Lng </b>: ${patientData.lng}</div>
                <c:forEach items="${patientData.therapy}" var="therapy" varStatus="thid">
                    <div><b>Therapy Name</b> : ${therapy.therapyName}</div>
                    <div><b>Medicine</b> : ${therapy.medicine}</div>
                    <div><b>Dosage</b> : ${therapy.dosage}</div>
                    <b>Tests Information</b> :
                    <c:forEach items="${therapy.tests}" var="test" varStatus="tid">
                        <div> Time : ${test.startTime}</div>
                        <div style="width: 50%; display: inline-block">
                            <table class="sessionTable">
                                <c:forEach items="${test.testSessions}" var="testSession" varStatus="tsid">
                                    <c:if test="${tsid.index == 0}">
                                        <thead>
                                        <tr>
                                            <th>${testSession.x}</th>
                                            <th>${testSession.y}</th>
                                            <th>${testSession.time}</th>
                                            <c:if test="${testSession.type == 2}">
                                                <th>${testSession.button}</th>
                                                <th>${testSession.correct}</th>
                                            </c:if>
                                        </tr>
                                        </thead>
                                    </c:if>
                                    <c:if test="${tsid.index != 0}">
                                        <tbody>
                                        <tr>
                                            <td><span>${testSession.x}</span></td>
                                            <td><span>${testSession.y}</span></td>
                                            <td><span>${testSession.time}</span></td>
                                            <c:if test="${testSession.type == 2}">
                                                <td><span>${testSession.button}</span></td>
                                                <td><span>${testSession.correct}</span></td>
                                            </c:if>
                                        </tr>
                                        </tbody>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </div>
                    </c:forEach>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="${contextPath}/resources/js/jquery.dataTables.min.js"></script>
<script>
    $(document).ready(function () {
        $("#tabs").tabs();
        $('.sessionTable').DataTable({
            "lengthMenu": [[5, 25, 50, -1], [5, 25, 50, "All"]],
            "displayLength": 5,
            "pageLength": 5,
        });
    });
</script>
</body>
</html>
