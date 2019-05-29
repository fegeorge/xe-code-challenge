package gr.xe.java.codechallenge.statistics.ads;

import gr.xe.java.codechallenge.statistics.ads.domain.InvalidRequestException;
import gr.xe.java.codechallenge.statistics.ads.domain.SearchAdsService;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.Ad;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.AdsPagingRepository;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.SearchQuery;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.SearchQueryRepository;
import gr.xe.java.codechallenge.statistics.stats.domain.StatsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SearchAdsServiceShould {

    private static final String QUERY = "Nea Smirni";
    private static final String INVALID_QUERY = "ab";
    private static final long SEARCH_ID = Long.valueOf(7);
    private static final int PAGE = 0;
    private static final int SIZE = 20;
    private static final int INVALID_PAGE = -1;
    private static final int INVALID_SIZE = -1;
    private static final Ad AD_1 = new Ad("1");
    private static final Ad AD_2 = new Ad("2");
    private static final Page RESULTS = new PageImpl(asList(AD_1, AD_2));
    private static final Optional<SearchQuery> SEARCH_QUERY = Optional.of(new SearchQuery(SEARCH_ID, QUERY));

    private SearchAdsService searchAdsService;

    @Mock
    private AdsPagingRepository adsPagingRepository;

    @Mock
    private StatsService statsService;

    @Mock
    private SearchQueryRepository queryRepository;

    @Captor
    private ArgumentCaptor<String> adsCaptor;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        searchAdsService = new SearchAdsService(adsPagingRepository, statsService, queryRepository);
    }

    @Test(expected = InvalidRequestException.class)
    public void throwInvalidRequestExceptionWhenQuerySizeIsLessThanThree() {
        searchAdsService.searchForAds(INVALID_QUERY, PAGE, SIZE);
    }

    @Test(expected = InvalidRequestException.class)
    public void throwInvalidRequestExceptionWhenPageIsNotAPositiveInteger() {
        searchAdsService.searchForAds(INVALID_QUERY, INVALID_PAGE, SIZE);
    }

    @Test(expected = InvalidRequestException.class)
    public void throwInvalidRequestExceptionWhenSizeIsNotAPositiveInteger() {
        searchAdsService.searchForAds(INVALID_QUERY, PAGE, INVALID_SIZE);
    }

    @Test
    public void returnMatchingAds() {
        when(adsPagingRepository.findByTextIgnoreCaseContaining(QUERY, PageRequest.of(PAGE, SIZE))).thenReturn(RESULTS);
        when(adsPagingRepository.findByTextIgnoreCaseContaining(QUERY, Pageable.unpaged())).thenReturn(RESULTS);
        when(queryRepository.findSearchQueryByQueryText(QUERY)).thenReturn(SEARCH_QUERY);

        Page<Ad> actualAds = searchAdsService.searchForAds(QUERY, PAGE, SIZE);

        assertThat(actualAds).isEqualTo(RESULTS);
    }

    @Test
    public void generateStatsForSearch() {
        when(adsPagingRepository.findByTextIgnoreCaseContaining(QUERY, PageRequest.of(PAGE, SIZE))).thenReturn(RESULTS);
        when(adsPagingRepository.findByTextIgnoreCaseContaining(QUERY, Pageable.unpaged())).thenReturn(RESULTS);
        when(queryRepository.findSearchQueryByQueryText(QUERY)).thenReturn(SEARCH_QUERY);

        searchAdsService.searchForAds(QUERY, PAGE, SIZE);

        verify(statsService, times(2)).updateSearchesFor(adsCaptor.capture(), idCaptor.capture());
        assertThat(adsCaptor.getAllValues().get(0)).isEqualTo(AD_1.getId());
        assertThat(adsCaptor.getAllValues().get(1)).isEqualTo(AD_2.getId());
        assertThat(idCaptor.getValue()).isEqualTo(SEARCH_ID);
    }
}