package andres.art_connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@SpringBootApplication(scanBasePackages = "andres.art_connect")
public class ArtConnectApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ArtConnectApplication.class, args);
    }
}
