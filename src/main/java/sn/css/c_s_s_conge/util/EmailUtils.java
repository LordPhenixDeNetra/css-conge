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
        return "Salut " + name +"\n\n Votre demande de congé a été envoyer avec succés et elle est " + status + "\n\nL'equipe CSS";
    }

    public static String getSimpleMessageForDMTValidation(String name){
        return "Salut " + name +"\n\n Votre DMT a été valider avec succés vous pouvez faire votre demande" + "\n\nL'equipe CSS";
    }
    public static String getSimpleMessageForDMTNonValidation(String name){
        return "Salut " + name +"\n\n Votre DMT n'a été valider veuillez refaire l'envoi des informations tout en les vérifiant" + "\n\nL'equipe CSS";
    }
}
