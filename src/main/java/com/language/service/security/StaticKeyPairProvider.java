package com.language.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class StaticKeyPairProvider implements KeyPairProvider {
    private static final String publicKeyContent = "-----BEGIN PUBLIC KEY-----\n" +
            "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAseJaRjqARJXL1pNKecKu\n" +
            "q3OM1sff7SeieWmF0lHaFeQRWO1LHtzunsDZptewb/8lkGCWfqtIX3AOmkTf6inr\n" +
            "IOgjstcyqEoXU5nUfXFPINxAzAzW3wEcAUd2C7UWO1wHXBHXe023k6ATIqNuGfi9\n" +
            "AdTUnKGEqadXRlRNT4z5hI3h1SMAt1diLpm/DyA96IFYZBkAer9mANOkNv4E204H\n" +
            "L7KvYlPANJKdFSCmj90VzRdFBrYsHsH6G10gK7tgIuYz2eRk6CcehTJAhSgSCJfF\n" +
            "1lVM6f3GvYsVp9sgfNryaAPVrD8sNRV3B02DjNonKgyRuaj/+jURRQX7WMLw69rw\n" +
            "yz1juUYa2M4UjQfj6pT6Wg2FrhbaWmPYBbepuN7kERqphRz9LQU1PQQR2UCF1MML\n" +
            "VxXNY0hh2r42L7HxtkmlsbrKDkeVrykpG5xC6WLOrYxofXzA8oFosfzp31f4gyHM\n" +
            "aEbKUMQf5PkWf/Rk5TEpc6fJoswwcqYdWS97nxOpDdzZ9/7cFs3BfdeKiGQxfnKI\n" +
            "BI+g2Blw1cY9t+rnEyUrwFK3X5VjONqG0EA0fRzJsoBceILxHMAqOewjw3ATPrNY\n" +
            "H/ekaHGP4HQD5ccNGNCOo8m0ANyxSJKFu+r25GBg2wNeLiFXSWEM8/qogoLKrtZa\n" +
            "wWqCfFY5pTpL2J63kqR16esCAwEAAQ==\n" +
            "-----END PUBLIC KEY-----";

    private static final String privateKeyContent = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIJQgIBADANBgkqhkiG9w0BAQEFAASCCSwwggkoAgEAAoICAQCx4lpGOoBElcvW\n" +
            "k0p5wq6rc4zWx9/tJ6J5aYXSUdoV5BFY7Use3O6ewNmm17Bv/yWQYJZ+q0hfcA6a\n" +
            "RN/qKesg6COy1zKoShdTmdR9cU8g3EDMDNbfARwBR3YLtRY7XAdcEdd7TbeToBMi\n" +
            "o24Z+L0B1NScoYSpp1dGVE1PjPmEjeHVIwC3V2Iumb8PID3ogVhkGQB6v2YA06Q2\n" +
            "/gTbTgcvsq9iU8A0kp0VIKaP3RXNF0UGtiwewfobXSAru2Ai5jPZ5GToJx6FMkCF\n" +
            "KBIIl8XWVUzp/ca9ixWn2yB82vJoA9WsPyw1FXcHTYOM2icqDJG5qP/6NRFFBftY\n" +
            "wvDr2vDLPWO5RhrYzhSNB+PqlPpaDYWuFtpaY9gFt6m43uQRGqmFHP0tBTU9BBHZ\n" +
            "QIXUwwtXFc1jSGHavjYvsfG2SaWxusoOR5WvKSkbnELpYs6tjGh9fMDygWix/Onf\n" +
            "V/iDIcxoRspQxB/k+RZ/9GTlMSlzp8mizDByph1ZL3ufE6kN3Nn3/twWzcF914qI\n" +
            "ZDF+cogEj6DYGXDVxj236ucTJSvAUrdflWM42obQQDR9HMmygFx4gvEcwCo57CPD\n" +
            "cBM+s1gf96RocY/gdAPlxw0Y0I6jybQA3LFIkoW76vbkYGDbA14uIVdJYQzz+qiC\n" +
            "gsqu1lrBaoJ8VjmlOkvYnreSpHXp6wIDAQABAoICAGjTyHlMblx8nkQURrZceQzG\n" +
            "qn/EB/089y1vdxJLdCakYWizZiqI7gIWCe2de/d0N9HOacG59ItxgNl+rynSjCK3\n" +
            "SppQ4SR1jSuSIrvN82CdC350Lof6omNKlpEtpS1T/W5PgVnDoeRYWA54myCpCdEz\n" +
            "eBvZv1EvMWpbsbeojz6DUkuFNh3VXCG0Lvid+n2zA8DxTuwHMPhZwJM7vOb0I54D\n" +
            "QgHC8yjIIdPaiu2KzxcumFntLzDtBRPtHFFJOc03NeU5CBIbQ4wg4g7WX1I7BrZZ\n" +
            "TFs0V1NJzHg/1PRfHq0XXWsFS2r4/E0Ma/KseJn6EEKGCluLvGKdtNJJBysmufE/\n" +
            "3QOW27BwVrVyrA1ioj5pfX/2L+lO50RN6taSoy/CZ+DbCEIaHkqSaQ+CASIWPGvX\n" +
            "WpqJpL++gLHhH3bmv++375pbRe9oihPGU3wgDXQwtwkxe8j2hrkN+JV2Fv7gcgPe\n" +
            "b2Bjvy2nBLghoI0onGQgff9jMzN8a9BchjHx7Mm4FyDKxnb+mJ70uhM3IOOGQ3sx\n" +
            "LhZeSHagraAW2IzPlHrFHiYd7iga7tephcK1KXzmgEZG4V9BphEkz6l9Esl2ef1H\n" +
            "HCV+bV05Ws1D2xv0oRmTdvnvH1u46DoLOUpo82hZ60P1euHFWqZ6gMUn7GYAfBBF\n" +
            "kzS+DLu5z8PWt4oiqdd5AoIBAQDbncvOyuVwY7Oe92KRYls+d+ysHAFCtPMTevVo\n" +
            "RPdhxXshILHmMiTAmWtLjuQJ6qCOTZJBMSf4/+Q8acz/PQazA47ZVLSWjZ4Nk211\n" +
            "kIFAJJo6m1cABHuPhA24pBjvGkeWXWId8SVQh4f7V6zhbaVWzRZXmzbIsXT0uog8\n" +
            "HeHa0S5svAxphn7GuiQn41u3aSpyvWqltmv6yOTzEagfs/PR+IePZNxfjLoyRQi6\n" +
            "T2WCpwp4FINwirWUvX93OcDSpXjHj0/9IH0r0cEOi98HEgrxmq1ArutHb/IlNoc6\n" +
            "cKeDPasB6kKhaXErNUqXcqblIrYASXRus/NDFINcp88jQjL/AoIBAQDPWqRcaXSJ\n" +
            "JXq9JICLuC0vBaHyY/BCyUL7XdscWx2iwQFrnghkaqyPCbh2BIIftsg6mwOYbQCD\n" +
            "u5Cx96r+krP6ztCLb+/1EG2+5JofaQgLoQaQp7xNIK8dYyUJrmkTK9oKGDASYf24\n" +
            "TiyDy5X2960q/AGDg1oo5f2CKNsGgiqGIpRpJV1kt4XNH5bnEYN3bhfJbUb25PgM\n" +
            "ZqCZ+50lNcNiflOw70STzRFkZRv7C4f6ckIHLbe/wW+78S8bdIreLRBE4T/4YiIS\n" +
            "kHn1h/R2C+rhrhuQi+1V+5RPe7HhAnDbyDU4Lzhrz8pkBSCs1VEwK2YkULSx5PYz\n" +
            "4jtYDyKMt0UVAoIBAG1pPcI2XvwRD8mgoaCgiaijgmt0QESFdZw848ogocY55k/o\n" +
            "w/hU/u5dAJFSDNXL6FU83CJSZQGuKnzWkOSP+Khym1Y21wkyYI2O5zds2JemJJgS\n" +
            "D9PYZ95mAZf9WNa2FvgYaetU2iw2eU6ErYJZZ8fIwNrAXxi5H/VUgM7CbGThVemh\n" +
            "/eUMtlg3IBAnOugzIoyrRVhte3ojX7yhDUtOEaiH0rFjpE6OA61Lqz92krH2mkac\n" +
            "oacN08zYY4p4YVIQUg3JniYTa4jLJQ0kLeaN3JnlX28cNecPJGnZsqEwwqOls25u\n" +
            "0Octas4F7LVqzy7UjiuybKlCSW0f6RneGFoOfu0CggEAQ4ZDDA0TdC4OYFXovpdF\n" +
            "r5BoAqVk8nrUI0OXPQ63Ae9UZRVbqkfK1fHWbihdr7I4Vk1wyEyG5F9KaYo71xcz\n" +
            "ITQEWcQkImwtMQME5cGwawkPOEBHY96G6E0HHLX9Zc7b/HmP/gyHMWPTvz+nIHvb\n" +
            "mqniXE+EJtluyto/KUluS6wrJGqUlV3mZrwoyiq740Ko4GGDwJcj5/XryvlTXXC6\n" +
            "pfJ5kp74eCFJzqXWjNRefcKDmg7bTcjSeCO8sXYsWtnV+HhZ+qsKaX0/GiQiCecr\n" +
            "oD6wn0vSCDQbQh8H+DxxQEFcg2VtPD64rAW3dFErVUJsytq1XVEwlAjSRoT3PVfZ\n" +
            "WQKCAQEAyTk7iqj93WnYgc+FzsdoZdTYVCqIHcmiBOaAVFYUIp+97LeRP7NjStMe\n" +
            "2KzeB5NCxALnpqdPmvOWtBqhFFW+BDn5McOCE3nER7bgPmiLUWw/PkzuxOAzhHMQ\n" +
            "HQVc9gacTpJfsKc5T3IhaO6zTwiiLm6n8WpRRwApWIP45DiQZph1F4XoTtomiDV7\n" +
            "lcDDgb5vqV1zupP4Y+FVSDGGJUFXJCYmDe/YNoz5TtWi5d4rC9wMkCx6vpgxXxAX\n" +
            "1gNfLuBnSebrhnXLZE/Kc7aWB7fnxYgqc3zLbwFbUNCQ0WxH+4tG0aU0b63GGHtI\n" +
            "/v4ciQEgkC3TM1FtL6KzV/Wf2ipNtQ==\n" +
            "-----END PRIVATE KEY-----";
    private static final Logger logger = LoggerFactory.getLogger(StaticKeyPairProvider.class);

    @Override
    public RSAPublicKey getRsaPublicKey() {
        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //URL url = classLoader.getResource("/keys/public_key.pem");


        //if (url != null) {
        //
        //} else {
        //    logger.debug("Could not get url for public key file");
        //    return null;
        //}


        try {
            //Resource resource = new ClassPathResource("classpath:/keys/public_key.pem");
            //InputStream inputStream = resource.getInputStream();
            //
            //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            //String line;
            //StringBuffer sb = new StringBuffer();
            //while ((line = reader.readLine()) != null){
            //    sb.append(line);
            //}
            String content = publicKeyContent;

            content = content.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(content));
            return (RSAPublicKey) kf.generatePublic(keySpecX509);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    @Override
    public RSAPrivateKey getRsaPrivateKey() {
        try {
            String content = privateKeyContent;
            content = content.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");

            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(content));
            return (RSAPrivateKey) kf.generatePrivate(keySpecPKCS8);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("", e);
            return null;
        }
    }
}
