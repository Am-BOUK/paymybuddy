package pay.my.buddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import pay.my.buddy.dal.ClientRepository;
import pay.my.buddy.entities.Client;

@ExtendWith(MockitoExtension.class)
class UserServiceDetailsTest {
	
	@Mock
	private ClientRepository clientRepository;
	
	@InjectMocks
	private UserServiceDetailsImpl userServiceDetais;

	@Test
	public void testLoadUserByUsername() {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		Optional<Client> optionalClient = Optional.of(client);
		when(clientRepository.findClientByEmail("test@test.com")).thenReturn(optionalClient);
		
		UserDetails result = userServiceDetais.loadUserByUsername("test@test.com");
		
		assertEquals(result.getUsername(), "test@test.com");
		
	}

	@Test
	public void testLoadUserByUsername_whenUserDoesNotExist() {
//		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
	
		
		try {
			userServiceDetais.loadUserByUsername("test@test.com");

		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("User : test@test.com, not found!");
		}		
	}
	
}
