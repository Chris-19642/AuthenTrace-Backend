package com.upc.appauthentrace.controllers;

import com.upc.appauthentrace.entidades.Plan;
import com.upc.appauthentrace.service.PlanServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    @Autowired
    private PlanServicio planServicio;

    @GetMapping
    public List<Plan> obtenerPlanes(){
        return planServicio.obtenerPlanes();
    }

    @PostMapping
    public Plan crearPlan(@RequestBody Plan plan){
        return planServicio.guardarPlan(plan);
    }
}
