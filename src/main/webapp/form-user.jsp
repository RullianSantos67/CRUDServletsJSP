<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <%@ include file="base-head.jsp" %>
</head>
<body>
    <%@ include file="nav-menu.jsp" %>

    <div id="container" class="container-fluid">
        <h3 class="page-header">${not empty user.id ? "Editar Usuário" : "Adicionar Usuário"}</h3>

        <form action="${pageContext.request.contextPath}/user/${action}" method="POST">
            <input type="hidden" name="userId" value="${user.id != null ? user.id : ''}">

            <div class="row">
                <div class="form-group col-md-4">
                    <label for="name">Nome</label>
                    <input type="text" class="form-control" id="name" name="name" 
                           placeholder="Nome do usuário" required
                           oninvalid="this.setCustomValidity('Por favor, informe o nome do usuário.')"
                           oninput="setCustomValidity('')"
                           value="${user.nome != null ? user.nome : ''}">
                </div>

                <div class="form-group col-md-4">
                    <label for="gender">Sexo</label>
                    <select id="gender" class="form-control selectpicker" name="gender" required
                            oninvalid="this.setCustomValidity('Por favor, informe o sexo')"
                            oninput="setCustomValidity('')">
                        <option value="" disabled ${user.sexo == null ? "selected" : ""}>Selecione o sexo</option>
                        <option value="F" ${user.sexo == 'F' ? "selected" : ""}>Feminino</option>
                        <option value="M" ${user.sexo == 'M' ? "selected" : ""}>Masculino</option>
                    </select>
                </div>

                <div class="form-group col-md-4">
                    <label for="mail">E-mail</label>
                    <input type="email" class="form-control" id="mail" name="mail" 
                           placeholder="E-mail do usuário" required
                           oninvalid="this.setCustomValidity('Por favor, informe o email do usuário.')"
                           oninput="setCustomValidity('')"
                           value="${user.email != null ? user.email : ''}">
                </div>
            </div>

            <div class="row">
                <div class="form-group col-md-4">
                    <label for="password">Senha</label>
                    <input type="password" class="form-control" id="password" name="password"
                           placeholder="${user.id != null ? 'Deixe em branco para manter a senha' : 'Senha'}"
                           ${user.id == null ? "required" : ""}
                           oninvalid="this.setCustomValidity('Por favor, informe a senha.')"
                           oninput="setCustomValidity('')">
                </div>
            </div>

            <hr />
            <div id="actions" class="row pull-right">
                <div class="col-md-12">
                    <a href="${pageContext.request.contextPath}/users" class="btn btn-default">Cancelar</a>
                    <button type="submit" class="btn btn-primary">
                        ${not empty user.id ? "Alterar Usuário" : "Cadastrar Usuário"}
                    </button>
                </div>
            </div>
        </form>
    </div>

    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
