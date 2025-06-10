<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <%@ include file="base-head.jsp" %>
  <title>${action == 'update' ? 'Editar Pet' : 'Novo Pet'}</title>
</head>
<body>
  <%@ include file="modal.html" %>
  <%@ include file="nav-menu.jsp" %>

  <div class="container-fluid">
    <h3>${action == 'update' ? 'Editar Pet' : 'Cadastrar Pet'}</h3>
    <hr/>

    <form action="${pageContext.request.contextPath}/pet/${action}" method="post">
      <c:if test="${action == 'update'}">
        <input type="hidden" name="id" value="${pet.id}"/>
      </c:if>

      <div class="row">
        <div class="form-group col-md-6">
          <label>Nome do Pet</label>
          <input type="text" name="name" class="form-control" required
                 value="${pet != null ? pet.name : ''}"/>
        </div>
        <div class="form-group col-md-6">
          <label>Espécie</label>
          <input type="text" name="species" class="form-control" required
                 value="${pet != null ? pet.species : ''}"/>
        </div>
      </div>

      <div class="row">
        <div class="form-group col-md-4">
          <label>Data de Nascimento</label>
          <input type="date" name="birthdate" class="form-control" required
                 value="<c:out value='${pet != null ? pet.birthdate : ""}'/>"/>
        </div>
        <div class="form-group col-md-4">
          <label>Peso (kg)</label>
          <input type="number" name="weight" class="form-control" step="0.1" required
                 value="<c:out value='${pet != null ? pet.weight : ""}'/>"/>
        </div>
        <div class="form-group col-md-4">
          <label>Proprietário</label>
          <select name="ownerId" class="form-control" required>
            <option value="">-- selecione --</option>
            <c:forEach var="u" items="${ownersList}">
              <option value="${u.id}"
                <c:if test="${pet != null && pet.ownerId == u.id}">selected</c:if>>
                ${u.nome}
              </option>
            </c:forEach>
          </select>
        </div>
      </div>

      <hr/>
      <button type="submit" class="btn btn-primary">
        ${action == 'update' ? 'Atualizar' : 'Salvar'}
      </button>
      <a href="${pageContext.request.contextPath}/pets" class="btn btn-default">Cancelar</a>
    </form>
  </div>

  <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
