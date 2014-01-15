<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Game Job Launcher</title>
  <meta name="layout" content="main"/>
</head>
<body>

<div>
    ${flash.error}
    ${flash.message}
</div>

<div>
    <g:uploadForm name="gameJobStart" action="startJob">
        <input type="file" name="file"/>
        <g:submitButton name="startJob" value="Start Job"/>
    </g:uploadForm>
</div>

</body>
</html>