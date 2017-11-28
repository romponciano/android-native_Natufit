package ponciano.romulo.natufit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;


public class RevisarPedido extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_revisar_pedido, container, false);

        getActivity().setTitle("Revisar Pedido");

        // setando widgets
        final Button confirmar = (Button) myView.findViewById(R.id.btn_salvar);
        final TextView pedido = (TextView) myView.findViewById(R.id.txt_pedido);
        final ScrollView sv = (ScrollView) myView.findViewById(R.id.sv);

        // mostrando pedido
        pedido.append("");
        sv.setVerticalScrollBarEnabled(false);
        sv.setHorizontalScrollBarEnabled(false);
        ArrayList<String> menu = new ArrayList<>();
        // verificar se tem saladas
        if( FuncoesGlobais.totalSaladas>0 ) {
            pedido.append("Total Salada no Pote R$ 9.50");
            pedido.append("\n");
            if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqProteinas) ) {
                menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqProteinas);
                FuncoesGlobais.preencherTextViewSaladas(menu, pedido);
            }
            if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqCarboidratos) ) {
                menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqCarboidratos);
                FuncoesGlobais.preencherTextViewSaladas(menu, pedido);
            }
            if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqLeguminosas) ) {
                menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqLeguminosas);
                FuncoesGlobais.preencherTextViewSaladas(menu, pedido);
            }
            if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqVerduras) ) {
                menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqVerduras);
                FuncoesGlobais.preencherTextViewSaladas(menu, pedido);
            }
            if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqLegumes) ) {
                menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqLegumes);
                FuncoesGlobais.preencherTextViewSaladas(menu, pedido);
            }
            if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqFrutas) ) {
                menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqFrutas);
                FuncoesGlobais.preencherTextViewSaladas(menu, pedido);
            }
            if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqOleaginosas) ) {
                menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqOleaginosas);
                FuncoesGlobais.preencherTextViewSaladas(menu, pedido);
            }
            pedido.append("\n");
        }
        // verificar se tem bebeidas
        if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqBebidas) ) {
            pedido.append("Total Bebidas R$" + String.format("%.2f", FuncoesGlobais.totalBebidas));
            pedido.append("\n");
            menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqBebidas);
            FuncoesGlobais.preencherTextView(menu, pedido);
        }
        // verificar se tem sobremesas
        if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqZeroAcucar) ) {
            pedido.append("Total Zero Açucar R$" + String.format("%.2f", FuncoesGlobais.totalZeroAcucar));
            pedido.append("\n");
            menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqZeroAcucar);
            FuncoesGlobais.preencherTextView(menu, pedido);
        }
        // verificar se tem zero açucar
        if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqZeroGlutem) ) {
            pedido.append("Total Zero Glúten R$" + String.format("%.2f", FuncoesGlobais.totalZeroGlutem));
            pedido.append("\n");
            menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqZeroGlutem);
            FuncoesGlobais.preencherTextView(menu, pedido);
        }
        // verificar se tem low carb
        if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqLowCarb) ) {
            pedido.append("Total Low Carb R$" + String.format("%.2f", FuncoesGlobais.totalLowCarbs));
            pedido.append("\n");
            menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqLowCarb);
            FuncoesGlobais.preencherTextView(menu, pedido);
        }
        // verificar se tem congelados
        if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqCongelados) ) {
            pedido.append("Total Congelados R$" + String.format("%.2f", FuncoesGlobais.totalCongelados));
            pedido.append("\n");
            menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqCongelados);
            FuncoesGlobais.preencherTextView(menu, pedido);
        }

        // valor total da compra
        pedido.append("\n");
        pedido.append("Total da Compra: R$ " + FuncoesGlobais.totalPedido());
        pedido.append("\n \n \n");

        // ao confirmar...
        confirmar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                pedido.getEditableText().delete(pedido.length()-1, pedido.length());
                pedido.getEditableText().delete(pedido.length()-1, pedido.length());

                String pc = pedido.getText().toString();
                String[] pedidoCompleto = pc.split("\n");

                Bundle p = new Bundle();
                p.putStringArray("ESCOLHAS", pedidoCompleto);
                // mudar fragment
                Fragment myFragment;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                myFragment = new Infos();
                myFragment.setArguments(p);
                ft.replace(R.id.content_frame, myFragment);
                ft.commit();
            }
        });

        return myView;
    }
}
