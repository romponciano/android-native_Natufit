package ponciano.romulo.natufit;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Rômulo Ponciano on 08/02/2017.
 */

public class FuncoesGlobais {

    // nome dos arqs
    final static String arqInfos = "informacoes.txt";
    final static String arqPedido = "pedido.txt";
    final static String arqSaladas = "saladas.txt";
    final static String arqProteinas = "proteinas.txt";
    final static String arqCarboidratos = "carboidratos.txt";
    final static String arqLeguminosas = "leguminosas.txt";
    final static String arqVerduras = "verduras.txt";
    final static String arqLegumes = "legumes.txt";
    final static String arqFrutas = "frutas.txt";
    final static String arqOleaginosas = "oleaginosas.txt";
    final static String arqBebidas = "bebidas.txt";
    final static String arqCongelados = "congelados.txt";
    final static String arqLowCarb = "lowcarb.txt";
    final static String arqZeroGlutem = "zeroglutem.txt";
    final static String arqZeroAcucar = "zeroacucar.txt";
    // variaveis para armazenar os valores de cada categoria
    static double totalSaladas = 0;
    static double totalBebidas = 0;
    static double totalCongelados = 0;
    static double totalLowCarbs = 0;
    static double totalZeroAcucar = 0;
    static double totalZeroGlutem = 0;
    // variaveis para formar nome do arqtxt que vai para email
    static String nomeQuemPediu = "";
    static String numeroQuemPediu ="";

    // LISTA DE BEBIDAS C/ VALORES
    final static double[] bebidas_valores = {
            3, 3, 3, 3,
            3, 3, 3, 3,
            4, 4, 4, 4, 4, 4,
            6, 6, 6, 6, 6, 6, 6
    };

    // LISTA DE CONGELADOS C/ VALORES
    final static double[] congelado_valores = {
            6, 6, 6, 6, 6,
            6, 6, 6, 6, 6,
            6, 6, 6
    };

    // LISTA DE LOW CARB C/ VALORES
    final static double[] lowcarbs_valores = {
            2, 4, 4, 6, 6,
            6, 6, 4.5
    };

    // LISTA DE ZERO GLUTEM C/ VALORES
    final static double[] zeroGlutem_valores = {
            8, 6, 6, 4, 1.20, 6,
            1.2, 1.2, 12, 4, 8,
            8, 3, 6
    };

    // LISTA DE ZERO AÇUCAR C/ VALORES
    final static double[] zeroAcucar_valores = {
            3, 3, 3, 3, 3,
            4, 3, 2, 8, 3,
            3, 3
    };

    // ------------------------------------ FUNÇÕES ---------------------
    // verificar se arquivo já existe
    public static boolean verificarArq(Context c, String arqNome) {
        File file = c.getFileStreamPath(arqNome);
        return file.exists();
    }



    // deletar arquivo
    public static void deletarArq(Context c, String arqNome) {
        c.deleteFile(arqNome);
    }

