package br.com.axe.api.services;

import br.com.axe.api.domain.User;
import br.com.axe.api.repositories.UserRepository;
import br.com.axe.api.services.exceptions.DataIntegrityViolationException;
import br.com.axe.api.services.exceptions.ObjectNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper modelMapper;

    private User user;

    private Optional<User> optUser;

    private static final Integer ID = 1;
    private static final String NAME = "Astolpho Pamphilo";
    private static final String EMAIL = "as.tolpho@email.com";
    private static final String PWD = "123@qazWSX";
    private static final String NEW_EMAIL = "astrogild@email.com";

    private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado!";

    private static final String EMAIL_JA_CADASTRADO_NO_SISTEMA = "Email já cadastrado no sistema!";

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
    void findByIdObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
        try {
            userService.findById(1);
        } catch (Exception ex) {
            assertObjectNotFoundException(ex);
        }
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(user));
        List<User> users = userService.findAll();
        assertNotNull(users);
        assertEquals(1, users.size());
        User user0 = users.get(0);
        assertEquals(User.class, user0.getClass());
        assertEquals(NAME, user0.getName());
        assertEquals(EMAIL, user0.getEmail());
        assertEquals(PWD, user0.getPassword());
    }

    @Test
    void create() {
        when(repository.save(any())).thenReturn(user);
        User newUser = userService.create(user);
        assertNotNull(newUser);
        assertEquals(ID, newUser.getId());
        assertEquals(NAME, newUser.getName());
        assertEquals(EMAIL, newUser.getEmail());
    }

    @Test
    void testCreateWithAnExistingEmail() {
        when(repository.findByEmail(anyString())).thenReturn(optUser);
        try {
            user.setId(null);
            userService.create(user);
        } catch (Exception ex) {
            assertDataIntegrityViolationException(ex);
        }
    }

    @Test
    void update() {
        when(repository.findById(anyInt())).thenReturn(optUser);
        when(repository.findByEmail(anyString())).thenReturn(optUser);
        when(repository.save(any())).thenReturn(user);
        User updUser = user;
        String newPwd = "123@QAZwsx";
        updUser.setPassword(newPwd);
        updUser = userService.update(ID, updUser);
        assertNotNull(updUser);
        assertEquals(ID, updUser.getId());
        assertEquals(NAME, updUser.getName());
        assertEquals(EMAIL, updUser.getEmail());
        assertEquals(newPwd, updUser.getPassword());
    }

    @Test
    void updateWhenObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
        try {
            userService.update(ID, user);
        } catch (Exception ex) {
            assertObjectNotFoundException(ex);
        }
    }

    @Test
    void updateWhenEmailAlreadyExists() {
        when(repository.findById(anyInt())).thenReturn(optUser);
    when(repository.findByEmail(anyString())).thenReturn(
            Optional.ofNullable(User.builder()
                    .id(2).name("Astrogildo Genova").email("astrogildo@email.com").password("123@WSXqaz")
                    .build()));
        try {
            User updUser = user;
            String newPwd = "123@QAZwsx";
            updUser.setPassword(newPwd);
            updUser.setEmail(NEW_EMAIL);
            userService.update(ID, updUser);
        } catch (Exception ex) {
            assertDataIntegrityViolationException(ex);
        }
    }

    @Test
    void delete() {
        when(repository.findById(anyInt())).thenReturn(optUser);
        doNothing().when(repository).delete(any());
        userService.delete(ID);
        Mockito.verify(repository, times(1)).delete(any());
    }

    @Test
    void deleteWhenObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
        try {
            userService.delete(ID);
        } catch (Exception ex) {
            assertObjectNotFoundException(ex);
        }
    }

    private void assertObjectNotFoundException(Exception ex) {
        assertEquals(ObjectNotFoundException.class, ex.getClass());
        assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
    }

    private void assertDataIntegrityViolationException(Exception ex) {
        assertEquals(DataIntegrityViolationException.class, ex.getClass());
        assertEquals(EMAIL_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
    }

    private void init() {
        user = User.builder().id(ID).name(NAME).email(EMAIL).password(PWD).build();
        optUser = Optional.of(User.builder().id(ID).name(NAME).email(EMAIL).password(PWD).build());
    }
}