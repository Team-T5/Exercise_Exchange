package com.example.exerciseexchange.Statistiche_esercizi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseexchange.R;

import java.util.ArrayList;
import java.util.List;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class AdattatoreStrutturaEsame extends RecyclerView.Adapter<AdattatoreStrutturaEsame.strutturaEsameViewHolder> {
    private ArrayList<itemStrutturaEsame> ListaItems;

    public static class strutturaEsameViewHolder extends RecyclerView.ViewHolder{
        public TableLayout tblSezione;

        public TextView txtNumeroSezione;

        public strutturaEsameViewHolder(@NonNull View itemView) {
            super(itemView);
            //Prelevo gli oggetti dall'interfaccia
            txtNumeroSezione = itemView.findViewById(R.id.txtNumeroSezione);
            tblSezione = itemView.findViewById(R.id.tblSezione);
        }

//        public void aggiungiRigaCategoria(String nomeCategoria){
//            //Creo la riga
//            TableRow nuovaRiga = new TableRow(getApplicationContext());
//
//            //Creo la TextView
//            TextView nuovaCategoria = new TextView(getApplicationContext());
//            nuovaCategoria.setText(nomeCategoria);
//
//            //Aggiungo la TextView alla riga
//            nuovaRiga.addView(nuovaCategoria);
//
//            //Aggiungo la riga alla tabella
//            tblSezione.addView(nuovaRiga);
//        }
    }

    public AdattatoreStrutturaEsame(ArrayList<itemStrutturaEsame> itemsList){
        ListaItems = itemsList;
    }

    @NonNull
    @Override
    public strutturaEsameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_struttura_esame, parent, false);
        strutturaEsameViewHolder sevh = new strutturaEsameViewHolder(v);
        return sevh;
    }

    @Override
    public void onBindViewHolder(@NonNull strutturaEsameViewHolder holder, int position) {
        //Inserisco i dati nell'oggetto
        holder.txtNumeroSezione.setText(Integer.toString(position + 1));   //Se i numeri non vanno bene modificare questa linea
    }

    @Override
    public int getItemCount() {
        return ListaItems.size();
    }
}
