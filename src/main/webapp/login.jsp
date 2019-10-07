<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.hellokoding.auth.social.FacebookConnectionService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%
    FacebookConnectionService fbConnection = new FacebookConnectionService();
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Log in with your account</title>

      <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
      <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
      <meta name="google-signin-scope" content="kanikagulabani9@gmail.com">
      <meta name="google-signin-client_id" content="831334154326-1lb1hpusmpqp2dndg4dgb4em6h4hqjaf.apps.googleusercontent.com">
      <script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
  </head>

  <body style="text-align: center; margin: 0 auto;">
      <script>
      function onSignIn(googleUser) {
          // Useful data for your client-side scripts:
          var profile = googleUser.getBasicProfile();
          console.log("ID: " + profile.getId()); // Don't send this directly to your server!
          console.log('Full Name: ' + profile.getName());
          console.log('Given Name: ' + profile.getGivenName());
          console.log('Family Name: ' + profile.getFamilyName());
          console.log("Image URL: " + profile.getImageUrl());
          console.log("Email: " + profile.getEmail());

          // The ID token you need to pass to your backend:
          var id_token = googleUser.getAuthResponse().id_token;
          console.log("ID Token: " + id_token);
      }
  </script>
  <div class="container">
      <form method="POST" action="${contextPath}/login" class="form-signin">
        <h2 class="form-heading">Log in</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
<%--            <div class="fb-login-button" data-width="" data-size="large" data-button-type="continue_with" data-auto-logout-link="false" data-use-continue-as="false"></div>--%>
            <div class="g-signin2" data-onsuccess="onSignIn"></div>
        </div>
      </form>
    </div>

     <div style="margin: 0 auto;">
         <a href="<%=fbConnection.getFBAuthUrl()%>">
             <img style="margin-top: 138px;" src="./resources/img/facebookloginbutton.png" />
         </a>
     </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
  </body>
</html>
