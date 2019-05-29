package gr.xe.java.codechallenge.statistics.stats.domain;

public interface StatsService {

    void updateSearchesFor(String adId, long searchId);

    void updatePageCountFor(String adId);

    void updateClicksCountFor(String adId);
}
