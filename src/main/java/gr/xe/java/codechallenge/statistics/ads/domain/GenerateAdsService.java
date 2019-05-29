package gr.xe.java.codechallenge.statistics.ads.domain;

import gr.xe.java.codechallenge.statistics.ads.infrastructure.AdsRepository;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateAdsService {

    @Autowired
    private AdsRepository adsRepository;

    public Ad insertAd(Ad adv) {
        return adsRepository.save(adv);
    }


}
