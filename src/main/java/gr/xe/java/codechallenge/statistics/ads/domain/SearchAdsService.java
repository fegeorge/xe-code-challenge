package gr.xe.java.codechallenge.statistics.ads.domain;

import gr.xe.java.codechallenge.statistics.ads.infrastructure.Ad;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.AdsPagingRepository;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.SearchQuery;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.SearchQueryRepository;
import gr.xe.java.codechallenge.statistics.stats.domain.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchAdsService implements SearchService {

    private static final String INVALID_QUERY = "Minimum query size is 3";
    private static final String INVALID_PAGE = "Page should be a non negative integer";
    private static final String INVALID_SIZE = "Size should be a positive integer";
    private AdsPagingRepository adsPagingRepository;
    private SearchQueryRepository searchQueryRepository;
    private StatsService statsService;

    @Autowired
    public SearchAdsService(AdsPagingRepository adsPagingRepository, StatsService statsService, SearchQueryRepository searchQueryRepository) {
        this.adsPagingRepository = adsPagingRepository;
        this.statsService = statsService;
        this.searchQueryRepository = searchQueryRepository;
    }

    @Override
    public Page<Ad> searchForAds(String query, int page, int size) {
        validateQuery(query);
        validatePage(page);
        validateSize(size);
        Page<Ad> pagedResults = adsPagingRepository.findByTextIgnoreCaseContaining(query, PageRequest.of(page, size));

        Optional<SearchQuery> searchQuery = searchQueryRepository.findSearchQueryByQueryText(query);
        if (!searchQuery.isPresent()) {
            searchQueryRepository.save(new SearchQuery(query));
            long searchId = searchQueryRepository.findSearchQueryByQueryText(query).get().getSearchId();
            updateSearchesFor(adsPagingRepository.findByTextIgnoreCaseContaining(query, Pageable.unpaged()), searchId);
        } else {
            updateSearchesFor(adsPagingRepository.findByTextIgnoreCaseContaining(query, Pageable.unpaged()), searchQuery.get().getSearchId());
        }

        updatePageCountFor(pagedResults);
        return pagedResults;
    }

    private void validateQuery(String query) {
        if (query.length() < 3)
            throw new InvalidRequestException(INVALID_QUERY);
    }

    private void validatePage(int page) {
        if (page < 0)
            throw new InvalidRequestException(INVALID_PAGE);
    }

    private void validateSize(int size) {
        if (size < 1)
            throw new InvalidRequestException(INVALID_SIZE);
    }

    private void updateSearchesFor(Page<Ad> ads, long searchId) {
        ads.getContent().forEach(ad ->
            statsService.updateSearchesFor(ad.getId(), searchId)
        );
    }

    private void updatePageCountFor(Page<Ad> ads) {
        ads.getContent().forEach(ad ->
            statsService.updatePageCountFor(ad.getId())
        );
    }
}
