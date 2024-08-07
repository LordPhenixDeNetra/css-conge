package sn.css.c_s_s_conge.util;

public class EmailUtils {

    public static String getEmailMessage(String name, String host, String token){
        return "Hello " + name +"\n\n Your new acount has created. Please click the link to verify account. \n\n" +
                getVerifiactionUrl(host, token) + "\n\nThe support Team";
    }

    public static String getVerifiactionUrl(String host, String token) {
        return host +  "/api/utilisateur?token="+token;
    }

    public static String getSimpleMessage(String name, String status){
        return "Salut " + name +"\n\n Votre demande a été envoyer avec succé de congé et elle est " + status + "\n\nL'equipe CSS";
    }
}
