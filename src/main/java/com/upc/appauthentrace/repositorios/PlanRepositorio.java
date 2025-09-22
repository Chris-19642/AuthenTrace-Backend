package com.upc.appauthentrace.repositorios;

import com.upc.appauthentrace.entidades.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepositorio extends JpaRepository<Plan, Long> {
}
