<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="base-head.jsp" %>
    <title>CRUD Manager - Pets</title>
</head>
<body>
<%@include file="modal.html" %>
<%@include file="nav-menu.jsp" %>

<div id="container" class="container-fluid">
    <c:if test="${not empty message}">
        <div class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            ${message}
        </div>
    </c:if>

    <div id="top" class="row">
        <div class="col-md-3">
            <h3>Pets</h3>
        </div>

        <div class="col-md-6">
            <div class="input-group h2">
                <input class="form-control" id="search" type="text" placeholder="Pesquisar pets">
                <span class="input-group-btn">
                    <button class="btn btn-danger" type="submit">
                        <span class="glyphicon glyphicon-search"></span>
                    </button>
                </span>
            </div>
        </div>

        <div class="col-md-3">
            <a href="${pageContext.request.contextPath}/pets?action=new" class="btn btn-danger pull-right h2">
                <span class="glyphicon glyphicon-plus"></span>&nbspAdicionar Pet
            </a>
        </div>
    </div>

    <hr />

    <div id="list" class="row">
        <div class="table-responsive col-md-12">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Espécie</th>
                    <th>Data de Nascimento</th>
                    <th>Peso (kg)</th>
                    <th>Proprietário</th>
                    <th>Editar</th>
                    <th>Excluir</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="pet" items="${listPets}">
                    <tr>
                        <td>${pet.id}</td>
                        <td>${pet.name}</td>
                        <td>${pet.species}</td>
                        <td><fmt:formatDate value="${pet.birthdate}" pattern="dd/MM/yyyy" /></td>
                        <td>${pet.weight}</td>
                        <td>
                            <c:forEach var="owner" items="${ownersList}">
                                <c:if test="${owner.id == pet.ownerId}">
                                    ${owner.nome}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td class="actions">
                            <a class="btn btn-info btn-xs"
                               href="${pageContext.request.contextPath}/pets?action=edit&id=${pet.id}">
                                <span class="glyphicon glyphicon-edit"></span>
                            </a>
                        </td>
                        <td class="actions">
                            <a class="btn btn-danger btn-xs modal-remove"
                               pet-id="${pet.id}"
                               pet-name="${pet.name}" data-toggle="modal"
                               data-target="#delete-modal" href="#">
                                <span class="glyphicon glyphicon-trash"></span>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div id="bottom" class="row">
        <div class="col-md-12">
            <ul class="pagination">
                <li class="disabled"><a>&lt; Anterior</a></li>
                <li class="disabled"><a>1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li class="next"><a href="#" rel="next">Próximo &gt;</a></li>
            </ul>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        // Fecha automaticamente o alerta após 5 segundos
        setTimeout(function () {
            $(".alert").alert('close');
        }, 5000);

        // Configura modal de exclusão
        $(".modal-remove").click(function () {
            var petName = $(this).attr('pet-name');
            var petId = $(this).attr('pet-id');
            
            // Atualiza o texto de confirmação
            $(".modal-body #hiddenValue").text("o pet '" + petName + "'");
            
            // Define o ID do pet a ser excluído
            $("#deleteId").val(petId);
            
            // Define a ação do formulário
            $("#deleteForm").attr("action", "${pageContext.request.contextPath}/pets?action=delete");
        });
    });
</script>
</body>
</html>