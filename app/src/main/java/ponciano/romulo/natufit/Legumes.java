package ponciano.romulo.natufit;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Legumes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);

        setTitle("Opções de Legumes");

        // setando valores
        final Button btn = (Button) findViewById(R.id.btn_salvar);
        final ListView listView = (ListView) findViewById(R.id.lista_opcoes);
        final Context c = this;
        // pegar lista de itens
        String[] itens = getResources().getStringArray(R.array.array_legumes);

        // populando lista
        final ArrayAdapter<String> adpSV = FuncoesGlobais.criarAdaptador(c, itens); // adapter s/ valores
        FuncoesGlobais.popularLista(listView, adpSV);

        // máximo de 2 items na lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
                int count = 0;
                for (int i = 0, ei = checkedItemPositions.size(); i < ei; i++) {
                    if (checkedItemPositions.valueAt(i)) {
                        count++;
                    }
                }
                if (count > 2) {
                    FuncoesGlobais.msgAlerta(c, "Só é possível selecionar " +
                            "no máximo dois itens");
                    listView.clearChoices();
                    adpSV.notifyDataSetChanged();
                }
            }
        });

        // quando usr clickar para salvar
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // pegando selecionados
                String[] selecionados = FuncoesGlobais.pegarSelecionados(listView, adpSV);

                // salvando escolhas
                FuncoesGlobais.salvarEscolhasSemValor(
                        c,
                        FuncoesGlobais.arqLegumes,
                        selecionados
                );

                FuncoesGlobais.totalSaladas = 9.50;

                // voltar para Saladas
            }
        });
    }

}
