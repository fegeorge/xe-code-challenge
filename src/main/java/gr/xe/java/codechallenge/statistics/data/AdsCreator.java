package gr.xe.java.codechallenge.statistics.data;

import gr.xe.java.codechallenge.statistics.Utils.Search;
import gr.xe.java.codechallenge.statistics.ads.domain.GenerateAdsService;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class AdsCreator {

    @Autowired
    private GenerateAdsService generateAdsService;

    /**
     * Create ad data.
     *
     * @return the created ads
     */
    @PostConstruct
    public List<Ad> createAd() {
        List<Ad> ads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Search search = new Search();
            Ad[] searchResults = search.getSearchResults();
            ads = Arrays.asList(searchResults);
            List<String> regions = new ArrayList<>();
            regions.add("Koukaki");
            regions.add("Marousi");
            regions.add("Kentro");
            Random rand = new Random();
            for (Ad adv : ads) {
                String randomElement = regions.get(rand.nextInt(regions.size()));
                adv.setText(adv.getText() + " for " + randomElement);
                generateAdsService.insertAd(adv);
            }
        }
        return ads;
    }
}
