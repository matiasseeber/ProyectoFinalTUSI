package Database;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Entidades.Clientes;
import Helpers.Helpers;

public class DBSetUsuarioProfileInformation extends AsyncTask<Boolean, Void, Boolean> {

    private Clientes clientes;
    private TextView nombre = null;
    private TextView apellido = null;
    private TextView nombreDeUsuario = null;
    private TextView email = null;
    private EditText direccion = null;
    private TextView sexo = null;
    private TextView contraseña;
    private TextView contraseña2;


    public DBSetUsuarioProfileInformation() {
    }

    public TextView getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public TextView getContraseña() {
        return contraseña;
    }

    public void setContraseña(TextView contraseña) {
        this.contraseña = contraseña;
    }

    public TextView getContraseña2() {
        return contraseña2;
    }

    public void setContraseña2(TextView contraseña2) {
        this.contraseña2 = contraseña2;
    }

    public void setNombreDeUsuario(TextView nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getApellido() {
        return apellido;
    }

    public void setApellido(TextView apellido) {
        this.apellido = apellido;
    }


    public TextView getEmail() {
        return email;
    }

    public void setEmail(TextView email) {
        this.email = email;
    }

    public EditText getDireccion() {
        return direccion;
    }

    public void setDireccion(EditText direccion) {
        this.direccion = direccion;
    }

    public TextView getSexo() {
        return sexo;
    }

    public void setSexo(TextView sexo) {
        this.sexo = sexo;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    private boolean getUsuarioInformation() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Clientes where Clientes.id = " + clientes.getId() + ";");
            rs.beforeFirst();
            boolean wasComercioSet = false;
            if (rs.next()) {
                clientes.setNombre(rs.getString("nombre"));
                clientes.setApellido(rs.getString("apellido"));
                clientes.setNombreUsuario(rs.getString("nombreUsuario"));
                clientes.setEmail(rs.getString("email"));
                clientes.setDireccion(rs.getString("direccion"));
                clientes.setSexo(rs.getString("sexo"));
                clientes.setContraseña(rs.getString("contraseña"));
                wasComercioSet = true;
            }
            return wasComercioSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {
        return getUsuarioInformation();
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if (nombre != null) nombre.setText(clientes.getNombre());
        if (apellido != null) apellido.setText(clientes.getApellido());
        if (nombreDeUsuario != null) nombreDeUsuario.setText(clientes.getNombreUsuario());
        if (email != null) email.setText(clientes.getEmail());
        if (direccion != null) direccion.setText(clientes.getDireccion());
        if (sexo != null) sexo.setText(clientes.getSexo());
        if (contraseña != null) contraseña.setText(clientes.getContraseña());
        if (contraseña2 != null) contraseña2.setText(clientes.getContraseña());
    }
}
