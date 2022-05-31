package br.com.axe.api.resources;

import br.com.axe.api.domain.User;
import br.com.axe.api.domain.dto.UserDTO;
import br.com.axe.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserResource {
    public static final String ID = "/{id}";
    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(ID)
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(service.findAll().stream().map(
                x -> mapper.map(x, UserDTO.class)
        ).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO user) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequestUri().path(ID)
                .buildAndExpand(mapper.map(service.create(
                        mapper.map(user, User.class)), UserDTO.class).getId()).toUri()).build();
    }

    @PutMapping(ID)
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO user) {
        return ResponseEntity.ok().body(
                mapper.map(service.update(id, mapper.map(user, User.class)), UserDTO.class)
        );
    }

    @DeleteMapping(ID)
    public ResponseEntity<UserDTO> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
