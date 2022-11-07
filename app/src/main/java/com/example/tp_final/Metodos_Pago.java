package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import Database.DBInsertCreditCardAndOrder;
import Database.DBInsertPedido;
import Database.DBLoadCreditCardsInSpinner;
import Entidades.PedidoCabecera;
import Entidades.PedidoDetalle;
import Entidades.Tarjeta;

public class Metodos_Pago extends AppCompatActivity {

    private Spinner spTarjetasUsuario;
    private EditText txtNroTarjeta;
    private EditText nombreTarjeta;
    private EditText codSeguridad;
    private RadioGroup radioGroupMetodoDePago;
    RadioButton btnCredito;
    RadioButton btnDebito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodos_pago);
        spTarjetasUsuario = (Spinner) findViewById(R.id.spTarjetasUsuario);
        txtNroTarjeta = (EditText) findViewById(R.id.editTextNumber);
        nombreTarjeta = (EditText) findViewById(R.id.editTextTextPersonName);
        codSeguridad = (EditText) findViewById(R.id.editTextCodSeguridad);
        radioGroupMetodoDePago = (RadioGroup) findViewById(R.id.radioGroupMetodoDePago);
        btnCredito = (RadioButton) radioGroupMetodoDePago.getChildAt(0);
        btnDebito = (RadioButton) radioGroupMetodoDePago.getChildAt(1);
        DBLoadCreditCardsInSpinner dbLoadCreditCardsInSpinner = new DBLoadCreditCardsInSpinner();
        dbLoadCreditCardsInSpinner.setSpinner(spTarjetasUsuario);
        dbLoadCreditCardsInSpinner.setContext(getApplicationContext());
        dbLoadCreditCardsInSpinner.execute();
        spTarjetasUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean enabled = true;
                if(position != 0){
                    Tarjeta tarjeta = (Tarjeta) spTarjetasUsuario.getSelectedItem();
                    txtNroTarjeta.setText(tarjeta.getNumTarjeta());
                    nombreTarjeta.setText(tarjeta.getCliente().getNombre() + " " + tarjeta.getCliente().getApellido());
                    codSeguridad.setText("***");
                    if(tarjeta.getTipoTarjeta().equals("Crédito")){
                        btnCredito.setChecked(true);
                        btnDebito.setChecked(false);
                    }else{
                        btnCredito.setChecked(false);
                        btnDebito.setChecked(true);
                    }
                    enabled = false;
                }else{
                    txtNroTarjeta.setText("");
                    nombreTarjeta.setText("");
                    codSeguridad.setText("");
                    btnCredito.setChecked(false);
                    btnDebito.setChecked(false);
                    enabled = true;
                }
                btnCredito.setEnabled(enabled);
                btnDebito.setEnabled(enabled);
                txtNroTarjeta.setEnabled(enabled);
                nombreTarjeta.setEnabled(enabled);
                codSeguridad.setEnabled(enabled);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void ClickBack(View view) {
        this.finish();
    }

    public ArrayList<PedidoDetalle> jsonParsePedidoDetalle() {
        ArrayList<PedidoDetalle> pedidoDetalleArrayList = new ArrayList<>();
        Gson gson = new Gson();
        ArrayList<String> arrayList = getIntent().getStringArrayListExtra("detallePedidos");
        for (int i = 0; i < arrayList.size(); i++) {
            String json = arrayList.get(i);
            PedidoDetalle pedidoDetalle = gson.fromJson(json, PedidoDetalle.class);
            pedidoDetalleArrayList.add(pedidoDetalle);
        }
        return pedidoDetalleArrayList;
    }

    public PedidoCabecera jsonParsePedidoCabecera(){
        Gson gson = new Gson();
        PedidoCabecera pedidoCabecera = gson.fromJson(getIntent().getStringExtra("pedidoCabecera"), PedidoCabecera.class);
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setNumTarjeta(txtNroTarjeta.getText().toString());
        if(spTarjetasUsuario.getSelectedItemPosition() == 0){
            String tipoTarjeta = btnCredito.isChecked() ? btnCredito.getText().toString() : btnDebito.getText().toString();
            tarjeta.setTipoTarjeta(tipoTarjeta);
            tarjeta.setCodSeguridad(Integer.parseInt(codSeguridad.getText().toString()));
        }
        pedidoCabecera.setTarjeta(tarjeta);
        return pedidoCabecera;
    }

    public boolean isFormValid() {
        Boolean isFormValid = true;
        if (txtNroTarjeta.getText().toString().length() != 16) {
            txtNroTarjeta.setError("El numero de tarjeta debe ser de 16 numeros");
            isFormValid = false;
        }
        if (nombreTarjeta.getText().toString().isEmpty()) {
            nombreTarjeta.setError("Este campo es requerido.");
            isFormValid = false;
        }
        if (codSeguridad.getText().toString().length() < 3) {
            codSeguridad.setError("El codigo de seguridad debe de contener 3 numeros.");
            isFormValid = false;
        }
        if(!btnCredito.isChecked() && !btnDebito.isChecked()){
            Toast.makeText(getApplicationContext(), "Se debe seleccionar el tipo de tarjeta.", Toast.LENGTH_LONG).show();
        }
        return isFormValid;
    }

    public void ClickFinish(View view) {
        if (!isFormValid()) return;
        PedidoCabecera pedidoCabecera = jsonParsePedidoCabecera();
        ArrayList<PedidoDetalle> pedidoDetalleArrayList = jsonParsePedidoDetalle();
        if (spTarjetasUsuario.getVisibility() == View.GONE || spTarjetasUsuario.getSelectedItemPosition() == 0) {
            DBInsertCreditCardAndOrder dbInsertCreditCardAndOrder = new DBInsertCreditCardAndOrder();
            dbInsertCreditCardAndOrder.setActivity(this);
            dbInsertCreditCardAndOrder.setContext(getApplicationContext());
            dbInsertCreditCardAndOrder.setPedidoCabecera(pedidoCabecera);
            dbInsertCreditCardAndOrder.setPedidoDetalleArrayList(pedidoDetalleArrayList);
            dbInsertCreditCardAndOrder.execute();
        } else {
            DBInsertPedido insertPedido = new DBInsertPedido();
            pedidoCabecera.setPedidoDetalles(pedidoDetalleArrayList);
            insertPedido.setPedidoCabecera(pedidoCabecera);
            insertPedido.setContext(getApplicationContext());
            insertPedido.setActivity(this);
            insertPedido.execute();
        }
    }
}