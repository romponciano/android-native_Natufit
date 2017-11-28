package ponciano.romulo.natufit;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Carboidratos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);

        setTitle("Opções de Carboidratos");

        // setando valores
        final ListView listView = (ListView) findViewById(R.id.lista_opcoes);
        final Button btn = (Button) findViewById(R.id.btn_salvar);
        final Context c = this;
        // pegar lista de itens
        String[] itens = getResources().getStringArray(R.array.array_carboidratos);

        // populando lista
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                c,
                android.R.layout.simple_list_item_single_choice,
                itens
        );
        FuncoesGlobais.popularLista(listView, adapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // quando usr clickar para salvar
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // pegando selecionados
                String[] selecionados = FuncoesGlobais.pegarSelecionados(listView, adapter);

                // salvando escolhas
                FuncoesGlobais.salvarEscolhasSemValor(
                        c,
                        FuncoesGlobais.arqCarboidratos,
                        selecionados
                );

                FuncoesGlobais.totalSaladas = 9.50;

                // voltar para Saladas
            }
        });
    }

}
