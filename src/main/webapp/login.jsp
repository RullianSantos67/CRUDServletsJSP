<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Login - CRUD Manager</title>
    <!-- Bootstrap 3.4.1 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css"/>
    <!-- Font Awesome para ícones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
</head>
<body style="background-color: #f5f5f5; padding-top: 50px;">
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="panel panel-default" style="margin-top: 50px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                    <div class="panel-body">
                        <div class="text-center">
                            <h3>Faça seu login</h3>
                        </div>
                        
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger alert-dismissible">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                ${errorMessage}
                            </div>
                        </c:if>
                        
                        <form action="${pageContext.request.contextPath}/login" method="post">
                            <div class="form-group">
                                <label for="email">E-mail:</label>
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <i class="fas fa-envelope"></i>
                                    </span>
                                    <input type="email" class="form-control" id="email" name="email" 
                                           required autofocus placeholder="Digite seu e-mail"/>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="password">Senha:</label>
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <i class="fas fa-lock"></i>
                                    </span>
                                    <input type="password" class="form-control" id="password" name="password" 
                                           required placeholder="Digite sua senha"/>
                                </div>
                            </div>
                            
                            <button type="submit" class="btn btn-danger btn-block" style="margin-top: 20px;">
                                <i class="fas fa-sign-in-alt"></i> Entrar
                            </button>
                        </form>
                        
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Scripts JavaScript -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/js/bootstrap.min.js"></script>
    
    <script>
        $(document).ready(function() {
           
            setTimeout(function() {
                $(".alert").alert('close');
            }, 5000);
        });
    </script>
</body>
</html>