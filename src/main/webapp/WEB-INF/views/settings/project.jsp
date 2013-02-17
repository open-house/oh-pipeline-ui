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
                // TODO - add new version is through versions, this is for changing project name
                <select name="project-name" id="project-name">
                    <option>TODO</option>
                    <option>TODO2</option>
                </select>
                <input type="text" name="version-number" id="version-number" value="" />
                <input type="submit" value="add new version" />
            </form>

            <hr />

        </div><!-- .content -->
    </body>
</html>
