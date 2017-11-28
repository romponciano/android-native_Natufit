package ponciano.romulo.natufit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Calendar;


public class Infos extends Fragment {
    View myView;
    static boolean finalizarPedido = false;
    final int tempoMinimoEntrega = 40;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_infos, container, false);

        getActivity().setTitle("Informações de Entrega");

        // lista de bairros onde é possível entregar
        final ArrayList<String> bairrosEntrega = new ArrayList<String>();
        bairrosEntrega.add("QUIMICA");
        bairrosEntrega.add("QUÍMICA");
        bairrosEntrega.add("ASA BRANCA");
        bairrosEntrega.add("CENTRO");
        bairrosEntrega.add("CAIEIRA");
        bairrosEntrega.add("CAIEIRA SAO PEDRO");
        bairrosEntrega.add("CAIEIRA SÃO PEDRO");
        bairrosEntrega.add("CAEIRA");
        bairrosEntrega.add("CAEIRA SAO PEDRO");
        bairrosEntrega.add("CAEIRA SÃO PEDRO");
        bairrosEntrega.add("VILA HELENA");
        bairrosEntrega.add("OFICINAS VELHAS");
        bairrosEntrega.add("OFICINA VELHA");
        bairrosEntrega.add("OFICINAS VELHA");

        // setar widgets
        final Button finalizar = (Button) myView.findViewById(R.id.btn_finalizar);
        final EditText nome = (EditText) myView.findViewById(R.id.edt_nome);
        final EditText telefone = (EditText) myView.findViewById(R.id.edt_telefone);
        final EditText cidade = (EditText) myView.findViewById(R.id.edt_cidade);
        final EditText bairro = (EditText) myView.findViewById(R.id.edt_bairro);
        final EditText rua = (EditText) myView.findViewById(R.id.edt_rua);
        final EditText numero = (EditText) myView.findViewById(R.id.edt_numero);
        final EditText referencia = (EditText) myView.findViewById(R.id.edt_referencia);
        final EditText troco = (EditText) myView.findViewById(R.id.edt_troco);
        final EditText edtHora = (EditText) myView.findViewById(R.id.edt_hora);
        final EditText edtMinuto = (EditText) myView.findViewById(R.id.edt_minuto);

        // pegar hora atual
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);
        // setar hora no widget
        /*
        minuto += tempoMinimoEntrega+5;
        if( minuto>=60 ) {
            minuto = minuto - 60;
            hora += 1;
        }
        */
        edtHora.setText(String.valueOf(hora+1));
        if(minuto<10) {
            edtMinuto.setText("0"+String.valueOf(minuto));
        }
        else edtMinuto.setText(String.valueOf(minuto));

        // pegar infos.txt se existir
        if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqInfos) ) {
            ArrayList<String> lista_infos;
            lista_infos = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqInfos);
            nome.setText(lista_infos.get(0).toString());
            telefone.setText(lista_infos.get(1).toString());
            cidade.setText(lista_infos.get(2).toString());
            bairro.setText(lista_infos.get(3).toString());
            rua.setText(lista_infos.get(4).toString());
            numero.setText(lista_infos.get(5).toString());
            if( lista_infos.size()>6) {
                referencia.setText(lista_infos.get(6).toString());
            }
        }

        // finalizar infos
        finalizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // verificar se campos estão preenchidos
                if ( (verificarEdtText(nome)) && (verificarEdtText(telefone)) &&
                     (verificarEdtText(troco)) && (verificarEdtText(edtHora)) &&
                     (verificarEdtText(edtMinuto)) && (verificarEdtText(cidade)) &&
                     (verificarEdtText(bairro)) && (verificarEdtText(rua)) &&
                     (verificarEdtText(numero))
                        ) {
                    // verificar cidade e bairro
                    if (verificarCidade(cidade)) {
                        if (verificarBairro(bairro, bairrosEntrega)) {
                            /*
                            int vt = (int)(Double.parseDouble(troco.getText().toString()));
                            int vc = (int)(Double.parseDouble(FuncoesGlobais.totalPedido()));
                            if( vt >= vc ) {
                            */
                                // salvando infos.txt
                                ArrayList<String> infos = new ArrayList<>();
                                infos.add(nome.getText().toString());
                                infos.add(telefone.getText().toString());
                                infos.add(cidade.getText().toString());
                                infos.add(bairro.getText().toString());
                                infos.add(rua.getText().toString());
                                infos.add(numero.getText().toString());
                                infos.add(referencia.getText().toString());
                                String[] Infos = infos.toArray(new String[infos.size()]);
                                FuncoesGlobais.salvarInfos(getContext(), Infos);

                                // juntando lista para salvar pedido.txt
                                ArrayList<String> pedidoC = new ArrayList<String>();
                                Bundle args = getArguments();
                                String[] temp = args.getStringArray("ESCOLHAS");
                                for (int i = 0; i < temp.length; i++) {
                                    String t = temp[i];
                                    pedidoC.add(t);
                                }
                                pedidoC.add("Nome: " + nome.getText().toString());
                                pedidoC.add("Telefone: " + telefone.getText().toString());
                                pedidoC.add("Cidade: " + cidade.getText().toString());
                                pedidoC.add("Bairro: " + bairro.getText().toString());
                                pedidoC.add("Rua: " + rua.getText().toString());
                                pedidoC.add("Nº: " + numero.getText().toString());
                                pedidoC.add("Ponto de Referência: " + referencia.getText().toString());
                                pedidoC.add("Troco p/: R$" + troco.getText().toString());
                                pedidoC.add("Hora da Entrega: " + edtHora.getText().toString() +
                                        ":" + edtMinuto.getText().toString()
                                );
                                pedidoC.add("\n");

                                // gravar nome e numero para nome do arqtxt que vai para email
                                FuncoesGlobais.nomeQuemPediu = nome.getText().toString();
                                FuncoesGlobais.numeroQuemPediu = telefone.getText().toString();

                                // pegar hora
                                Calendar c = Calendar.getInstance();
                                int h = c.get(Calendar.HOUR_OF_DAY);
                                int m = c.get(Calendar.MINUTE);
                                String min;
                                if (m < 10) {
                                    min = String.valueOf("0" + String.valueOf(m));
                                } else min = String.valueOf(m);
                                int dia = c.get(Calendar.DATE);
                                int mes = c.get(Calendar.MONTH);
                                int ano = c.get(Calendar.YEAR);
                                pedidoC.add(
                                        "Data do Pedido: " +
                                                String.valueOf(dia) + "/" +
                                                String.valueOf(mes + 1) + "/" +
                                                String.valueOf(ano) + "  " +
                                                String.valueOf(h) + ":" +
                                                min
                                );

                                // salvar pedido.txt
                                String[] pedidoCompleto = pedidoC.toArray(new String[pedidoC.size()]);
                                FuncoesGlobais.salvarPedido(
                                        getContext(),
                                        pedidoCompleto
                                );

                                int horaEntrega = Integer.parseInt(edtHora.getText().toString());
                                int minutoEntrega = Integer.parseInt(edtMinuto.getText().toString());

                                int diferencaMinuto = (60 - m) + minutoEntrega;

                                if (horaEntrega < 10 || horaEntrega > 17) {
                                    FuncoesGlobais.msgAlerta(getContext(), "Não é possível entregar neste horário. " +
                                            "\nHorário de Funcionamento: 10:00 às 16:00.");
                                    finalizarPedido = false;
                                } else if (horaEntrega < h) msgAlertConfirmacao();
                                else if (horaEntrega == h && (minutoEntrega - m) >= tempoMinimoEntrega)
                                    finalizarPedido = true;
                                else if (horaEntrega == h + 1) {
                                    if (diferencaMinuto >= tempoMinimoEntrega) finalizarPedido = true;
                                    else msgAlertConfirmacao();
                                } else finalizarPedido = true;

                                checarFinalizar();
                            /*
                            } else FuncoesGlobais.msgAlerta(getContext(),
                                "O valor do Troco p/ deve ser maior ou igual ao valor total do pedido.");
                            */
                        }
                    }
                }
                else {
                    FuncoesGlobais.msgAlerta(
                            getContext(),
                            "Campo vazio. Por favor, preencha todas as informações."
                    );
                }
            }
        });

        return myView;
    }

    // função para verificar cidade
    public boolean verificarCidade(EditText cid) {
        String c = cid.getText().toString().toUpperCase();
        if( (c.equals("BARRA DO PIRAI")) || (c.equals("BARRA DO PIRAÍ")) ) {
            return true;
        }
        else {
            FuncoesGlobais.msgAlerta(
                    getContext(),
                    "Entregas apenas para Barra do Piraí.\n " +
                            "Entre em contato pelo telefone ou Whatsapp " +
                            "para mais informações."
            );
            return false;
        }
    }

    // função para verificar bairro
    public boolean verificarBairro(EditText bai, ArrayList<String> lista) {
        String b = bai.getText().toString().toUpperCase();
        if( lista.contains(b) ) {
            return true;
        }
        else {
            FuncoesGlobais.msgAlerta(
                    getContext(),
                    "Entrega não disponível para este bairro.\n " +
                            "Entre em contato pelo telefone ou Whatsapp " +
                            "para mais informações."
            );
            return false;
        }
    }

    // função para verificar se EditText esta vazio
    public boolean verificarEdtText(EditText edt) {
        return !TextUtils.isEmpty(edt.getText().toString());
    }

    // função para confirmar Sim ou Não
    public void msgAlertConfirmacao() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        finalizarPedido = true;
                        checarFinalizar();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        finalizarPedido = false;
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Esta entrega será agendada para amanhã, pois o tempo mínimo de uma entrega é de " + tempoMinimoEntrega + " minutos.\n" +
                "Confirmar agendamento para amanhã neste horário ou cancelar?")
                .setPositiveButton("Agendar", dialogClickListener)
                .setNegativeButton("Cancelar", dialogClickListener)
                .show();
    }

    // função para chegar se é hora de finalizar pedido
    public void checarFinalizar() {
        if( finalizarPedido ) {
            // prox fragment
            Fragment myFragment;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            myFragment = new UltimoPasso();
            ft.replace(R.id.content_frame, myFragment);
            ft.commit();
        }
    }
}
