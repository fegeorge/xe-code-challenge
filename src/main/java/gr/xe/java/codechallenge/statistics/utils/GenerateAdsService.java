package gr.xe.java.codechallenge.statistics.utils;

import gr.xe.java.codechallenge.statistics.ads.infrastructure.repository.AdsRepository;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.entity.Ad;
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
