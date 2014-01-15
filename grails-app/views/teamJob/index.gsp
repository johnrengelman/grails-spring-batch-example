<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Team Job Launcher</title>
  <meta name="layout" content="main"/>
</head>
<body>

<div>
    ${flash.error}
    ${flash.message}
</div>

<div>
    <g:form name="teamJobStart" action="startJob">
        <g:submitButton name="startJob" value="Start Job"/>
    </g:form>
</div>

</body>
</html>