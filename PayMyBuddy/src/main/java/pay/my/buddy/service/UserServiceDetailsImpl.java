package pay.my.buddy.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pay.my.buddy.dal.ClientRepository;
import pay.my.buddy.entities.Client;

@Service
public class UserServiceDetailsImpl implements UserDetailsService {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final Optional<Client> user = clientRepository.findClientByEmail(email);

		if (user.isPresent()) {
			return new User(email, user.get().getPassword(), new ArrayList<GrantedAuthority>());
		} else {
			throw new InternalAuthenticationServiceException("User : " + email + ", not found!");
		}
	}

}
