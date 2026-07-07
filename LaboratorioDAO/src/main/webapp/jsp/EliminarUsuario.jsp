<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Eliminar Usuario</title>
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
        .confirm-card {
            width: min(520px, 100%);
            background: #fff;
            border: 1px solid #d9dfe8;
            border-radius: 4px;
            box-shadow: 0 8px 22px rgba(37, 56, 88, 0.18);
            padding: 40px;
            text-align: center;
        }
        .warn-icon {
            width: 48px;
            height: 48px;
            margin: 0 auto 18px;
            border-radius: 50%;
            background: #fff0f0;
            color: #d73a31;
            border: 1px solid #f1c7c7;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            font-weight: bold;
        }
        h2 { margin: 0 0 12px; font-size: 30px; font-weight: 400; }
        p { margin: 0 0 28px; color: #666; line-height: 1.5; }
        .actions { display: flex; gap: 12px; justify-content: center; }
        .btn {
            min-width: 140px;
            padding: 14px 18px;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            display: inline-block;
            border: none;
            cursor: pointer;
            font-weight: bold;
            text-align: center;
        }
        .btn-danger { background-color: #d73a31; box-shadow: 0 3px 6px rgba(215, 58, 49, 0.22); }
        .btn-danger:hover { background-color: #bf3028; }
        .btn-secondary { background-color: #6f7785; }
        .btn-secondary:hover { background-color: #5e6673; }
        @media (max-width: 520px) {
            .confirm-card { padding: 30px 22px; }
            .actions { flex-direction: column; }
            .btn { width: 100%; }
        }
    </style>
</head>
<body>
    <main class="confirm-card">
        <div class="warn-icon">!</div>
        <h2>Confirmar Eliminación</h2>
        <p>¿Está seguro de que desea eliminar este usuario?</p>

        <div class="actions">
            <form action="${pageContext.request.contextPath}/gestionar?ruta=confirmarEliminacion" method="post" style="display:inline;">
                <input type="hidden" name="idUsuario" value="${idUsuario}" />
                <button type="submit" class="btn btn-danger">Confirmar</button>
            </form>
            <a href="${pageContext.request.contextPath}/gestionar?ruta=cancelarEliminacion" class="btn btn-secondary">Cancelar</a>
        </div>
    </main>
</body>
</html>
