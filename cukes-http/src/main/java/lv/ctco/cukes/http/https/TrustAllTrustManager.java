package lv.ctco.cukes.http.https;

import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@SuppressWarnings("SameReturnValue")
public class TrustAllTrustManager implements TrustManager, javax.net.ssl.X509TrustManager {

    /**
     * Method to trust all the HTTPS certificates. To be used only in the
     * development environment for convenience sake
     */
    public static void trustAllHttpsCertificates() {
        try {
            javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
            javax.net.ssl.TrustManager tm = new TrustAllTrustManager();
            trustAllCerts[0] = tm;
            javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
            javax.net.ssl.SSLSessionContext serverSessionContext = sc.getServerSessionContext();
            serverSessionContext.setSessionTimeout(0);
            sc.init(null, trustAllCerts, null);
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException | NoSuchAlgorithmException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    @Override
    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        // Explicitly do nothing
    }

    @Override
    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        // Explicitly do nothing
    }
}
