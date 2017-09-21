package setup;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Configuration {

    private static final String SECRETS_FILE = "secrets.json";

    public static String setupToken() {
        String token;
        File file = new File(SECRETS_FILE);
        /* check if file exists */
        if(file.exists()) {
            try {
                /* read file and get token */
                JSONTokener tokener = new JSONTokener(new FileInputStream(file));
                JSONObject jsonobj = new JSONObject(tokener);
                token = jsonobj.getString("token");
                /* return token if valid */
                if(validate_token(token)) {
                    return token;
                }
            } catch (FileNotFoundException | JSONException ignored) {
            }
            /* if not returned, delete file */
            file.delete();
        }
        /* you've got 3 tries */
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        for(int n = 3; n > 0; n--) {
            /* delete file if existing */
            if (file.exists()) {
                file.delete();

                /* prompt for token */
                System.out.println("Enter you'r token:");
                try {
                    try {
                        /* read line and close readers */
                        String line = br.readLine();
                        token = line.trim();
                    } catch (NullPointerException ignore) {
                        break;
                    }
                    if(!validate_token(token)) {
                        continue;
                    }

                    /* write token to file */
                    JSONObject jsonobj = new JSONObject();
                    jsonobj.put("token", token);
                    FileWriter fw = new FileWriter(file);
                    fw.write(jsonobj.toString());
                    fw.close();

                    /* close buffers */
                    br.close();
                    isr.close();
                    return token;
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    /* close buffers */
                    try {
                        br.close();
                        isr.close();
                    } catch (IOException ignored) {
                    }
                    break;
                }
            }
        }
        System.out.println("Ragequit!!");
        Runtime.getRuntime().exit(1);
        return null;
    }

    private static boolean validate_token(String token) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);

        builder.setToken(token);

        try {
            JDA jda = builder.buildBlocking();
            jda.shutdownNow();
            return true;
        } catch (InterruptedException | RateLimitedException | LoginException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
