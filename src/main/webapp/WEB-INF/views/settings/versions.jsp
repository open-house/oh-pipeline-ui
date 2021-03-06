<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>

    <form method="POST" action="${contextPath}/settings/versions/${projectName}">
        <spring:bind path="version.number">
            <input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
        </spring:bind>
        <input type="submit" value="add new version" />
    </form>

    <hr />

    <c:forEach items="${versions}" var="version" varStatus="loop">
        ${version.versionNumber}
        <br />
    </c:forEach>
</body>
