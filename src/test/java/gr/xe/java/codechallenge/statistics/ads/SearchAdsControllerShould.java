package gr.xe.java.codechallenge.statistics.ads;

import gr.xe.java.codechallenge.statistics.ads.api.SearchAdsController;
import gr.xe.java.codechallenge.statistics.ads.domain.exception.InvalidRequestException;
import gr.xe.java.codechallenge.statistics.ads.domain.SearchService;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.entity.Ad;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

public class SearchAdsControllerShould {

    private static final int PAGE = 0;
    private static final int SIZE = 20;
    private static final String QUERY = "Nea Smirni";
    private static final String INVALID_QUERY = "a";
    private static final String QUERY_WITHOUT_MATCHING_ADS = "This will not find any results";
    private static final String INVALID_QUERY_MESSAGE = "Minimum query size is 3";
    private static final List<Ad> ADS = asList(new Ad());
    private static final Page RESULTS = new PageImpl(ADS);
    private static final Page NO_RESULTS = new PageImpl(emptyList());

    private SearchAdsController searchAdsController;

    @Mock
    private SearchService searchService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        searchAdsController = new SearchAdsController(searchService);
    }

    @Test
    public void returnErrorWhenRequestIsInvalid() {
        when(searchService.searchForAds(INVALID_QUERY, PAGE, SIZE)).thenThrow(new InvalidRequestException(INVALID_QUERY_MESSAGE));

        ResponseEntity response = searchAdsController.search(INVALID_QUERY, PAGE, SIZE);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(INVALID_QUERY_MESSAGE);
    }

    @Test
    public void returnEmptyResultsWhenNoMatchingAdsWereFound() {
        when(searchService.searchForAds(QUERY_WITHOUT_MATCHING_ADS, PAGE, SIZE)).thenReturn(NO_RESULTS);

        ResponseEntity response = searchAdsController.search(QUERY_WITHOUT_MATCHING_ADS, PAGE, SIZE);

        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
    }

    @Test
    public void returnMatchingAds() {
        when(searchService.searchForAds(QUERY, PAGE, SIZE)).thenReturn(RESULTS);

        ResponseEntity response = searchAdsController.search(QUERY, PAGE, SIZE);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(RESULTS);
    }
}
