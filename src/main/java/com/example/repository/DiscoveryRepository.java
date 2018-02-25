package com.example.repository;

import com.example.model.Discovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscoveryRepository extends JpaRepository<Discovery, Long>{
}
