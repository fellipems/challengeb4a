package com.challenge.b4a.utils;

import java.util.ResourceBundle;

public class Mensagens {
    public static String usar(String idMensagem) {
        return ResourceBundle.getBundle("messages_pt").getString(idMensagem);
    }
}
