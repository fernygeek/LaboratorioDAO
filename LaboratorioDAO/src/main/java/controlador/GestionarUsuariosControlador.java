package controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.UsuarioDAO;
import modelo.entities.Administrador;
import modelo.entities.Empleado;
import modelo.entities.Usuario;

@WebServlet("/gestionar")
public class GestionarUsuariosControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final UsuarioDAO usuarioDAO = new UsuarioDAO();

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
		if (ruta == null) {
			ruta = "ingresar";
		}

		if (!validarAdministrador(req, resp)) {
			return;
		}

		switch (ruta) {
			case "ingresar" -> ingresar(req, resp);
			case "new", "newUser" -> nuevo(req, resp);
			case "seleccionarPerfilEmpleado" -> seleccionarPerfilEmpleado(req, resp);
			case "guardarAdministrador" -> guardarInformacionAdministrador(req, resp);
			case "guardarEmpleado" -> guardarInformacionEmpleado(req, resp);
			case "update", "updateUser" -> update(req, resp);
			case "actualizarAdministrador" -> actualizarInformacionAdministrador(req, resp);
			case "actualizarEmpleado" -> actualizarInformacionEmpleado(req, resp);
			case "delete", "deleteUser" -> delete(req, resp);
			case "confirmarEliminacion" -> confirmarEliminacion(req, resp);
			case "cancelarEliminacion" -> cancelarEliminacion(req, resp);
			default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
		}
	}

	private boolean validarAdministrador(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession sesion = req.getSession(false);
		Usuario usuarioSesion = sesion != null ? (Usuario) sesion.getAttribute("usuarioSesion") : null;

		if (usuarioSesion instanceof Administrador) {
			return true;
		}

		if (usuarioSesion == null) {
			resp.sendRedirect(req.getContextPath() + "/autenticar?ruta=entrar");
			return false;
		}

		req.setAttribute("mensaje", "Por ahora solo los administradores pueden ingresar al sistema.");
		req.getRequestDispatcher("/jsp/Login.jsp").forward(req, resp);
		return false;
	}

	public void ingresar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		mostrarListaUsuarios(usuarioDAO.consultarUsuarios(), req, resp);
	}

	public void nuevo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		mostrarFormularioNuevoUsuario(req, resp);
	}

	public void seleccionarPerfilEmpleado(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("mostrarCamposEmpleado", true);
		mostrarFormularioNuevoUsuario(req, resp);
	}

	public void guardarInformacionAdministrador(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nombre = req.getParameter("nombre");
		String clave = req.getParameter("clave");
		String correo = req.getParameter("correo");
		guardarInformacionAdministrador(nombre, clave, correo, req, resp);
	}

	public void guardarInformacionAdministrador(String nombre, String clave, String correo, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		if (usuarioDAO.validarCorreoDuplicado(correo)) {
			mostrarMensajeNuevo("Ya existe un usuario con ese correo", req, resp);
			return;
		}

		usuarioDAO.registrarAdministrador(nombre, clave, correo);
		mostrarListaUsuarios(usuarioDAO.consultarUsuarios(), req, resp);
	}

	public void guardarInformacionEmpleado(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nombre = req.getParameter("nombre");
		String clave = req.getParameter("clave");
		String correo = req.getParameter("correo");
		Integer idDepartamento = leerIdDepartamento(req);
		guardarInformacionEmpleado(nombre, clave, correo, idDepartamento, req, resp);
	}

	public void guardarInformacionEmpleado(String nombre, String clave, String correo, Integer idDepartamento,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (usuarioDAO.validarCorreoDuplicado(correo)) {
			mostrarMensajeNuevo("Ya existe un usuario con ese correo", req, resp);
			return;
		}

		if (!usuarioDAO.registrarEmpleado(nombre, clave, correo, idDepartamento)) {
			mostrarMensajeNuevo("Seleccione un departamento existente", req, resp);
			return;
		}

		mostrarListaUsuarios(usuarioDAO.consultarUsuarios(), req, resp);
	}

	public void guardarInformacionEmpleado(String nombre, String clave, String correo, String departamento, String piso,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		mostrarMensajeNuevo("Seleccione un departamento existente", req, resp);
	}

	public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer idUsuario = leerIdUsuario(req);
		Usuario usuario = usuarioDAO.obtenerUsuario(idUsuario);
		mostrarFormularioActualizacion(usuario, req, resp);
	}

	public void actualizarInformacionAdministrador(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer idUsuario = leerIdUsuario(req);
		String nombre = req.getParameter("nombre");
		String clave = req.getParameter("clave");
		String correo = req.getParameter("correo");
		actualizarSegunTipoActual(idUsuario, nombre, clave, correo, leerIdDepartamento(req), req, resp);
	}

	public void actualizarInformacionAdministrador(Integer idUsuario, String nombre, String clave, String correo,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (usuarioDAO.validarUsuarioDuplicado(idUsuario, nombre, correo)) {
			mostrarMensajeActualizacion("Ya existe un usuario con la informacion ingresada",
					usuarioDAO.obtenerUsuario(idUsuario), req, resp);
			return;
		}

		usuarioDAO.actualizarAdministrador(idUsuario, nombre, clave, correo);
		mostrarListaUsuarios(usuarioDAO.consultarUsuarios(), req, resp);
	}

	public void actualizarInformacionEmpleado(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer idUsuario = leerIdUsuario(req);
		String nombre = req.getParameter("nombre");
		String clave = req.getParameter("clave");
		String correo = req.getParameter("correo");
		Integer idDepartamento = leerIdDepartamento(req);
		actualizarSegunTipoActual(idUsuario, nombre, clave, correo, idDepartamento, req, resp);
	}

	private void actualizarSegunTipoActual(Integer idUsuario, String nombre, String clave, String correo,
			Integer idDepartamento, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Usuario usuarioActual = usuarioDAO.obtenerUsuario(idUsuario);
		if (usuarioActual instanceof Administrador) {
			actualizarInformacionAdministrador(idUsuario, nombre, clave, correo, req, resp);
			return;
		}
		if (usuarioActual instanceof Empleado) {
			actualizarInformacionEmpleado(idUsuario, nombre, clave, correo, idDepartamento, req, resp);
			return;
		}
		mostrarMensajeActualizacion("No se pudo identificar el perfil actual del usuario", usuarioActual, req, resp);
	}

	public void actualizarInformacionEmpleado(Integer idUsuario, String nombre, String clave, String correo,
			Integer idDepartamento, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (usuarioDAO.validarUsuarioDuplicado(idUsuario, nombre, correo)) {
			mostrarMensajeActualizacion("Ya existe un usuario con la informacion ingresada",
					usuarioDAO.obtenerUsuario(idUsuario), req, resp);
			return;
		}

		if (!usuarioDAO.actualizarEmpleado(idUsuario, nombre, clave, correo, idDepartamento)) {
			mostrarMensajeActualizacion("Seleccione un departamento existente",
					usuarioDAO.obtenerUsuario(idUsuario), req, resp);
			return;
		}

		mostrarListaUsuarios(usuarioDAO.consultarUsuarios(), req, resp);
	}

	public void actualizarInformacionEmpleado(Integer idUsuario, String nombre, String clave, String correo,
			String departamento, String piso, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		mostrarMensajeActualizacion("Seleccione un departamento existente",
				usuarioDAO.obtenerUsuario(idUsuario), req, resp);
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer idUsuario = leerIdUsuario(req);
		req.setAttribute("idUsuario", idUsuario);
		mostrarConfirmacionEliminacion(req, resp);
	}

	public void confirmarEliminacion(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer idUsuario = leerIdUsuario(req);
		Usuario usuarioSesion = (Usuario) req.getSession().getAttribute("usuarioSesion");
		if (usuarioSesion != null && idUsuario.equals(usuarioSesion.getIdUsuario())) {
			mostrarMensajeLista("No puede eliminar el usuario con el que ha iniciado sesión.", req, resp);
			return;
		}

		usuarioDAO.eliminarUsuario(idUsuario);
		mostrarListaUsuarios(usuarioDAO.consultarUsuarios(), req, resp);
	}

	public void cancelarEliminacion(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		mostrarListaUsuarios(usuarioDAO.consultarUsuarios(), req, resp);
	}

	private void mostrarListaUsuarios(List<Usuario> listaUsuarios, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("listaUsuarios", listaUsuarios);
		req.getRequestDispatcher("/jsp/ListaDeUsuarios.jsp").forward(req, resp);
	}

	private void mostrarMensajeLista(String mensaje, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("mensaje", mensaje);
		mostrarListaUsuarios(usuarioDAO.consultarUsuarios(), req, resp);
	}

	private void mostrarFormularioNuevoUsuario(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("departamentos", usuarioDAO.consultarDepartamentos());
		req.getRequestDispatcher("/jsp/NuevoUsuario.jsp").forward(req, resp);
	}

	private void mostrarFormularioActualizacion(Usuario usuario, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("usuario", usuario);
		req.setAttribute("departamentos", usuarioDAO.consultarDepartamentos());
		req.getRequestDispatcher("/jsp/ActualizarUsuario.jsp").forward(req, resp);
	}

	private void mostrarConfirmacionEliminacion(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/jsp/EliminarUsuario.jsp").forward(req, resp);
	}

	private void mostrarMensajeNuevo(String mensaje, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("mensaje", mensaje);
		mostrarFormularioNuevoUsuario(req, resp);
	}

	private void mostrarMensajeActualizacion(String mensaje, Usuario usuario, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mensaje", mensaje);
		mostrarFormularioActualizacion(usuario, req, resp);
	}

	private Integer leerIdUsuario(HttpServletRequest req) {
		return Integer.valueOf(req.getParameter("idUsuario"));
	}

	private Integer leerIdDepartamento(HttpServletRequest req) {
		String idDepartamento = req.getParameter("idDepartamento");
		if (idDepartamento == null || idDepartamento.isBlank()) {
			return null;
		}
		return Integer.valueOf(idDepartamento);
	}
}
