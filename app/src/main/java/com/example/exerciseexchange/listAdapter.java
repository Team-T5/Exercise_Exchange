package com.example.exerciseexchange;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class listAdapter extends RecyclerView.Adapter<listAdapter.listViewHolder> {
    private ArrayList<esercizio_item> esercizio_list;
    private OnItemClickListener itemListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        itemListener = listener;
    }

    public static class listViewHolder extends RecyclerView.ViewHolder{
        public ImageView immagineEsercizio;
        public TextView editableID, editableCaricatoDa, editableVotoMedio;

        public listViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            immagineEsercizio = itemView.findViewById(R.id.immagineEserczio);
            editableID = itemView.findViewById(R.id.editableID);
            editableCaricatoDa = itemView.findViewById(R.id.editableCaricatoDa);
            editableVotoMedio = itemView.findViewById(R.id.editableVotoMedio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public listAdapter(ArrayList<esercizio_item> esercizio_list){
        this.esercizio_list = esercizio_list;
    }

    @NonNull
    @Override
    public listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.esercizio_item, parent, false);
        listViewHolder lvh= new listViewHolder(v, itemListener);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull listViewHolder holder, int position) {
        esercizio_item currentEsercizio = esercizio_list.get(position);
        /*
        In questo metodo si istanziano gli oggetti mostrati nella RecyclerView
         */

        //La foto mostrata nella carta è la prima della lista.
        String URL_prima_foto = currentEsercizio.getImgURL();
        //Utilizzo Picasso per impostare l'immagine
        Picasso.get().load(URL_prima_foto).fit().centerInside().placeholder(R.drawable.loading).into(holder.immagineEsercizio);

        //Imposto i campi di testo
        holder.editableID.setText(Integer.toString(currentEsercizio.getID()));
        holder.editableCaricatoDa.setText(currentEsercizio.getCaricatoDa());
        /*
        Mostro il voto medio con una sola cifra decimale
         */
        DecimalFormat df = new DecimalFormat("0.0");
        holder.editableVotoMedio.setText(df.format(currentEsercizio.getVotoMedio()));
    }

    @Override
    public int getItemCount() {
        return esercizio_list.size();
    }
}
