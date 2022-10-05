package Entidades;

public class Provincias {
    private String Id_Provincia;
    private String Nombre;
    public Provincias(String Id_Provincia, String Nombre){
        this.Id_Provincia = Id_Provincia;
        this.Nombre = Nombre;
    }

    public String getId_Provincia() {
        return Id_Provincia;
    }

    public void setId_Provincia(String id_Provincia) {
        Id_Provincia = id_Provincia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
