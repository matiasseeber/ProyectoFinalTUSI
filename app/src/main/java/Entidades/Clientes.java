package Entidades;

public class Clientes {
    private int id;
    private int dni;
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;
    private String sexo;
    private int edad;
    private int cod_localidad;
    private boolean estado;

    public Clientes() {
    }

    public Clientes(int id, String nombre, String apellido, String email, String contraseña, String sexo, int edad, int cod_localidad, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
        this.sexo = sexo;
        this.edad = edad;
        this.cod_localidad = cod_localidad;
        this.estado = estado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getCod_localidad() {
        return cod_localidad;
    }

    public void setCod_localidad(int cod_localidad) {
        this.cod_localidad = cod_localidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
