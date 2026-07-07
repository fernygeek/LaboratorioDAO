# Sistema de Gestion de Usuarios

Laboratorio práctico realizado por el GR02 en la asignatura de Aplicaciones Web para aplicar los conceptos del patrón de arquitectura MVC, DAO y ORM.

## Casos de uso implementados

### Autenticar

Permite validar las credenciales del usuario. Solo los usuarios con perfil Administrador pueden iniciar sesion. Si un Empleado intenta ingresar, se muestra el mensaje:

`Por ahora solo los administradores pueden iniciar sesion.`

### Gestionar Usuarios

Permite al Administrador consultar, crear, actualizar y eliminar usuarios. Los empleados se asocian a un Departamento existente, que incluye nombre y numero de piso.

## Modelo principal

El sistema utiliza las entidades:

- `Usuario`
- `Administrador`
- `Empleado`
- `Departamento`

Las operaciones de persistencia se realizan mediante `UsuarioDAO` usando JPA / EntityManager sobre una base de datos MySQL.

## Nota sobre permisos

El modulo de Gestion de Usuarios esta restringido a usuarios Administrador. Los usuarios Empleado no pueden iniciar sesion en el sistema.
