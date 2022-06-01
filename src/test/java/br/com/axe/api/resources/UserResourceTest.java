package br.com.axe.api.resources;

import br.com.axe.api.domain.User;
import br.com.axe.api.domain.dto.UserDTO;
import br.com.axe.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserResourceTest {

    @InjectMocks
    private UserResource resource;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper mapper;

    private User user;

    private UserDTO userDTO;

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
        when(userService.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO respUser = response.getBody();
        assertEquals(ID, respUser.getId());
        assertEquals(NAME, respUser.getName());
        assertEquals(EMAIL, respUser.getEmail());
    }

    @Test
    void findByIdWhenDataIntegrityVuolationExceptionIsThrown() {
        when(userService.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO respUser = response.getBody();
        assertEquals(ID, respUser.getId());
        assertEquals(NAME, respUser.getName());
        assertEquals(EMAIL, respUser.getEmail());
    }

    @Test
    void findAll() {
        when(userService.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<UserDTO> users = response.getBody();
        assertEquals(1, users.size());
        UserDTO respUser = users.get(0);
        assertEquals(1, respUser.getId());
        assertEquals(ID, respUser.getId());
        assertEquals(NAME, respUser.getName());
        assertEquals(EMAIL, respUser.getEmail());

    }

    @Test
    void create() {
        when(userService.create(any())).thenReturn(user);
        when(mapper.map(userDTO, User.class)).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.create(userDTO);

        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createFailedOnDataIntegrityViolationExceptionIsThrown() {
        when(userService.create(any())).thenReturn(user);
        when(mapper.map(userDTO, User.class)).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.create(userDTO);

        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void update() {
        when(userService.update(anyInt(), any())).thenReturn(user);
        when(mapper.map(userDTO, User.class)).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = resource.update(ID, userDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO respUser = response.getBody();
        assertEquals(ID, respUser.getId());
        assertEquals(NAME, respUser.getName());
        assertEquals(EMAIL, respUser.getEmail());
    }

    @Test
    void delete() {
        doNothing().when(userService).delete(anyInt());

        ResponseEntity<UserDTO> response = resource.delete(ID);

        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).delete(anyInt());
    }

    private void init() {
        user = User.builder().id(ID).name(NAME).email(EMAIL).password(PWD).build();
        userDTO = UserDTO.builder().id(ID).name(NAME).email(EMAIL).password(PWD).build();
    }
}