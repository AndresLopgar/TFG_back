package andres.art_connect.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("andres.art_connect.domain")
@EnableJpaRepositories("andres.art_connect.repos")
@EnableTransactionManagement
public class DomainConfig {
}
