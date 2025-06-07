<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <%@include file="base-head.jsp" %>
    <title>CRUD Manager - Pet</title>
</head>
<body>
<%@include file="modal.html" %>
<%@include file="nav-menu.jsp" %>

<div id="container" class="container-fluid">
    <div id="top" class="row">
        <div class="col-md-12">
            <h2 class="h3">Cadastro de Pet</h2>
        </div>
    </div>

    <hr />

    <form action="${pageContext.request.contextPath}/pets" method="post">
        <c:if test="${pet != null}">
            <input type="hidden" name="id" value="${pet.id}" />
        </c:if>

        <div class="row">
            <div class="form-group col-md-6">
                <label for="name">Nome do Pet</label>
                <input type="text" id="name" name="name" class="form-control" required
                       value="${pet != null ? pet.name : ''}" />
            </div>

            <div class="form-group col-md-6">
                <label for="species">Espécie</label>
                <input type="text" id="species" name="species" class="form-control" required
                       value="${pet != null ? pet.species : ''}" />
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-4">
                <label for="birthdate">Data de Nascimento</label>
                <input type="date" id="birthdate" name="birthdate" class="form-control" required
                       value="<c:out value='${pet != null && pet.birthdate != null ? pet.birthdate.toString() : ""}'/>" />
            </div>

            <div class="form-group col-md-4">
                <label for="weight">Peso (kg)</label>
                <input type="number" id="weight" name="weight" class="form-control" step="0.1" required
                       value="<c:out value='${pet != null ? pet.weight : ""}'/>" />
            </div>

            <div class="form-group col-md-4">
                <label for="ownerId">Proprietário</label>
                <select id="ownerId" name="ownerId" class="form-control" required>
                    <option value="">-- selecione --</option>
                    <c:forEach var="u" items="${ownersList}">
                        <option value="${u.id}" <c:if test="${pet != null && pet.ownerId == u.id}">selected</c:if>>
                            ${u.nome}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <hr />

        <div class="row">
            <div class="form-group col-md-12">
                <a href="${pageContext.request.contextPath}/pets" class="btn btn-default">
                    <span class="glyphicon glyphicon-remove"></span> Cancelar
                </a>
                <button type="submit" class="btn btn-danger">
                    <span class="glyphicon glyphicon-ok"></span> Salvar Pet
                </button>
            </div>
        </div>
    </form>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
