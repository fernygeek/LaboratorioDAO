package modelo.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import modelo.dao.UsuarioDAO;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "perfil", discriminatorType = DiscriminatorType.STRING)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Integer idUsuario;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "clave", nullable = false)
	private String clave;

	@Column(name = "correo", nullable = false, unique = true)
	private String correo;

	public Usuario() {
	}

	public Usuario(String nombre, String clave, String correo) {
		this.nombre = nombre;
		this.clave = clave;
		this.correo = correo;
	}

	public Usuario(String nombre, String clave, String correo, String perfil) {
		this(nombre, clave, correo);
	}

	public static Usuario autenticar(String nombre, String clave) {
		return new UsuarioDAO().autenticar(nombre, clave);
	}

	public static List<Usuario> consultarUsuarios() {
		return new UsuarioDAO().consultarUsuarios();
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPerfil() {
		if (this instanceof Administrador) {
			return "Administrador";
		}
		if (this instanceof Empleado) {
			return "Empleado";
		}
		return "Usuario";
	}

	@Transient
	public void setPerfil(String perfil) {
		// La columna perfil es manejada por JPA como discriminador de herencia.
	}

	public boolean esEmpleado() {
		return this instanceof Empleado;
	}

	@Transient
	public Departamento getDepartamento() {
		return null;
	}

	@Transient
	public String getPiso() {
		return null;
	}
}