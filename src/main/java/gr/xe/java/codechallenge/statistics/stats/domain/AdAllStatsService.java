package gr.xe.java.codechallenge.statistics.stats.domain;

import gr.xe.java.codechallenge.statistics.stats.infrastructure.entity.AdAllStats;
import gr.xe.java.codechallenge.statistics.stats.infrastructure.repository.AdAllStatsRepository;
import gr.xe.java.codechallenge.statistics.stats.infrastructure.entity.SearchStat;
import gr.xe.java.codechallenge.statistics.stats.infrastructure.repository.SearchStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdAllStatsService implements StatsService {

    private AdAllStatsRepository adAllStatsRepository;
    private SearchStatRepository searchStatRepository;

    @Autowired
    public AdAllStatsService(AdAllStatsRepository adAllStatsRepository, SearchStatRepository searchStatRepository) {
        this.adAllStatsRepository = adAllStatsRepository;
        this.searchStatRepository = searchStatRepository;
    }

    @Override
    public void updateSearchesFor(String adId, long searchId) {
        Optional<SearchStat> searchStat = searchStatRepository.findByAdIdAndSearchId(adId, searchId);

        if (!searchStat.isPresent())
            searchStatRepository.save(new SearchStat(adId, searchId));

        int searchCount = searchStatRepository.countByAdId(adId);
        Optional<AdAllStats> existingAdStat = adAllStatsRepository.findByAdId(adId);

        if (existingAdStat.isPresent()) {
            AdAllStats updatedStat = new AdAllStats(
                existingAdStat.get().getId(),
                adId,
                existingAdStat.get().getPageCount(),
                existingAdStat.get().getClicksCount(),
                searchCount
            );
            adAllStatsRepository.save(updatedStat);
        } else {
            AdAllStats stat = new AdAllStats(adId, 0, 0, searchCount);
            adAllStatsRepository.save(stat);
        }
    }

    @Override
    public void updatePageCountFor(String adId) {
        Optional<AdAllStats> existingAdStat = adAllStatsRepository.findByAdId(adId);

        if (existingAdStat.isPresent()) {
            existingAdStat.get().setPageCount(existingAdStat.get().getPageCount() + 1);
            adAllStatsRepository.save(existingAdStat.get());
        } else {
            AdAllStats stat = new AdAllStats(adId, 1, 0);
            adAllStatsRepository.save(stat);
        }
    }

    @Override
    public void updateClicksCountFor(String adId) {
        Optional<AdAllStats> existingAdStat = adAllStatsRepository.findByAdId(adId);
        if (existingAdStat.isPresent()) {
            existingAdStat.get().setClicksCount(existingAdStat.get().getClicksCount() + 1);
            adAllStatsRepository.save(existingAdStat.get());
        } else {
            AdAllStats updatedStat = new AdAllStats(adId, 1);
            adAllStatsRepository.save(updatedStat);
        }
    }
}
