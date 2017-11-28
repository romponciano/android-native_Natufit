package ponciano.romulo.natufit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;

import java.util.ArrayList;


public class Saladas extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_saladas, container, false);

        getActivity().setTitle("Saladas");

        // setando valores
        final ListView listView = (ListView) myView.findViewById(R.id.lista_menu);
        // pegar lista de itens
        String[] itens = getResources().getStringArray(R.array.array_opcoes);

        // populando lista
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                itens
        );
        FuncoesGlobais.popularLista(listView, adapter);


        // ao clickar na opção.. abrir itens
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getActivity(), Proteinas.class);
                    startActivity(intent);
                }
                if (position == 1) {
                    Intent intent = new Intent(getActivity(), Carboidratos.class);
                    startActivity(intent);
                }
                if (position == 2) {
                    Intent i = new Intent(getActivity(), Leguminosas.class);
                    startActivity(i);
                }
                if (position == 3) {
                    Intent i = new Intent(getActivity(), Verduras.class);
                    startActivity(i);
                }
                if (position == 4) {
                    Intent i = new Intent(getActivity(), Legumes.class);
                    startActivity(i);
                }
                if (position == 5) {
                    Intent i = new Intent(getActivity(), Frutas.class);
                    startActivity(i);
                }
                if (position == 6) {
                    Intent i = new Intent(getActivity(), Oleaginosas.class);
                    startActivity(i);
                }
            }
        });

        return myView;
    }
}

