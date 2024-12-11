package com.helmes.assignment.entity.repositories;

import com.helmes.assignment.entity.models.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

}
