package com.example.application.views.Email;

import java.io.IOException;


public class Email {

    public void sendMessage(String comanda,String to,String Plecare,String Destinatie,String OraP,String OraAjung,String Tarif,String loc,String nrInmat,String nume) throws IOException {
        Process p = Runtime.getRuntime().exec("python E:\\Adrian\\PoliHack\\pythonMail\\main.py "+comanda+" "+to + " "+ Plecare + " "+ Destinatie  + " "+ OraP  + " "+ OraAjung
                + " "+ Tarif  + " "+ loc + " "+ nrInmat  + " "+ nume);

    }

}
