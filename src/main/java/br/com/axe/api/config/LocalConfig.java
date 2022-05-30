package br.com.axe.api.config;

import br.com.axe.api.domain.User;
import br.com.axe.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {
    @Autowired
    private UserRepository repo;
    @Bean
    public void startDB() {
        User u1 = User.builder().
                name("Astolpho Pamphilo").
                email("as.tolpho@email.com").
                password("123@QAZwsx").
                build();
        User u2 = User.builder().
                name("Agripino Pamphilo").
                email("agri.pino@email.com").
                password("123@QAZwsx").
                build();

        repo.saveAll(List.of(u1, u2));
    }
}