    // deletar todos arquivos
    public static void deletarTodosArq(Context c) {
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqPedido)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqPedido);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqBebidas)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqBebidas);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqCongelados)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqCongelados);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqLowCarb)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqLowCarb);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqSaladas)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqSaladas);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqZeroAcucar)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqZeroAcucar);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqZeroGlutem)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqZeroGlutem);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqOleaginosas)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqOleaginosas);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqFrutas)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqFrutas);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqLegumes)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqLegumes);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqCarboidratos)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqCarboidratos);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqLeguminosas)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqLeguminosas);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqProteinas)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqProteinas);
        }
        if(FuncoesGlobais.verificarArq(c, FuncoesGlobais.arqVerduras)) {
            FuncoesGlobais.deletarArq(c, FuncoesGlobais.arqVerduras);
        }
        // resetar valor total
        totalSaladas = 0;
        totalBebidas = 0;
        totalCongelados = 0;
        totalLowCarbs = 0;
        totalZeroAcucar = 0;
        totalZeroGlutem = 0;
    }

    public static void preencherTextView(ArrayList<String> l, TextView cont) {
        for(int i = 0; i < l.size(); i++) {
            cont.append("\t");
            cont.append(l.get(i) + "\n");
        }
        cont.append("\n");
    }

    // preencher textview Saladas no Pote
    public static void preencherTextViewSaladas(ArrayList<String> l, TextView cont) {
        for(int i = 0; i < l.size(); i++) {
            cont.append("\t");
            cont.append(l.get(i) + "\n");
        }
    }

    // carregar pedido
    public static ArrayList<String> carregarPedido(Context c, String arqNome) {
        ArrayList<String> lines = new ArrayList<>(); // stores lines from text file.
        try {
            InputStream inputStream = c.openFileInput(arqNome);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                    lines.add(receiveString);
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            lines.add("");
            msgAlerta(c, "Erro! Contate a empresa: " + e.toString());
        } catch (IOException e) {
            lines.add("");
            msgAlerta(c, "Erro! Contate a empresa: " + e.toString());
        }

        return lines;
    }

    // salvar escolhas
    public static void salvarEscolhas (Context c, String arqNome, String[] arr, String[] v) {
        FileOutputStream outputStream;
        try {
            outputStream = c.openFileOutput(arqNome, Context.MODE_PRIVATE);
            int i = 0;
            for (String s : arr) {
                outputStream.write(s.getBytes());
                outputStream.write( (" R$ " + v[i]).getBytes() );
                i++;
                outputStream.write("\n".getBytes());
            }

            outputStream.close();
            Toast.makeText(c, "Salvo", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            msgAlerta(c, "Erro! Contate a empresa: " + e.toString());
        }
    }

    // salvar escolhas Saladas no pote
    // salvar escolhas
    public static void salvarEscolhasSemValor (Context c, String arqNome, String[] arr) {
        FileOutputStream outputStream;
        try {
            outputStream = c.openFileOutput(arqNome, Context.MODE_PRIVATE);
            int i = 0;
            for (String s : arr) {
                outputStream.write(s.getBytes());
                i++;
                outputStream.write("\n".getBytes());
            }

            outputStream.close();
            Toast.makeText(c, "Salvo", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            msgAlerta(c, "Erro! Contate a empresa: " + e.toString());
        }
    }

    // salvar pedido
    public static void salvarPedido (Context c, String[] arr) {
        FileOutputStream outputStream;
        try {
            outputStream = c.openFileOutput(arqPedido, Context.MODE_PRIVATE);
            for (String s : arr) {
                outputStream.write(s.getBytes());
                outputStream.write("\n".getBytes());
            }

            outputStream.close();
            //Toast.makeText(c, "Salvo", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            msgAlerta(c, "Erro! Contate a empresa: " + e.toString());
        }
    }

    // função para calcular o total do pedido
    public static String totalPedido(){
        double t = totalBebidas + totalSaladas + totalZeroGlutem
                + totalLowCarbs + totalCongelados + totalZeroAcucar;
        return String.format("%.2f", t);
    }

    // converter double[] to string[]
    public static String[] converterDoubleToString(double[] d) {
        String[] s = new String[d.length];

        for( int i = 0; i<d.length; i++) s[i] = String.format("%.2f", d[i]);

        return s;
    }

    // salvar pedido
    public static void salvarInfos (Context c, String[] arr) {
        FileOutputStream outputStream;
        try {
            outputStream = c.openFileOutput(arqInfos, Context.MODE_PRIVATE);
            for (String s : arr) {
                outputStream.write(s.getBytes());
                outputStream.write("\n".getBytes());
            }

            outputStream.close();
            Toast.makeText(c, "Salvo", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            msgAlerta(c, "Erro! Contate a empresa: " + e.toString());
        }
    }

    // alert mensagem
    public static void msgAlerta (Context c, String m) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(c);
        builder1.setMessage(m);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    // função para criar adaptador
    public static ArrayAdapter<String> criarAdaptador(Context c, String[] arr) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                c,
                android.R.layout.simple_list_item_multiple_choice,
                arr
        );
        return adapter;
    }

    // função para juntar dois arrays
    public static String[] juntarArrays(String[] nomes, double[] valores) {
        String[] vls = converterDoubleToString(valores);
        String[] saida = new String[nomes.length];

        for(int i=0; i<nomes.length; i++) {
            saida[i] = nomes[i] + " R$" + vls[i];
        }

        return saida;
    }

    // função para popular lsita
    public static void popularLista(ListView lista, ArrayAdapter<String> adp) {
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lista.setAdapter(adp);
    }

    // função para pegar selecionados da lista
    public static String[] pegarSelecionados(ListView lv, ArrayAdapter<String> adp) {
        SparseBooleanArray checked = lv.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adp.getItem(position));
        }
        String[] outputStrArr = new String[selectedItems.size()];
        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }
        return outputStrArr;
    }

    // criar alimentos para associar valor
    public static ArrayList<Alimento> criarAlimento(String[] a, double[] v) {
        ArrayList<Alimento> l_alimento = new ArrayList<>();

        for(int i =0; i<a.length; i++) {
            l_alimento.add(new Alimento(a[i], v[i]));
        }

        return l_alimento;
    }

    // pegar valor do seleciona
    public static double[] pegarValor(String[] s, ArrayList<Alimento> a, Context c) {
        double[] val = new double[s.length];

        try {
            for(int i = 0; i<s.length; i++) {
                for(int j = 0; j<a.size(); j++) {
                    if (s[i].equals(a.get(j).nome)) {
                        val[i] = a.get(j).valor;
                        break;
                    } else val[i] = 0;
                }
            }
        } catch (Throwable e) {
            msgAlerta(c, e.toString());
        }
        return val;
    }

    // calcular total da lista
    public static double calcularTotal(double[] v) {
        double total = 0;

        for(int i = 0; i<v.length; i++) total = total + v[i];

        return total;
    }
}
