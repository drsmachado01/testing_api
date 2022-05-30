package br.com.axe.api.resources;

import br.com.axe.api.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserResource {
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(User.builder()
                        .id(1)
                        .name("Astolpho Pamphilo")
                        .email("as.tolpho@email.com")
                        .password("123456")
                .build());
    }
}
