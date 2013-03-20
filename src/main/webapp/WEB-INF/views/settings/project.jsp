<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>

    <fieldset>
        <legend>${projectName}</legend>
        <form method="POST" action="${contextPath}/settings/projects/${projectName}">
            <spring:bind path="project.name">
                <label for="${status.expression}">New Name:</label>
                <br />
                <input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
            </spring:bind>
            <input type="submit" value="update" />
        </form>
    </fieldset>

</body>
