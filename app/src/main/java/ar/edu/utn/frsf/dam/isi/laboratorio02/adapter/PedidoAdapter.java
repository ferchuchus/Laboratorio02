package ar.edu.utn.frsf.dam.isi.laboratorio02.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.text.SimpleDateFormat;
import java.util.List;


import ar.edu.utn.frsf.dam.isi.laboratorio02.R;
import ar.edu.utn.frsf.dam.isi.laboratorio02.activity.NuevoPedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.holder.PedidoHolder;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import static ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.Estado.*;

public class PedidoAdapter extends ArrayAdapter<Pedido> {
    private Context ctx;
    private List<Pedido> datos;
    private int cantidad = 0;

    public PedidoAdapter(Context act, List<Pedido> objetos) {
        super(act, R.layout.activity_historial_pedido, objetos);
        this.ctx = act;
        this.datos = objetos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View fila = convertView;
        if (fila == null) {
            fila = inflater.inflate(R.layout.fila_historial, null);
        }
        PedidoHolder holder = (PedidoHolder) fila.getTag();

        if (holder == null) {
            holder = new PedidoHolder(fila);
            fila.setTag(holder);
        }

        Pedido pedido = super.getItem(position);

        if (pedido.getRetirar() == true) {
//            holder.ivImagen.setImageResource(R.drawable.cutlery);
        } else {
//            holder.ivImagen.setImageResource(R.drawable.truck);

        }

        holder.tvMailPedido.setText("Contacto: " + pedido.getMailContacto());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        holder.tvHoraEntrega.setText("Fecha: " + sdf.format(pedido.getFecha()));
        colorEstado(pedido, holder);
        deshabilitaBoton(pedido, holder, datos);
        for (PedidoDetalle p : pedido.getDetalle()) {
            cantidad += p.getCantidad();
        }
        holder.tvCantidadItems.setText("Items: " + cantidad);
        holder.tvPrecio.setText("A Pagar: " + pedido.total());

        holder.btnCancelar.setTag(position);
        holder.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indice = (int) v.getTag();
                Pedido pedidoSel = datos.get(indice);
                pedidoSel.setEstado(CANCELADO);
                PedidoAdapter.this.notifyDataSetChanged();
            }
        });

        holder.btnVerDetalle.setTag(position);
        holder.btnVerDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indice= (int) v.getTag();
                Pedido pedidoSel=datos.get(indice);
                Intent i= new Intent(getContext(), NuevoPedido.class);
                i.putExtra("VER_DETALLE", 1);
                i.putExtra("ID_PEDIDO", pedidoSel.getId());
                ctx.startActivity(i);
            }
        });
        return fila;
    }

    private void colorEstado(Pedido pedido, PedidoHolder holder) {
        switch (pedido.getEstado()) {
            case LISTO:
                holder.estado.setTextColor(Color.DKGRAY);
                holder.estado.setText("Estado: LISTO");
                break;
            case ENTREGADO:
                holder.estado.setTextColor(Color.BLUE);
                holder.estado.setText("Estado: ENTREGADO");
                break;
            case CANCELADO:
                holder.estado.setTextColor(Color.RED);
                holder.estado.setText("Estado: CANCELADO");
                break;
            case RECHAZADO:
                holder.estado.setTextColor(Color.RED);
                holder.estado.setText("Estado: RECHAZADO");
                break;
            case ACEPTADO:
                holder.estado.setTextColor(Color.GREEN);
                holder.estado.setText("Estado: ACEPTADO");
                break;
            case EN_PREPARACION:
                holder.estado.setTextColor(Color.MAGENTA);
                holder.estado.setText("Estado: EN_PREPARACION");
                break;
            case REALIZADO:
                holder.estado.setTextColor(Color.BLUE);
                holder.estado.setText("Estado: REALIZADO");
                break;
        }
    }

    private void deshabilitaBoton(final Pedido pedido, PedidoHolder holder, final List<Pedido> datos) {
        if (pedido.getEstado().equals(RECHAZADO) || pedido.getEstado().equals(CANCELADO)) {
            holder.btnCancelar.setEnabled(false);
        }

    }

}

