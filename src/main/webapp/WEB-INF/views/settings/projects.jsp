<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
    <form method="POST" action="${contextPath}/settings/projects">
        <spring:bind path="project.name">
            <input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
        </spring:bind>
        <input type="submit" value="add new project" />
    </form>

    <hr />

    <table>
        <c:forEach items="${projects}" var="project" varStatus="loop">
            <tr>
                <td>
                    <a href="${contextPath}/settings/projects/${project.name}">${project.name}</a>
                </td>
                <td>
                    <a href="${contextPath}/settings/versions/${project.name}">versions</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
