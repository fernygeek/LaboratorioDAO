<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        * { box-sizing: border-box; }
        body {
            min-height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            color: #252525;
            background: linear-gradient(135deg, #eef6ff 0%, #e7edff 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 32px;
        }
        .login-card {
            width: min(560px, 100%);
            background: #fff;
            border: 1px solid #d9dfe8;
            border-radius: 4px;
            box-shadow: 0 8px 22px rgba(37, 56, 88, 0.18);
            padding: 44px;
        }
        .header-icon {
            width: 42px;
            height: 42px;
            margin: 0 auto 22px;
            border: 3px solid #1976d2;
            border-left: 0;
            border-radius: 4px;
            position: relative;
        }
        .header-icon::before {
            content: "";
            position: absolute;
            width: 18px;
            height: 3px;
            background: #1976d2;
            left: -10px;
            top: 18px;
        }
        .header-icon::after {
            content: "";
            position: absolute;
            width: 12px;
            height: 12px;
            border-right: 3px solid #1976d2;
            border-top: 3px solid #1976d2;
            left: 0;
            top: 13px;
            transform: rotate(45deg);
        }
        h2 {
            margin: 0 0 10px;
            font-size: 34px;
            font-weight: 400;
            line-height: 1.18;
            text-align: center;
        }
        .subtitle {
            margin: 0 0 34px;
            color: #757575;
            text-align: center;
            font-size: 16px;
        }
        .form-group { margin-bottom: 22px; }
        label {
            display: block;
            margin-bottom: 7px;
            color: #555;
            font-weight: bold;
            font-size: 14px;
        }
        input {
            width: 100%;
            min-height: 54px;
            padding: 0 16px;
            border: 1px solid #cfcfcf;
            border-radius: 4px;
            background: #fff;
            color: #252525;
            font-size: 16px;
        }
        input:focus {
            outline: none;
            border-color: #1976d2;
            box-shadow: 0 0 0 3px rgba(25, 118, 210, 0.14);
        }
        .btn {
            width: 100%;
            padding: 15px 18px;
            background-color: #1976d2;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            box-shadow: 0 3px 6px rgba(25, 118, 210, 0.28);
        }
        .btn:hover { background-color: #1568bd; }
        .mensaje {
            color: #8a1f1f;
            background-color: #fff0f0;
            border: 1px solid #f1c7c7;
            padding: 12px 14px;
            border-radius: 4px;
            margin-bottom: 22px;
        }
        @media (max-width: 560px) {
            body { padding: 18px; }
            .login-card { padding: 30px 22px; }
            h2 { font-size: 30px; }
        }
    </style>
</head>
<body>
    <main class="login-card">
        <div class="header-icon"></div>
        <h2>Inicio de Sesión</h2>
        <p class="subtitle">Ingrese sus credenciales para continuar</p>

        <c:if test="${not empty mensaje}">
            <div class="mensaje"><c:out value="${mensaje}" /></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/autenticar?ruta=solicitarIngreso" method="post">
            <div class="form-group">
                <label for="nombre">Nombre de usuario:</label>
                <input type="text" id="nombre" name="nombre" required />
            </div>
            <div class="form-group">
                <label for="clave">Clave:</label>
                <input type="password" id="clave" name="clave" required />
            </div>
            <button type="submit" class="btn">Ingresar</button>
        </form>
    </main>
</body>
</html>
