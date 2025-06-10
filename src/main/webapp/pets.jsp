<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <%@ include file="base-head.jsp" %>
  <title>CRUD Manager – Pets</title>
</head>
<body>
  <%@ include file="modal.html" %>
  <%@ include file="nav-menu.jsp" %>

  <div class="container-fluid">
    <c:if test="${not empty message}">
      <div class="alert alert-${alertType == 1 ? 'success' : 'danger'} alert-dismissable">
        <button class="close" data-dismiss="alert">&times;</button>
        ${message}
      </div>
    </c:if>

    <div class="row">
      <div class="col-md-3"><h3>Pets</h3></div>
      <div class="col-md-6">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="Pesquisar pets">
          <span class="input-group-btn">
            <button class="btn btn-danger"><span class="glyphicon glyphicon-search"></span></button>
          </span>
        </div>
      </div>
      <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/pet/form" class="btn btn-danger pull-right">
          <span class="glyphicon glyphicon-plus"></span> Adicionar Pet
        </a>
      </div>
    </div>

    <hr/>

    <div class="table-responsive">
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th>ID</th><th>Nome</th><th>Espécie</th><th>Data</th><th>Peso</th><th>Proprietário</th><th>Editar</th><th>Excluir</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="pet" items="${listPets}">
            <tr>
              <td>${pet.id}</td>
              <td>${pet.name}</td>
              <td>${pet.species}</td>
              <td><fmt:formatDate value="${pet.birthdate}" pattern="dd/MM/yyyy"/></td>
              <td>${pet.weight}</td>
              <td>
                <c:forEach var="u" items="${ownersList}">
                  <c:if test="${u.id == pet.ownerId}">${u.nome}</c:if>
                </c:forEach>
              </td>
              <td>
                <a href="${pageContext.request.contextPath}/pet/update?id=${pet.id}" class="btn btn-info btn-xs">
                  <span class="glyphicon glyphicon-edit"></span>
                </a>
              </td>
              <td>
                <a href="#" class="btn btn-danger btn-xs modal-remove"
                   data-id="${pet.id}" data-name="${pet.name}" data-target="#delete-modal" data-toggle="modal">
                  <span class="glyphicon glyphicon-trash"></span>
                </a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  <script>
  $('.modal-remove').click(function() {
    var id = $(this).data('id'),
        name = $(this).data('name');

    $('#deleteId').val(id); 
    $('#hiddenValue').text(name); 
    $('#deleteForm').attr('action', '${pageContext.request.contextPath}/pet/delete'); 
  });
</script>

</body>
</html>
