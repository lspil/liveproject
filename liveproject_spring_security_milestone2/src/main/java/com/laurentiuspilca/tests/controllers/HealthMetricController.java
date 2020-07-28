package com.laurentiuspilca.tests.controllers;

import com.laurentiuspilca.tests.entities.HealthMetric;
import com.laurentiuspilca.tests.services.HealthMetricService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metric")
public class HealthMetricController {

  private final HealthMetricService healthMetricService;

  public HealthMetricController(HealthMetricService healthMetricService) {
    this.healthMetricService = healthMetricService;
  }

  @PostMapping
  public void addHealthMetric(@RequestBody HealthMetric healthMetric) {
    healthMetricService.addHealthMetric(healthMetric);
  }

  @GetMapping
  public List<HealthMetric> findHealthMetrics() {
    return healthMetricService.findHealthMetricHistory();
  }

  @DeleteMapping("/{username}")
  public void deleteHealthMetricForUser(@PathVariable String username) {
    healthMetricService.deleteHealthMetricForUser(username);
  }
}
