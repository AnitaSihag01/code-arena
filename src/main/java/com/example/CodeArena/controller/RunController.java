package com.example.CodeArena.controller;

import com.example.CodeArena.model.Problem;
import com.example.CodeArena.repository.ProblemRepository;
import com.example.CodeArena.service.CodeChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/judge")
@RequiredArgsConstructor
public class RunController {

    private final ProblemRepository problemRepository;
    private final CodeChecker codeChecker;

    @PostMapping("/run")
    public Map<String, Object> run(@RequestBody SubmitRequest request) {
        return execute(request, true);
    }

    @PostMapping("/submit")
    public Map<String, Object> submit(@RequestBody SubmitRequest request) {
        return execute(request, false);
    }

    private Map<String, Object> execute(SubmitRequest request, boolean sampleOnly) {
        Problem problem = problemRepository.findById(request.problemId())
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        CodeChecker.CheckResult result = codeChecker.check(problem, request.code(), sampleOnly);

        return Map.of(
                "verdict", result.verdict().name(),
                "message", result.message()
        );
    }

    public record SubmitRequest(Long problemId, String code) {}
}