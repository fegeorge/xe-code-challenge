package gr.xe.java.codechallenge.statistics.ads.domain;

import gr.xe.java.codechallenge.statistics.ads.infrastructure.Ad;
import org.springframework.data.domain.Page;

public interface SearchService {

    Page<Ad> searchForAds(String query, int page, int size);
}
