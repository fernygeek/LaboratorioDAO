<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Usuarios</title>
    <style>
        * { box-sizing: border-box; }
        body {
            min-height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            color: #252525;
            background: linear-gradient(135deg, #eef6ff 0%, #e7edff 100%);
            padding: 40px;
        }
        .page-card {
            width: min(1120px, 100%);
            margin: 0 auto;
            background: #fff;
            border: 1px solid #d9dfe8;
            border-radius: 4px;
            box-shadow: 0 8px 22px rgba(37, 56, 88, 0.18);
            padding: 34px;
        }
        .page-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 18px;
            margin-bottom: 24px;
        }
        h2 { margin: 0; font-size: 32px; font-weight: 400; }
        .table-wrap {
            overflow-x: auto;
            border: 1px solid #e1e5eb;
            border-radius: 4px;
        }
        table { width: 100%; border-collapse: collapse; min-width: 720px; }
        th, td { padding: 16px 18px; text-align: left; border-bottom: 1px solid #e8ebf0; }
        th {
            background-color: #f5f5f5;
            color: #606060;
            font-size: 13px;
            text-transform: uppercase;
            letter-spacing: 0;
        }
        tr:last-child td { border-bottom: 0; }
        tbody tr:hover { background-color: #f9fbff; }
        .actions-cell { display: flex; gap: 8px; flex-wrap: wrap; }
        .profile-badge {
            display: inline-flex;
            align-items: center;
            min-height: 28px;
            padding: 5px 12px;
            border-radius: 999px;
            background: #eef6ff;
            border: 1px solid #b9d7f8;
            color: #155fa6;
            font-size: 13px;
            font-weight: bold;
            line-height: 1;
            white-space: nowrap;
        }
        .btn {
            min-height: 38px;
            padding: 10px 14px;
            background-color: #1976d2;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 7px;
            font-weight: bold;
            border: 1px solid transparent;
            box-shadow: 0 2px 5px rgba(25, 118, 210, 0.22);
        }
        .btn:hover { background-color: #1568bd; }
        .btn-create {
            background-color: #2e9e5d;
            border-color: #2e9e5d;
            color: #fff;
            text-transform: uppercase;
            box-shadow: 0 3px 8px rgba(46, 158, 93, 0.24);
        }
        .btn-create:hover { background-color: #26894f; border-color: #26894f; }
        .btn-warning {
            background-color: #fffaf0;
            border-color: #f2b632;
            color: #9a6a00;
            box-shadow: none;
        }
        .btn-warning:hover {
            background-color: #fff2cc;
            border-color: #dda527;
            color: #7c5500;
        }
        .btn-danger {
            background-color: #fff4f5;
            border-color: #e79aa0;
            color: #c7353d;
            box-shadow: none;
        }
        .btn-danger:hover {
            background-color: #ffe9eb;
            border-color: #d73a31;
            color: #a92a31;
        }
        .empty-row { color: #777; background: #fafafa; }
        .mensaje {
            color: #8a1f1f;
            background-color: #fff0f0;
            border: 1px solid #f1c7c7;
            padding: 12px 14px;
            border-radius: 4px;
            margin-bottom: 18px;
        }
        @media (max-width: 720px) {
            body { padding: 18px; }
            .page-card { padding: 24px 18px; }
            .page-header { align-items: flex-start; flex-direction: column; }
        }
    </style>
</head>
<body>
    <main class="page-card">
        <div class="page-header">
            <h2>Gestión de Usuarios</h2>
            <a href="${pageContext.request.contextPath}/gestionar?ruta=new" class="btn btn-create"><span aria-hidden="true">+</span>Crear Nuevo Usuario</a>
        </div>

        <c:if test="${not empty mensaje}">
            <div class="mensaje"><c:out value="${mensaje}" /></div>
        </c:if>

        <div class="table-wrap">
            <table>
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Correo</th>
                        <th>Perfil</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="u" items="${listaUsuarios}">
                        <tr>
                            <td><c:out value="${u.nombre}" /></td>
                            <td><c:out value="${u.correo}" /></td>
                            <td><span class="profile-badge"><c:out value="${u.perfil}" /></span></td>
                            <td class="actions-cell">
                                <a class="btn btn-warning" href="${pageContext.request.contextPath}/gestionar?ruta=update&idUsuario=${u.idUsuario}">Actualizar</a>
                                <a class="btn btn-danger" href="${pageContext.request.contextPath}/gestionar?ruta=delete&idUsuario=${u.idUsuario}"><span aria-hidden="true">🗑</span>Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty listaUsuarios}">
                        <tr class="empty-row">
                            <td colspan="4" style="text-align:center;">No hay usuarios registrados.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
