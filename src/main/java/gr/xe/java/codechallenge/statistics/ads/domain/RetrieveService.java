package gr.xe.java.codechallenge.statistics.ads.domain;

import gr.xe.java.codechallenge.statistics.ads.infrastructure.entity.Ad;

import java.util.Optional;

public interface RetrieveService {

    Optional<Ad> searchForSpecificAd(String AdId, Boolean isSearch);
}
