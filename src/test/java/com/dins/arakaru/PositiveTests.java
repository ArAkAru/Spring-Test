package com.dins.arakaru;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.dins.arakaru.DTO.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;







@SpringBootTest
class PositiveTests{
	private TestRestTemplate restTemplate;
	
	

	@BeforeEach
	void init() {
		restTemplate = new TestRestTemplate();
	}

	@Test
	void addUser() {

		User userTest2 = new User("Te", "TestUser");
		ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:8080/users", userTest2, User.class);
		restTemplate.delete("http://localhost:8080/users/" + response.getBody().getId());
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getBody().getId(), notNullValue());
		assertThat(response.getBody().getFirstName(), is("Te"));
		assertThat(response.getBody().getLastName(), is("TestUser"));
		
		User userTest15 = new User("TestTestTestTes", "TestUser");
		response = restTemplate.postForEntity("http://localhost:8080/users", userTest15, User.class);
		restTemplate.delete("http://localhost:8080/users/" + response.getBody().getId());
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getBody().getId(), notNullValue());
		assertThat(response.getBody().getFirstName(), is("TestTestTestTes"));
		assertThat(response.getBody().getLastName(), is("TestUser"));
		
		User userWithEmptyLN = new User("Test","");
		response = restTemplate.postForEntity("http://localhost:8080/users", userWithEmptyLN, User.class);
		restTemplate.delete("http://localhost:8080/users/" + response.getBody().getId());
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getBody().getId(), notNullValue());
		assertThat(response.getBody().getFirstName(), is("Test"));
		assertThat(response.getBody().getLastName(), is(""));
		
		User userRU = new User("Аркадий","Арутюн\u044F\u043D");
		response = restTemplate.postForEntity("http://localhost:8080/users", userRU, User.class);
		restTemplate.delete("http://localhost:8080/users/" + response.getBody().getId());
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(response.getBody().getId(), notNullValue());
		assertThat(response.getBody().getFirstName(), is("Аркадий"));
		assertThat(response.getBody().getLastName(), is("Арутюнян"));
		
	}

	@Test
	void getById() {
		User tempUser = new User("TEMP66", "TEMP");
		ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:8080/users", tempUser, User.class);
		ResponseEntity<User> userresponse = restTemplate
				.getForEntity("http://localhost:8080/users/" + response.getBody().getId(), User.class);
		restTemplate.delete("http://localhost:8080/users/" + response.getBody().getId());
		assertThat(userresponse.getStatusCode(), is(HttpStatus.OK));
		assertThat(userresponse.getBody().getFirstName(), is("TEMP66"));
		assertThat(userresponse.getBody().getLastName(), is("TEMP"));

	}

}
