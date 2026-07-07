package modelo.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("Empleado")
public class Empleado extends Usuario {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "id_departamento")
	private Departamento departamento;

	public Empleado() {
	}

	public Empleado(String nombre, String clave, String correo, Departamento departamento) {
		super(nombre, clave, correo);
		this.departamento = departamento;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getPiso() {
		return departamento != null ? departamento.getNumeroPiso() : null;
	}
}
