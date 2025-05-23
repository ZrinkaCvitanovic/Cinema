package hr.fer.kinoprojekt;

import hr.fer.kinoprojekt.application.dto.DvoranaDto;
import hr.fer.kinoprojekt.domain.model.Dvorana;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DvoranaControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/api/dvorana";

    private static final DvoranaDto TEST_DVORANA = DvoranaDto.builder()
            .ime("TestDvorana")
            .kapacitet(150)
            .otvorena(true)
            .build();

    @Test
    @Order(1)
    void createDvorana_shouldReturnSuccess() {
        ResponseEntity<String> response = restTemplate.postForEntity(
                BASE_URL,
                TEST_DVORANA,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("SUCCESS!");
    }

    @Test
    @Order(2)
    void getAllDvorana_shouldContainCreatedDvorana() {
        // Create first (if needed)
        restTemplate.postForEntity(BASE_URL, TEST_DVORANA, String.class);

        ResponseEntity<DvoranaDto[]> response = restTemplate.getForEntity(
                BASE_URL + "/all",
                DvoranaDto[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<DvoranaDto> dvorane = List.of(response.getBody());
        assertThat(dvorane).isNotEmpty();
        assertThat(dvorane).anyMatch(d -> d.getIme().equals(TEST_DVORANA.getIme()));
    }

    @Test
    @Order(3)
    void getDvorana_whenExists_shouldReturnDto() {
        // Ensure created
        restTemplate.postForEntity(BASE_URL, TEST_DVORANA, String.class);

        ResponseEntity<DvoranaDto> response = restTemplate.getForEntity(
                BASE_URL + "/" + TEST_DVORANA.getIme(),
                DvoranaDto.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DvoranaDto dvorana = response.getBody();
        assertThat(dvorana).isNotNull();
        assertThat(dvorana.getIme()).isEqualTo(TEST_DVORANA.getIme());
        assertThat(dvorana.getKapacitet()).isEqualTo(TEST_DVORANA.getKapacitet());
        assertThat(dvorana.isOtvorena()).isEqualTo(TEST_DVORANA.isOtvorena());
    }

    @Test
    @Order(4)
    void getDvorana_whenNotExists_shouldReturn404() {
        ResponseEntity<DvoranaDto> response = restTemplate.getForEntity(
                BASE_URL + "/nonexistent",
                DvoranaDto.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(5)
    void editDvorana_shouldUpdateSuccessfully() {
        // Create first
        restTemplate.postForEntity(BASE_URL, TEST_DVORANA, String.class);

        DvoranaDto updated = DvoranaDto.builder()
                .ime(TEST_DVORANA.getIme())
                .kapacitet(200)
                .otvorena(false)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DvoranaDto> requestEntity = new HttpEntity<>(updated, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.PUT,
                requestEntity,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("SUCCESS!");

        // Verify update
        ResponseEntity<DvoranaDto> getResponse = restTemplate.getForEntity(
                BASE_URL + "/" + updated.getIme(),
                DvoranaDto.class
        );

        DvoranaDto fetched = getResponse.getBody();
        assertThat(fetched).isNotNull();
        assertThat(fetched.getKapacitet()).isEqualTo(200);
        assertThat(fetched.isOtvorena()).isFalse();
    }

    @Test
    @Order(6)
    void deleteDvorana_shouldRemoveSuccessfully() {
        // Create first
        restTemplate.postForEntity(BASE_URL, TEST_DVORANA, String.class);

        ResponseEntity<String> deleteResponse = restTemplate.exchange(
                BASE_URL + "/" + TEST_DVORANA.getIme(),
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponse.getBody()).isEqualTo("SUCCESS!");

        // Confirm deletion
        ResponseEntity<DvoranaDto> getResponse = restTemplate.getForEntity(
                BASE_URL + "/" + TEST_DVORANA.getIme(),
                DvoranaDto.class
        );

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
