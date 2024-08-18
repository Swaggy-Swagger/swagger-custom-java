package com.test.swaggercustom.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Plan API for Test")
@RestController
@RequestMapping(value = "/plan")
public class TestController {

    @Operation(summary = "추천 일정 가져오기")
    @GetMapping("/recommended")
    public void getRecommendedPlan() {
    }

    @Operation(summary = "일정 추가하기")
    @PostMapping
    public void createPlan(@RequestBody TestDto testDto) {
    }

    @Operation(summary = "일정 전체 가져오기")
    @GetMapping
    public void getAllPlans() {
    }

    @Operation(summary = "일정 가져오기")
    @GetMapping("/{id}")
    public void getPlan(@PathVariable Long id) {
    }
}
