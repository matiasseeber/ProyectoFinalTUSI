package Entidades;

public class Tarjeta {
    private int id;
    private Clientes cliente;
    private String numTarjeta;
    private String tipoTarjeta;
    private int codSeguridad;
    public Tarjeta(){}

    public int getId() {
        return id;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public int getCodSeguridad() {
        return codSeguridad;
    }

    public void setCodSeguridad(int codSeguridad) {
        this.codSeguridad = codSeguridad;
    }

    @Override
    public String toString() {
        if(id == -1)
            return "Nueva tarjeta";
        String subString = numTarjeta.substring(11, 15);
        return "**** **** **** " + subString + " - " + tipoTarjeta;
    }
}
