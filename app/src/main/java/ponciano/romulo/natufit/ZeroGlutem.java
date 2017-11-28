package ponciano.romulo.natufit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class ZeroGlutem extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_menu, container, false);

        getActivity().setTitle("Zero Glutem");

        // setando valores
        final Button proximo = (Button) myView.findViewById(R.id.btn_salvar);
        final ListView listView = (ListView) myView.findViewById(R.id.lista_menu);
        // pegar lista de itens
        String[] itens = getResources().getStringArray(R.array.array_zeroglutem);
        // criar obj de alimentos
        final ArrayList<Alimento> lista = FuncoesGlobais.criarAlimento(itens, FuncoesGlobais.zeroGlutem_valores);
        // pegar valores de cada item da lista
        double[] valor = FuncoesGlobais.pegarValor(itens, lista, getContext());
        // juntar arrays de itens c/ valores
        String[] arr = FuncoesGlobais.juntarArrays(itens, valor);

        // populando lista
        final ArrayAdapter<String> adapter = FuncoesGlobais.criarAdaptador(getContext(), arr); // adapter c/ valores
        final ArrayAdapter<String> adpSV = FuncoesGlobais.criarAdaptador(getContext(), itens); // adapter s/ valores
        FuncoesGlobais.popularLista(listView, adapter);

        // aguardando click para salvar
        proximo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // pegando selecionados
                String[] selecionados = FuncoesGlobais.pegarSelecionados(listView, adpSV);

                double[] valorSelecionados = FuncoesGlobais.pegarValor(selecionados, lista, getContext());
                FuncoesGlobais.totalZeroGlutem = FuncoesGlobais.calcularTotal(valorSelecionados);

                // salvando escolhas
                FuncoesGlobais.salvarEscolhas(
                        getContext(),
                        FuncoesGlobais.arqZeroGlutem,
                        selecionados,
                        FuncoesGlobais.converterDoubleToString(valorSelecionados)
                );
            }
        });

        return myView;
    }
}

