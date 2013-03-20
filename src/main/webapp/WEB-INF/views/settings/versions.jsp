<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
    <form method="POST" action="${contextPath}/settings/versions">
        <select name="project-name" id="project-name">
            <option value="TODO">TODO</option>
        </select>
        <input type="text" name="version-number" id="version-number" value="" />
        <input type="submit" value="add new version" />
    </form>

    <hr />

    <c:forEach items="${versions}" var="version" varStatus="loop">
        ${version.versionNumber}
        <br />
    </c:forEach>
</body>
