package gr.xe.java.codechallenge.statistics.stats.infrastructure.repository;

import gr.xe.java.codechallenge.statistics.stats.infrastructure.entity.SearchStat;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SearchStatRepository extends CrudRepository<SearchStat, Long> {

    Optional<SearchStat> findByAdIdAndSearchId(String adId, Long searchId);

    int countByAdId(String adId);
}
