package jku.ce.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findById(Long id);
    Optional<Region> findByName(String name);

    Optional<Region> findByNameAndFederalState(String defaultRegion, FederalState federalState);
}

