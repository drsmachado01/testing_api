package br.com.axe.api.services;

import br.com.axe.api.domain.User;
import br.com.axe.api.domain.dto.UserDTO;
import br.com.axe.api.repositories.UserRepository;
import br.com.axe.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper modelMapper;

    private User user;

    private UserDTO userDTO;

    private Optional<User> optUser;

    private static final Integer ID = 1;
    private static final String NAME = "Astolpho Pamphilo";
    private static final String EMAIL = "as.tolpho@email.com";
    private static final String PWD = "123@qazWSX";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        init();
    }

    @Test
    void findById() {
        when(repository.findById(anyInt())).thenReturn(optUser);
        User oUsr = userService.findById(ID);
        assertNotNull(oUsr);
        assertEquals(User.class, oUsr.getClass());
        assertEquals(ID, oUsr.getId());
        assertEquals(NAME, oUsr.getName());
        assertEquals(EMAIL, oUsr.getEmail());
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(user));
        List<User> users = userService.findAll();
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(ID, users.get(0).getId());
    }

    @Test
    void create() {
        when(repository.save(user)).thenReturn(user);
        User newUser = userService.create(user);
        assertNotNull(newUser);
        assertEquals(ID, newUser.getId());
        assertEquals(NAME, newUser.getName());
        assertEquals(EMAIL, newUser.getEmail());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void init() {
        user = User.builder().id(ID).name(NAME).email(EMAIL).password(PWD).build();
        userDTO = UserDTO.builder().id(ID).name(NAME).email(EMAIL).password(PWD).build();
        optUser = Optional.of(User.builder().id(ID).name(NAME).email(EMAIL).password(PWD).build());
    }
}