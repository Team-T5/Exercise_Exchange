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

import static com.example.exerciseexchange.MyApplication.noImageURL;
import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class listAdapter extends RecyclerView.Adapter<listAdapter.listViewHolder> {
    private ArrayList<esercizio_item> esercizio_list;
//    public ImageView imgEsercizio;
//    public int IDEsercizio;
//    public String esercizioCaricatoDa;
//    public double votoMedioEsercizio;

    public static class listViewHolder extends RecyclerView.ViewHolder{
        public ImageView immagineEsercizio;
        public TextView editableID, editableCaricatoDa, editableVotoMedio;

        public listViewHolder(@NonNull View itemView) {

            super(itemView);
            immagineEsercizio = itemView.findViewById(R.id.immagineEserczio);
            editableID = itemView.findViewById(R.id.editableID);
            editableCaricatoDa = itemView.findViewById(R.id.editableCaricatoDa);
            editableVotoMedio = itemView.findViewById(R.id.editableVotoMedio);
        }
    }

    public listAdapter(ArrayList<esercizio_item> esercizio_list){
        this.esercizio_list = esercizio_list;
    }

    @NonNull
    @Override
    public listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.esercizio_item, parent, false);
        listViewHolder lvh= new listViewHolder(v);
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
        Picasso.get().load(URL_prima_foto).fit().centerInside().placeholder(R.drawable.no_image_icon).into(holder.immagineEsercizio);

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
