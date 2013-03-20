<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>

<fieldset>
    <legend>${projectName} ${versionNumber}</legend>
    <form method="POST" action="${contextPath}/settings/versions/${projectName}/${versionNumber}">
        <spring:bind path="version.number">
            <label for="${status.expression}">New Version:</label>
            <br />
            <input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
        </spring:bind>
        <input type="submit" value="update" />
    </form>
</fieldset>

</body>
