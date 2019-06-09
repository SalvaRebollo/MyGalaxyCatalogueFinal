package com.ceviche.sareb.salvisapp.Adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceviche.sareb.salvisapp.Clases.UsuarioProductosItemListClass;
import com.ceviche.sareb.salvisapp.EditarProducto;
import com.ceviche.sareb.salvisapp.MenuNavegacion;
import com.ceviche.sareb.salvisapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdaptadorListMisProductos extends RecyclerView.Adapter<AdaptadorListMisProductos.ViewHolderMisProductos> {

    ArrayList<UsuarioProductosItemListClass> listaMisProductos;
    Context mainContext;
    RecyclerView.Adapter adapter;

    public AdaptadorListMisProductos(ArrayList<UsuarioProductosItemListClass> listaMisProductos, Context mainContext) {
        this.listaMisProductos = listaMisProductos;
        this.mainContext = mainContext;
        this.adapter = this;
    }

    @Override
    public ViewHolderMisProductos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_misproductos, null, false);

        return new ViewHolderMisProductos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMisProductos viewHolderMisProductos, final int position) {

        viewHolderMisProductos.ilTextoTitulo.setText(listaMisProductos.get(position).getTitulo());
        viewHolderMisProductos.ilTextoDescripcion.setText(listaMisProductos.get(position).getDescripcion());
        viewHolderMisProductos.ilTextoPrecio.setText(Html.fromHtml("<b>Precio: </b>" + listaMisProductos.get(position).getPrecio() + "€"));
        viewHolderMisProductos.ilTextoEstado.setText(Html.fromHtml("<b>Estado: </b>" + listaMisProductos.get(position).getEstado()));
        viewHolderMisProductos.ilTextoCategoria.setText(Html.fromHtml("<b>Categoría: </b>" + listaMisProductos.get(position).getCategoria()));
        viewHolderMisProductos.ilTextoUsuario.setText(Html.fromHtml("<b>" + listaMisProductos.get(position).getNombreUsuarioCreador() + "</b>"));

        Picasso.with(this.mainContext)
                .load(listaMisProductos.get(position).getimagen())
                .placeholder(R.drawable.productosinfoto)
                .into(viewHolderMisProductos.ilImagenProducto);

        if (listaMisProductos.get(position).getFotoUsuarioCreador() == null || listaMisProductos.get(position).getFotoUsuarioCreador().isEmpty()) {
            Picasso.with(this.mainContext)
                    .load(R.drawable.avatardefault)
                    .placeholder(R.drawable.avatardefault)
                    .into(viewHolderMisProductos.ilImagenUsuario);
        } else {
            Picasso.with(this.mainContext)
                    .load(listaMisProductos.get(position).getFotoUsuarioCreador())
                    .placeholder(R.drawable.avatardefault)
                    .into(viewHolderMisProductos.ilImagenUsuario);
        }


        viewHolderMisProductos.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idProductoAEditar = listaMisProductos.get(position).getProductoid();


                AlertDialog dialogEliminar = AskOptionEditar(position, idProductoAEditar);
                dialogEliminar.show();

            }
        });

        viewHolderMisProductos.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String idProductoABorrar = listaMisProductos.get(position).getProductoid();


                AlertDialog dialogEliminar = AskOption(position, idProductoABorrar);
                dialogEliminar.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return listaMisProductos.size();
    }

    private AlertDialog AskOption(final int position, final String idProductoABorrar) {
        AlertDialog miDialogoEliminar = new AlertDialog.Builder(mainContext)
                //set message, title, and icon
                .setTitle("Eliminar " + listaMisProductos.get(position).getTitulo())
                .setMessage("¿Estás seguro de realizar esta acción?")


                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Productos").child("ProductosRegistrados").child(idProductoABorrar);
                        database.removeValue();

                        /**
                         * MySQL Logic
                         */
                        ejecutarServicio("https://my-galaxy-catalogue-php.herokuapp.com?ope=delete", idProductoABorrar);


                        Toast.makeText(mainContext, "Has eliminado el producto: " + listaMisProductos.get(position).getTitulo(), Toast.LENGTH_SHORT).show();

                        listaMisProductos.remove(position);
                        adapter.notifyItemRemoved(position);


                        // Para pasar de una actividad a otra
                        mainContext.startActivity(new Intent(mainContext, MenuNavegacion.class));

                        // FIN para pasar de una actividad a otra
                        dialog.dismiss();
                    }

                })

                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return miDialogoEliminar;

    }

    private AlertDialog AskOptionEditar(final int position, final String idProductoAEditar) {
        AlertDialog miDialogoEliminar = new AlertDialog.Builder(mainContext)
                //set message, title, and icon
                .setTitle("¿Quieres editar " + listaMisProductos.get(position).getTitulo() + "?")
                .setMessage("¿Estás seguro de que quieres editar esta publicación?")


                .setPositiveButton("Editar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        // Para pasar de una actividad a otra
                        Intent intent = new Intent(mainContext, EditarProducto.class);
                        intent.putExtra("idProductoAEditar", idProductoAEditar);
                        mainContext.startActivity(intent);

                        // FIN para pasar de una actividad a otra
                        dialog.dismiss();
                    }

                })

                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return miDialogoEliminar;

    }

    /**
     * MySQL LOGIC
     */
    private void ejecutarServicio(String URL, String idProduct) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mainContext, "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mainContext, error.toString(), Toast.LENGTH_SHORT);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("IdProduct", idProduct);


                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mainContext);
        requestQueue.add(stringRequest);


    }

    public class ViewHolderMisProductos extends RecyclerView.ViewHolder {

        TextView ilTextoTitulo, ilTextoDescripcion, ilTextoPrecio, ilTextoEstado, ilTextoCategoria, ilTextoUsuario;
        ImageView ilImagenProducto, ilImagenUsuario;

        LinearLayout listaItem;

        FloatingActionButton btnEditar, btnEliminar;

        public ViewHolderMisProductos(@NonNull View itemView) {
            super(itemView);

            ilTextoTitulo = itemView.findViewById(R.id.ilTextoTitulo);
            ilTextoDescripcion = itemView.findViewById(R.id.ilTextoDescripcion);
            ilTextoPrecio = itemView.findViewById(R.id.ilTextoPrecio);
            ilTextoEstado = itemView.findViewById(R.id.ilTextoEstado);
            ilTextoCategoria = itemView.findViewById(R.id.ilTextoCategoria);

            ilTextoUsuario = itemView.findViewById(R.id.ilTextoUsuario);

            ilImagenProducto = itemView.findViewById(R.id.ilImagenProducto);
            ilImagenUsuario = itemView.findViewById(R.id.ilImagenUsuario);

            listaItem = itemView.findViewById(R.id.listaItem);

            btnEditar = itemView.findViewById(R.id.btnMisProductosEditar);
            btnEliminar = itemView.findViewById(R.id.btnMisProductosEliminar);
        }
    }


}
