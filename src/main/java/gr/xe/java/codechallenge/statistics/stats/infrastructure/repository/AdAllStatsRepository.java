package gr.xe.java.codechallenge.statistics.stats.infrastructure.repository;

import gr.xe.java.codechallenge.statistics.stats.infrastructure.entity.AdAllStats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdAllStatsRepository extends CrudRepository<AdAllStats, Long> {

    Optional<AdAllStats> findByAdId(String adId);
}
