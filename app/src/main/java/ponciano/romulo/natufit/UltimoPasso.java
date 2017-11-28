package ponciano.romulo.natufit;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;


public class UltimoPasso extends Fragment {
    View myView;

    // testando EMAIL SEND auto
    private static final String username = "natufitpedidos@gmail.com";
    private static final String password = "!#7natufit7#!";
    private Multipart _multipart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_revisar_pedido, container, false);

        getActivity().setTitle("Enviar Pedido");

        // setando widgets
        final Button confirmar = (Button) myView.findViewById(R.id.btn_salvar);
        final TextView pedido = (TextView) myView.findViewById(R.id.txt_pedido);
        final ScrollView sv = (ScrollView) myView.findViewById(R.id.sv);

        confirmar.setText("Enviar Pedido");

        // mostrando pedido
        pedido.setText("");
        sv.setVerticalScrollBarEnabled(false);
        sv.setHorizontalScrollBarEnabled(false);
        ArrayList<String> menu = new ArrayList<>();
        // se arq existir.. mostrar...
        if( FuncoesGlobais.verificarArq(getContext(), FuncoesGlobais.arqPedido) ) {
            pedido.append("\n");
            menu = FuncoesGlobais.carregarPedido(getContext(), FuncoesGlobais.arqPedido);
            FuncoesGlobais.preencherTextView(menu, pedido);
        }


        // clickar no botão de enviar pedido
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // raphaela.custodios@gmail.com
                String email = "raphaela.custodios@gmail.com";
                String subject = FuncoesGlobais.nomeQuemPediu + " " + FuncoesGlobais.numeroQuemPediu;
                String message = pedido.getText().toString();

                sendMail(email, subject, message);
            }
        });

        return myView;
    }

    private void sendMail(String email, String subject, String messageBody)
    {
        Session session = createSessionObject();

        try {
            Message message = createMessage(email, subject, messageBody, session);
            new SendMailTask().execute(message);
            FuncoesGlobais.msgAlerta(getContext(), "Pedido enviado! \n Aguarde confirmação via whatsapp :)");
            FuncoesGlobais.deletarTodosArq(getContext());
        } catch (AddressException e) {
            FuncoesGlobais.msgAlerta(getContext(), "Erro ao enviar! Entre em contato com a empresa!");
            e.printStackTrace();
        } catch (MessagingException e) {
            FuncoesGlobais.msgAlerta(getContext(), "Erro ao enviar! Entre em contato com a empresa!");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            FuncoesGlobais.msgAlerta(getContext(), "Erro ao enviar! Entre em contato com a empresa!");
            e.printStackTrace();
        }
    }

    public void addAttachment(String filename) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);

        _multipart.addBodyPart(messageBodyPart);
    }
    private Message createMessage(String email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username, "Natufit Pedidos"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }

    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getContext(), "Por favor, espera...", "Enviando pedido...", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
