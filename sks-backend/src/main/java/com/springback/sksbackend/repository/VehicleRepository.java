package com.springback.sksbackend.repository;

import com.springback.sksbackend.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
}
