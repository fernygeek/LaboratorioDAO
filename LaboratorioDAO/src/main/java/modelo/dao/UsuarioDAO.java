package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entities.Administrador;
import modelo.entities.Departamento;
import modelo.entities.Empleado;
import modelo.entities.Usuario;

public class UsuarioDAO {

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("mundial");

	private EntityManager crearEntityManager() {
		return emf.createEntityManager();
	}

	public Usuario autenticar(String nombre, String clave) {
		EntityManager em = crearEntityManager();
		try {
			TypedQuery<Usuario> consulta = em.createQuery(
					"SELECT u FROM Usuario u WHERE u.nombre = :nombre AND u.clave = :clave", Usuario.class);
			consulta.setParameter("nombre", nombre);
			consulta.setParameter("clave", clave);
			return consulta.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}

	public List<Usuario> consultarUsuarios() {
		EntityManager em = crearEntityManager();
		try {
			return em.createQuery("SELECT u FROM Usuario u ORDER BY u.idUsuario", Usuario.class).getResultList();
		} finally {
			em.close();
		}
	}

	public List<Departamento> consultarDepartamentos() {
		EntityManager em = crearEntityManager();
		try {
			return em.createQuery("SELECT d FROM Departamento d ORDER BY d.nombre", Departamento.class).getResultList();
		} finally {
			em.close();
		}
	}

	public boolean validarCorreoDuplicado(String correo) {
		EntityManager em = crearEntityManager();
		try {
			Long total = em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.correo = :correo", Long.class)
					.setParameter("correo", correo)
					.getSingleResult();
			return total > 0;
		} finally {
			em.close();
		}
	}

	public boolean registrarAdministrador(String nombre, String clave, String correo) {
		return guardar(new Administrador(nombre, clave, correo));
	}

	public boolean registrarAdministrador(String nombre, String clave, String correo, String perfil) {
		return registrarAdministrador(nombre, clave, correo);
	}

	public boolean registrarEmpleado(String nombre, String clave, String correo, String nombreDepartamento,
			String piso) {
		return false;
	}

	public boolean registrarEmpleado(String nombre, String clave, String correo, Integer idDepartamento) {
		EntityManager em = crearEntityManager();
		try {
			em.getTransaction().begin();
			Departamento departamento = em.find(Departamento.class, idDepartamento);
			if (departamento == null) {
				em.getTransaction().rollback();
				return false;
			}
			em.persist(new Empleado(nombre, clave, correo, departamento));
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			return false;
		} finally {
			em.close();
		}
	}

	public boolean registrarEmpleado(String nombre, String clave, String correo, String perfil,
			Integer idDepartamento) {
		return registrarEmpleado(nombre, clave, correo, idDepartamento);
	}

	public boolean registrarEmpleado(String nombre, String clave, String correo, String perfil,
			String nombreDepartamento, String piso) {
		return registrarEmpleado(nombre, clave, correo, nombreDepartamento, piso);
	}

	public Usuario obtenerUsuario(Integer idUsuario) {
		EntityManager em = crearEntityManager();
		try {
			return em.find(Usuario.class, idUsuario);
		} finally {
			em.close();
		}
	}

	public boolean validarUsuarioDuplicado(Integer idUsuario, String nombre, String correo) {
		EntityManager em = crearEntityManager();
		try {
			TypedQuery<Long> consulta = em.createQuery(
					"SELECT COUNT(u) FROM Usuario u WHERE u.idUsuario <> :idUsuario "
							+ "AND (u.correo = :correo OR u.nombre = :nombre)",
					Long.class);
			consulta.setParameter("idUsuario", idUsuario);
			consulta.setParameter("correo", correo);
			consulta.setParameter("nombre", nombre);
			return consulta.getSingleResult() > 0;
		} finally {
			em.close();
		}
	}

	public boolean validarUsuarioDuplicado(Integer idUsuario, String nombre, String correo, String perfil) {
		return validarUsuarioDuplicado(idUsuario, nombre, correo);
	}

	public boolean validarUsuarioDuplicado(Integer idUsuario, String nombre, String correo, String perfil,
			String departamento, String piso) {
		return validarUsuarioDuplicado(idUsuario, nombre, correo);
	}

	public boolean actualizarAdministrador(Integer idUsuario, String nombre, String clave, String correo) {
		EntityManager em = crearEntityManager();
		try {
			em.getTransaction().begin();
			Usuario usuario = em.find(Usuario.class, idUsuario);
			if (usuario == null) {
				em.getTransaction().rollback();
				return false;
			}
			usuario.setNombre(nombre);
			usuario.setClave(clave);
			usuario.setCorreo(correo);
			em.merge(usuario);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			return false;
		} finally {
			em.close();
		}
	}

	public boolean actualizarAdministrador(Integer idUsuario, String nombre, String clave, String correo,
			String perfil) {
		return actualizarAdministrador(idUsuario, nombre, clave, correo);
	}

	public boolean actualizarEmpleado(Integer idUsuario, String nombre, String clave, String correo,
			String nombreDepartamento, String piso) {
		return false;
	}

	public boolean actualizarEmpleado(Integer idUsuario, String nombre, String clave, String correo,
			Integer idDepartamento) {
		EntityManager em = crearEntityManager();
		try {
			em.getTransaction().begin();
			Usuario usuario = em.find(Usuario.class, idUsuario);
			Departamento departamento = em.find(Departamento.class, idDepartamento);
			if (usuario == null || departamento == null) {
				em.getTransaction().rollback();
				return false;
			}
			usuario.setNombre(nombre);
			usuario.setClave(clave);
			usuario.setCorreo(correo);
			if (usuario instanceof Empleado empleado) {
				empleado.setDepartamento(departamento);
				em.merge(empleado);
			} else {
				em.merge(usuario);
			}
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			return false;
		} finally {
			em.close();
		}
	}

	public boolean actualizarEmpleado(Integer idUsuario, String nombre, String clave, String correo, String perfil,
			Integer idDepartamento) {
		return actualizarEmpleado(idUsuario, nombre, clave, correo, idDepartamento);
	}

	public boolean actualizarEmpleado(Integer idUsuario, String nombre, String clave, String correo, String perfil,
			String nombreDepartamento, String piso) {
		return actualizarEmpleado(idUsuario, nombre, clave, correo, nombreDepartamento, piso);
	}

	public boolean eliminarUsuario(Integer idUsuario) {
		EntityManager em = crearEntityManager();
		try {
			em.getTransaction().begin();
			Usuario usuario = em.find(Usuario.class, idUsuario);
			if (usuario != null) {
				em.remove(usuario);
			}
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			return false;
		} finally {
			em.close();
		}
	}

	private boolean guardar(Usuario usuario) {
		EntityManager em = crearEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(usuario);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			return false;
		} finally {
			em.close();
		}
	}

}
