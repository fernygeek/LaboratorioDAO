<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nuevo Usuario</title>
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
        .page-card {
            width: min(720px, 100%);
            background: #fff;
            border: 1px solid #d9dfe8;
            border-radius: 4px;
            box-shadow: 0 8px 22px rgba(37, 56, 88, 0.18);
            padding: 40px 44px;
        }
        .header-icon {
            width: 48px;
            height: 48px;
            margin: 0 auto 18px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            background: #eef6ff;
            color: #1976d2;
            font-size: 32px;
        }
        h2 {
            margin: 0 0 8px;
            font-size: 32px;
            font-weight: 400;
            line-height: 1.18;
            text-align: center;
        }
        .subtitle { margin: 0 0 30px; color: #757575; text-align: center; }
        .form-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 18px; }
        .form-group { margin: 0; }
        label { display: block; margin-bottom: 7px; color: #555; font-weight: bold; font-size: 14px; }
        input, select {
            width: 100%;
            min-height: 48px;
            padding: 0 14px;
            border: 1px solid #cfcfcf;
            border-radius: 4px;
            background: #fff;
            color: #252525;
            font-size: 15px;
        }
        input:focus, select:focus {
            outline: none;
            border-color: #1976d2;
            box-shadow: 0 0 0 3px rgba(25, 118, 210, 0.14);
        }
        #camposEmpleado {
            grid-column: 1 / -1;
            display: grid;
            grid-template-columns: repeat(2, minmax(0, 1fr));
            gap: 18px;
            padding: 18px;
            background: #f5f5f5;
            border-radius: 4px;
        }
        #camposEmpleado .form-group { grid-column: 1 / -1; }
        .actions { display: flex; gap: 12px; margin-top: 28px; }
        .btn, .btn-secondary {
            flex: 1;
            padding: 14px 18px;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            text-align: center;
            text-decoration: none;
        }
        .btn {
            background-color: #1976d2;
            box-shadow: 0 3px 6px rgba(25, 118, 210, 0.28);
        }
        .btn:hover { background-color: #1568bd; }
        .btn-secondary { background-color: #6f7785; }
        .btn-secondary:hover { background-color: #5e6673; }
        .mensaje {
            color: #8a1f1f;
            background-color: #fff0f0;
            border: 1px solid #f1c7c7;
            padding: 12px 14px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        @media (max-width: 720px) {
            body { padding: 18px; }
            .page-card { padding: 28px 22px; }
            .form-grid, #camposEmpleado { grid-template-columns: 1fr; }
            .actions { flex-direction: column; }
        }
    </style>
</head>
<body>
    <main class="page-card">
        <div class="header-icon" aria-hidden="true">👤</div>
        <h2>Nuevo Usuario</h2>
        <p class="subtitle">Ingrese la informacion del usuario</p>

        <c:if test="${not empty mensaje}">
            <div class="mensaje"><c:out value="${mensaje}" /></div>
        </c:if>

        <form id="formUsuario" action="${pageContext.request.contextPath}/gestionar?ruta=guardarAdministrador" method="post">
            <div class="form-grid">
                <div class="form-group">
                    <label for="nombre">Nombre:</label>
                    <input type="text" id="nombre" name="nombre" required />
                </div>
                <div class="form-group">
                    <label for="clave">Clave:</label>
                    <input type="password" id="clave" name="clave" required />
                </div>
                <div class="form-group">
                    <label for="correo">Correo:</label>
                    <input type="email" id="correo" name="correo" required />
                </div>
                <div class="form-group">
                    <label for="perfil">Perfil:</label>
                    <select id="perfil" name="perfil" required>
                        <option value="Administrador">Administrador</option>
                        <option value="Empleado">Empleado</option>
                    </select>
                </div>
                <div id="camposEmpleado">
                    <div class="form-group">
                        <label for="idDepartamento">Departamento:</label>
                        <select id="idDepartamento" name="idDepartamento">
                            <option value="">Seleccione un departamento</option>
                            <c:forEach var="d" items="${departamentos}">
                                <option value="${d.idDepartamento}">
                                    <c:out value="${d.nombre}" /> - Piso <c:out value="${d.numeroPiso}" />
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <div class="actions">
                <button type="submit" class="btn">Guardar Usuario</button>
                <a href="${pageContext.request.contextPath}/gestionar?ruta=ingresar" class="btn-secondary">Cancelar</a>
            </div>
        </form>
    </main>

    <script>
        const contexto = '${pageContext.request.contextPath}';
        const perfil = document.getElementById('perfil');
        const form = document.getElementById('formUsuario');
        const camposEmpleado = document.getElementById('camposEmpleado');
        const idDepartamento = document.getElementById('idDepartamento');

        function actualizarFormulario() {
            const esEmpleado = perfil.value === 'Empleado';
            camposEmpleado.style.display = esEmpleado ? 'grid' : 'none';
            idDepartamento.required = esEmpleado;
            form.action = contexto + '/gestionar?ruta=' + (esEmpleado ? 'guardarEmpleado' : 'guardarAdministrador');
        }

        perfil.addEventListener('change', actualizarFormulario);
        actualizarFormulario();
    </script>
</body>
</html>
