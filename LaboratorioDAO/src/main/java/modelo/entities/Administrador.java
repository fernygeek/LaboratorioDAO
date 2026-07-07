package modelo.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Administrador")
public class Administrador extends Usuario {

	private static final long serialVersionUID = 1L;

	public Administrador() {
	}

	public Administrador(String nombre, String clave, String correo) {
		super(nombre, clave, correo);
	}
}