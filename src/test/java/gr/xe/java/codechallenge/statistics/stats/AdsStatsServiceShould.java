package gr.xe.java.codechallenge.statistics.stats;

import gr.xe.java.codechallenge.statistics.stats.domain.AdAllStatsService;
import gr.xe.java.codechallenge.statistics.stats.infrastructure.AdAllStats;
import gr.xe.java.codechallenge.statistics.stats.infrastructure.AdAllStatsRepository;
import gr.xe.java.codechallenge.statistics.stats.infrastructure.SearchStatRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AdsStatsServiceShould {

    private static final String AD_ID = "4bb64aaa-7f35-4299-9093-d43fd9755456";
    private static final int SEARCH_COUNT = 4;
    private static final long SEARCH_ID = Long.valueOf(7);
    private static final int PAGE_COUNT = 7;
    private static final int CLICKS_COUNT = 8;
    private static final int INCREASED_SEARCH_COUNT = SEARCH_COUNT + 1;
    private static final int INCREASED_PAGE_COUNT = PAGE_COUNT + 1;
    private static final int INCREASED_CLICKS_COUNT = CLICKS_COUNT + 1;
    private static final AdAllStats EXISTING_AD_STAT = new AdAllStats(AD_ID, PAGE_COUNT, SEARCH_COUNT);
    private static final AdAllStats EXISTING_AD_STAT_2 = new AdAllStats(AD_ID, CLICKS_COUNT);

    private AdAllStatsService statsService;

    @Mock
    private AdAllStatsRepository allStatsRepository;
    @Mock
    private SearchStatRepository searchStatRepository;
    @Captor
    private ArgumentCaptor<AdAllStats> captor;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        statsService = new AdAllStatsService(allStatsRepository, searchStatRepository);
    }

    @Test
    public void updatePageCountEveryTimeAnAdIsSearchedAndDisplayedOnAPage() {
        when(allStatsRepository.findByAdId(AD_ID)).thenReturn(Optional.of(EXISTING_AD_STAT));

        statsService.updatePageCountFor(AD_ID);

        verify(allStatsRepository).save(captor.capture());
        assertThat(captor.getValue().getAdId()).isEqualTo(AD_ID);
        assertThat(captor.getValue().getPageCount()).isEqualTo(INCREASED_PAGE_COUNT);
    }

    @Test
    public void updateSearchCountWhenAdIsSearchedForTheFirstTime() {
        when(allStatsRepository.findByAdId(AD_ID)).thenReturn(Optional.empty());
        when(searchStatRepository.countByAdId(AD_ID)).thenReturn(SEARCH_COUNT);

        statsService.updateSearchesFor(AD_ID, SEARCH_ID);

        verify(allStatsRepository).save(captor.capture());
        assertThat(captor.getValue().getAdId()).isEqualTo(AD_ID);
        assertThat(captor.getValue().getPageCount()).isEqualTo(0);
        assertThat(captor.getValue().getClicksCount()).isEqualTo(0);
        assertThat(captor.getValue().getSearchCount()).isEqualTo(SEARCH_COUNT);
    }

    @Test
    public void updateSearchCountEveryTimeAnAdIsSearched() {
        when(allStatsRepository.findByAdId(AD_ID)).thenReturn(Optional.of(EXISTING_AD_STAT));
        when(searchStatRepository.countByAdId(AD_ID)).thenReturn(INCREASED_SEARCH_COUNT);

        statsService.updateSearchesFor(AD_ID, SEARCH_ID);

        verify(allStatsRepository).save(captor.capture());
        assertThat(captor.getValue().getAdId()).isEqualTo(AD_ID);
        assertThat(captor.getValue().getSearchCount()).isEqualTo(INCREASED_SEARCH_COUNT);
    }

    @Test
    public void updateClicksCountEveryTimeAnAdIsClicked() {
        when(allStatsRepository.findByAdId(AD_ID)).thenReturn(Optional.of(EXISTING_AD_STAT_2));

        statsService.updateClicksCountFor(AD_ID);

        verify(allStatsRepository).save(captor.capture());
        assertThat(captor.getValue().getAdId()).isEqualTo(AD_ID);
        assertThat(captor.getValue().getClicksCount()).isEqualTo(INCREASED_CLICKS_COUNT);
    }
}
