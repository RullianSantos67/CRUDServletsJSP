<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>CRUD Manager</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/js/bootstrap.min.js"></script>


<style> 
 .btn-login:hover {
            background-color: #c9302c;
            border-color: #ac2925;
        }
        .footer-links {
            text-align: center;
            margin-top: 20px;
        }
        
        body {
            padding-top: 70px;
        }

        .navbar-brand img {
            margin-top: -5px;
            width: 40px;
    		height: 40px;
        }


        .navbar-nav > li > a > .fas {
            margin-right: 6px;
            width: 20px;
            text-align: center;
            padding: 15px 20px;
        }
        
</style>

    
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}">
                <img height="30px" width="30px" src="${pageContext.request.contextPath}/images/logo.png" alt="PM">
                <strong>&nbsp;CRUD Manager</strong>
            </a>
        </div>

        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}/pets"><i class="fas fa-paw"></i>Pets</a></li>
                <li><a href="${pageContext.request.contextPath}/index.jsp"><i class="fas fa-home"></i>Início</a></li>
                <li><a href="${pageContext.request.contextPath}/users"><i class="fas fa-users"></i>Usuários</a></li>
                <li><a href="${pageContext.request.contextPath}/posts"><i class="fas fa-pencil-alt"></i>Posts</a></li>
                <li><a href="${pageContext.request.contextPath}/companies"><i class="fas fa-building"></i>Empresas</a></li>

                <%
                    Object usuario = session.getAttribute("usuarioLogado");
                    if (usuario != null) {
                %>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fas fa-user-circle"></i> Perfil <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="#"><i class="fas fa-cog"></i> Dados cadastrais</a></li>
                            <li><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Sair</a></li>
                        </ul>
                    </li>
                <%
                    } else {
                %>
                    <li><a href="${pageContext.request.contextPath}/login"><i class="fas fa-sign-in-alt"></i>Entrar</a></li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
 
</div>

</body>
</html>
