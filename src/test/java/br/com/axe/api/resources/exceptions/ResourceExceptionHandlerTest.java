package br.com.axe.api.resources.exceptions;

import br.com.axe.api.services.exceptions.DataIntegrityViolationException;
import br.com.axe.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler resourceExceptionHandler;

    private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado!";

    private static final String EMAIL_JA_CADASTRADO_NO_SISTEMA = "Email já cadastrado no sistema!";

    @BeforeEach
    void setUp() {
        try {
            MockitoAnnotations.openMocks(this);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void objectNotFound() {
        ResponseEntity<StandardError> response = resourceExceptionHandler.objectNotFound(
                new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO),
                new MockHttpServletRequest()
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        StandardError error = response.getBody();
        assertEquals(OBJETO_NAO_ENCONTRADO, error.getError());
        assertEquals(404, error.getStatus());
    }

    @Test
    void emailAlreadyExists() {
        ResponseEntity<StandardError> response = resourceExceptionHandler.emailAlreadyExists(
                new DataIntegrityViolationException(EMAIL_JA_CADASTRADO_NO_SISTEMA),
                new MockHttpServletRequest()
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        StandardError error = response.getBody();
        assertEquals(EMAIL_JA_CADASTRADO_NO_SISTEMA, error.getError());
        assertEquals(400, error.getStatus());
    }
}