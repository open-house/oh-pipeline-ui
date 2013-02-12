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

            <form class="filter">
                <spring:bind path="projectVersion.projectName">
                    <select id="${status.expression}" name="${status.expression}">
                        <c:forEach items="${projects}" var="project" varStatus="loop">
                            <option value="${project.name}"<c:if test="${project.name == status.value}"> selected="selected"</c:if>>
                                ${project.name}
                            </option>
                        </c:forEach>
                    </select>
                </spring:bind>

                <spring:bind path="projectVersion.versionNumber">
                    <select id="${status.expression}" name="${status.expression}">
                        <c:forEach items="${versions}" var="version" varStatus="loop">
                            <option value="${version}"<c:if test="${version == status.value}"> selected="selected"</c:if>>
                                ${version}
                            </option>
                        </c:forEach>
                    </select>
                </spring:bind>

                <input type="submit" value="show" />
            </form>

            <a href="/settings" class="settings">settings</a>

            <hr />
            <div class="error">${error}</div>

            <table>
                <thead>
                    <tr>
                        <th>&nbsp;</th>
                        <c:forEach items="${phases}" var="phase" varStatus="loop">
                            <th style="width: ${columnWidth}%">${phase}</th>
                        </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${builds}" var="build" varStatus="loop">
                        <tr>
                            <th><span class="label build">${build.number}</span></th>
                            <c:forEach items="${phases}" var="phase" varStatus="loop">
                                <td>
                                    <span class="<c:choose>
                                            <c:when test="${build.states[phase] == 'FAIL'}">label fail</c:when>
                                            <c:when test="${build.states[phase] == 'SUCCESS'}">label success</c:when>
                                            <c:when test="${build.states[phase] == 'IN_PROGRESS'}">label</c:when>
                                        </c:choose>">
                                        ${build.states[phase]}
                                    </span>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div><!-- .content -->
    </body>
</html>