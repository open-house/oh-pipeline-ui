<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
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
</body>
