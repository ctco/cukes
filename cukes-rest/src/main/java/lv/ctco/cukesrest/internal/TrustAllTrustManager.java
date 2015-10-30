package lv.ctco.cukesrest.internal;

import javax.net.ssl.*;
import java.security.*;

// TODO: Move to other package
public class TrustAllTrustManager implements TrustManager,
        javax.net.ssl.X509TrustManager {

    /**
     * Method to trust all the HTTPS certificates. To be used only in the
     * development environment for convenience sake
     */
    public static void trustAllHttpsCertificates() {
        try {
            javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
            javax.net.ssl.TrustManager tm = new TrustAllTrustManager();
            trustAllCerts[0] = tm;
            javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                    .getInstance("SSL");
            javax.net.ssl.SSLSessionContext sslsc = sc
                    .getServerSessionContext();
            sslsc.setSessionTimeout(0);
            sc.init(null, trustAllCerts, null);
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
                    .getSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
        return true;
    }

    public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
        return true;
    }

    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                                   String authType) throws java.security.cert.CertificateException {

        return;
    }

    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
                                   String authType) throws java.security.cert.CertificateException {
        return;
    }
}
