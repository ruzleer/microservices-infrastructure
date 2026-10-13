package com.example.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
class CircuitBreakerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnFallbackWhenDownstreamFails() {
        // given — WireMock отвечает ошибкой 500
        stubFor(get(urlEqualTo("/api/users"))
                .willReturn(aResponse().withStatus(500)));

        // when/then — после нескольких запросов получаем fallback
        for (int i = 0; i < 10; i++) {
            webTestClient.get()
                    .uri("/api/users")
                    .exchange();
        }

        webTestClient.get()
                .uri("/api/users")
                .exchange()
                .expectStatus().isEqualTo(503)
                .expectBody()
                .jsonPath("$.service").isEqualTo("user-service")
                .jsonPath("$.error").isEqualTo("Service Unavailable");
    }
}