<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>pipeline</title>
        <link rel="stylesheet" type="text/css" href="resources/css/screen.css" />
    </head>
    <body>
        <div class="content">

            <form method="POST">
                <input type="text" name="project-name" id="project-name" value="" />
                <input type="submit" value="add new project" />
            </form>

            <hr />

            <c:forEach items="${projects}" var="project" varStatus="loop">
                ${project.name}
                <br />
            </c:forEach>
        </div><!-- .content -->
    </body>
</html>
