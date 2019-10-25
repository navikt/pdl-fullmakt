package no.nav.pdl.fullmakt.app;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.getenv;
import static org.springframework.util.ResourceUtils.getFile;

public class LocalAppStarter {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local");
        System.setProperty("ENVIRONMENT_CLASS", "preprod");
        System.setProperty("NAIS_NAMESPACE", "default");

        try (InputStream inputStream = new FileInputStream(getFile(getenv("PERSON_CONFIG_LOCATION") + "pdl-fullmakt.properties"))) {
            Properties properties = new Properties();
            properties.load(inputStream);
            properties.forEach((key, value) -> System.setProperty(key.toString(), value.toString()));
        } catch (Exception e) {
            throw new RuntimeException("Finner ikke config på PATH.");
        }

        AppStarter.main(args);
    }
}
