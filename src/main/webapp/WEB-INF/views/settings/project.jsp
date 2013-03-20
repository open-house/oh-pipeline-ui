<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
    <form method="POST" action="${contextPath}/settings/project">
        // TODO - add new version is through versions, this is for changing project name
        <select name="project-name" id="project-name">
            <option>TODO</option>
            <option>TODO2</option>
        </select>
        <input type="text" name="version-number" id="version-number" value="" />
        <input type="submit" value="add new version" />
    </form>

    <hr />
</body>
