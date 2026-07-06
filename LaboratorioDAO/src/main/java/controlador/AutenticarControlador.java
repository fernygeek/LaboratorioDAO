package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.entities.Administrador;
import modelo.entities.Usuario;

@WebServlet("/autenticar")
public class AutenticarControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ruteador(req, resp);
	}

	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = req.getParameter("ruta");
		if (ruta == null || "entrar".equals(ruta)) {
			entrar(req, resp);
			return;
		}

		if ("solicitarIngreso".equals(ruta)) {
			solicitarIngreso(req.getParameter("nombre"), req.getParameter("clave"), req, resp);
			return;
		}

		resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
	}

	public void entrar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		mostrar(req, resp);
	}

	public void solicitarIngreso(String nombre, String clave, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Usuario usuario = Usuario.autenticar(nombre, clave);

		if (usuario == null) {
			mostrar("Usuario o clave incorrectos", req, resp);
			return;
		}

		if (!(usuario instanceof Administrador)) {
			HttpSession sesion = req.getSession(false);
			if (sesion != null) {
				sesion.invalidate();
			}
			mostrar("Por ahora solo los administradores pueden iniciar sesión.", req, resp);
			return;
		}

		crearSesion(usuario, req);
		listar(resp, req);
	}

	public void crearSesion(Usuario usuario, HttpServletRequest req) {
		HttpSession sesion = req.getSession(true);
		sesion.setAttribute("usuarioSesion", usuario);
	}

	public void listar(HttpServletResponse resp, HttpServletRequest req) throws IOException {
		resp.sendRedirect(req.getContextPath() + "/gestionar?ruta=ingresar");
	}

	private void mostrar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/jsp/Login.jsp").forward(req, resp);
	}

	private void mostrar(String mensaje, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("mensaje", mensaje);
		mostrar(req, resp);
	}
}
