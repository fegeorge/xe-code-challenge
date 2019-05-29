package gr.xe.java.codechallenge.statistics.ads.domain;

import gr.xe.java.codechallenge.statistics.ads.infrastructure.Ad;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.AdsRepository;
import gr.xe.java.codechallenge.statistics.stats.domain.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RetrieveAdService implements RetrieveService {

    private AdsRepository adsRepository;
    private StatsService statsService;

    @Autowired
    public RetrieveAdService(AdsRepository adsRepository, StatsService statsService) {
        this.adsRepository = adsRepository;
        this.statsService = statsService;
    }

    @Override
    public Optional<Ad> searchForSpecificAd(String AdId, Boolean isSearch) {
        Optional<Ad> result = adsRepository.findById(AdId);
        if (result.isPresent() && isSearch) {
            updateClicksCountFor(result.get().getId());
        }
        return result;
    }

    private void updateClicksCountFor(String AdId) {
        statsService.updateClicksCountFor(AdId);
    }
}
