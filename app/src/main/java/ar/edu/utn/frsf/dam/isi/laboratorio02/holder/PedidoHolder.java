package ar.edu.utn.frsf.dam.isi.laboratorio02.holder;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.R;


public class PedidoHolder {
    public TextView tvMailPedido;
    public TextView tvHoraEntrega;
    public TextView tvCantidadItems;
    public TextView tvPrecio;
    public TextView estado;
    public ImageView ivImagen;
    public Button btnCancelar;
    public Button btnVerDetalle;

    public PedidoHolder(View base) {
        tvMailPedido = (TextView) base.findViewById(R.id.tvContacto);
        tvHoraEntrega = (TextView) base.findViewById(R.id.tvFecha);
        tvCantidadItems = (TextView) base.findViewById(R.id.tvItems);
        tvPrecio = (TextView) base.findViewById(R.id.tvPago);
        estado = (TextView) base.findViewById(R.id.tvEstado);
        ivImagen = (ImageView) base.findViewById(R.id.imImagen);
        this.btnCancelar = (Button) base.findViewById(R.id.btnCancelar);
        this.btnVerDetalle= (Button) base.findViewById(R.id.btnVerDetalle);
    }
}
