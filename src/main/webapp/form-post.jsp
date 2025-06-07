<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp" %>
</head>
<body>
	<%@include file="modal.html" %>
	<%@include file="nav-menu.jsp" %>

	<div id="container" class="container-fluid">
		<div id="top" class="row">
			<div class="col-md-12">
				<h2>${action == 'insert' ? 'Novo Post' : 'Editar Post'}</h2>
			</div>
		</div>

		<hr />

		<div id="form" class="row">
			<div class="col-md-12">
				<form action="${pageContext.request.contextPath}/post/${action}" method="post" id="form">
					<input type="hidden" name="id" id="id" value="${post != null ? post.getId() : ''}" />

					<div class="form-group">
						<label for="title">Título</label>
						<input type="text" class="form-control" name="title" id="title"
							value="${post != null ? post.getTitle() : ''}" required />
					</div>

					<div class="form-group">
						<label for="content">Conteúdo</label>
						<textarea class="form-control" name="content" id="content" rows="5" required>${post != null ? post.getContent() : ''}</textarea>
					</div>

					<div class="form-group">
						<label for="userId">Usuário</label>
						<select name="userId" id="userId" class="form-control" required>
							<option value="" disabled ${post == null ? 'selected' : ''}>Selecione um usuário</option>
							<c:forEach var="user" items="${users}">
								<option value="${user.getId()}"
									${post != null && post.getUser().getId() == user.getId() ? 'selected' : ''}>
									${user.getNome()}
								</option>
							</c:forEach>
						</select>
					</div>

					<div class="form-group">
						<button type="submit" class="btn btn-primary">Salvar</button>
						<a href="${pageContext.request.contextPath}/posts" class="btn btn-default">Cancelar</a>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
