package aist.demo.util;

import aist.demo.exceptions.AistBaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;


@Slf4j
public class HttpUtils {

    /**
     * Метод, выполняющий GET-запрос с поддержкой Basic Authentication
     * @param sUrl
     * @param login
     * @param pass
     * @return
     */
    public static int fireGetRequest(String sUrl, String login, String pass) {
        HttpURLConnection con = null;
        try {
            con = connect(sUrl);
            if (!StringUtils.isEmpty(login)) {
                String enc = Base64.getEncoder().encodeToString((login + ":" + pass).getBytes(StandardCharsets.UTF_8));
                con.setRequestProperty("Authorization", "Basic " + enc);
            }
            con.setRequestMethod("GET");
            return con.getResponseCode();
        } catch (IOException ex) {
            log.error("Ошибка при выполнении GET-запроса", ex);
            return 500;
        } finally {
            if (con != null )con.disconnect();
        }
    }

    private static HttpURLConnection connect(String sUrl) {
        try {
            if (sUrl.contains("https")) {
                // Для обхода ошибки с проверкой SSL-сертификата конфигурим SSLContext со своим классом-заглушкой
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, new TrustManager[]{ new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1)  {}
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                }}, new SecureRandom());
                SSLContext.setDefault(sc);
                return (HttpsURLConnection) new URL(sUrl).openConnection();
            } else
                return (HttpURLConnection) new URL(sUrl).openConnection();
        } catch ( KeyManagementException | NoSuchAlgorithmException ex) {
            log.error("Ошибка работы с SSL", ex);
            throw new AistBaseException("Ошибка работы с SSL");
        } catch (IOException e) {
            log.error("Детали IOException", e);
            throw new AistBaseException("Ошибка доступа до " + sUrl);
        }
    }

}
