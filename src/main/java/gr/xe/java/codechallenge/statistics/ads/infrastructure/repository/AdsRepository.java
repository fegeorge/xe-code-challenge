package gr.xe.java.codechallenge.statistics.ads.infrastructure.repository;

import gr.xe.java.codechallenge.statistics.ads.infrastructure.entity.Ad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdsRepository extends CrudRepository<Ad, Long> {
    Optional<Ad> findById(String id);
}
