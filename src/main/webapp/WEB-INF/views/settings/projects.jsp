<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
                <spring:bind path="project.name">
                    <input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
                </spring:bind>
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
