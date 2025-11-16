package com.upc.appauthentrace.service;

import com.upc.appauthentrace.entidades.Plan;
import com.upc.appauthentrace.repositorios.PlanRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServicio {

    @Autowired
    private PlanRepositorio planRepositorio;

    public List<Plan> obtenerPlanes(){
        return planRepositorio.findAll();
    }
    public Plan guardarPlan(Plan plan){
        return planRepositorio.save(plan);
    }
}
