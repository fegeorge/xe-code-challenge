package gr.xe.java.codechallenge.statistics.ads;

import gr.xe.java.codechallenge.statistics.ads.api.RetrieveAdController;
import gr.xe.java.codechallenge.statistics.ads.domain.RetrieveService;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.Ad;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

public class RetrieveAdControllerShould {

    private static final String ADID = "c618825c-23be-4e87-9966-7c84a1b256e2";
    private static final String QUERY_WITHOUT_MATCHING_AD = "ea4c1ec4-8a50-4f8f-a97f-7432cfe5d871";
    private static final Optional<Ad> RESULT = Optional.of(new Ad());

    private RetrieveAdController retrieveAdController;

    @Mock
    private RetrieveService retrieveService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        retrieveAdController = new RetrieveAdController(retrieveService);
    }

    @Test
    public void returnMatchingAd() {
        when(retrieveService.searchForSpecificAd(ADID, false)).thenReturn(RESULT);

        ResponseEntity response = retrieveAdController.getAdDetails(ADID, false);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(RESULT);
    }

    @Test
    public void returnEmptyResultWhenNoMatchingAdWasFound() {
        when(retrieveService.searchForSpecificAd(QUERY_WITHOUT_MATCHING_AD, false)).thenReturn(Optional.empty());

        ResponseEntity response = retrieveAdController.getAdDetails(QUERY_WITHOUT_MATCHING_AD, false);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    }
}
