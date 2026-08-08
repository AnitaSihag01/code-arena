package com.example.CodeArena.controller;

import com.example.CodeArena.model.Problem;
import com.example.CodeArena.model.Submission;
import com.example.CodeArena.model.User;
import com.example.CodeArena.repository.ProblemRepository;
import com.example.CodeArena.repository.SubmissionRepository;
import com.example.CodeArena.repository.UserRepository;
import com.example.CodeArena.service.CodeChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/judge")
@RequiredArgsConstructor
public class RunController {

    private final ProblemRepository problemRepository;
    private final CodeChecker codeChecker;
    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;

    @PostMapping("/run")
    public Map<String, Object> run(@RequestBody SubmitRequest request) {
        return execute(request, true, false);
    }

    @PostMapping("/submit")
    public Map<String, Object> submit(@RequestBody SubmitRequest request, Authentication auth) {
        return execute(request, false, true, auth);
    }

    private Map<String, Object> execute(SubmitRequest request, boolean sampleOnly, boolean saveIt) {
        return execute(request, sampleOnly, saveIt, null);
    }

    private Map<String, Object> execute(SubmitRequest request, boolean sampleOnly, boolean saveIt, Authentication auth) {
        Problem problem = problemRepository.findById(request.problemId())
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        CodeChecker.CheckResult result = codeChecker.check(problem, request.code(), sampleOnly);

        if (saveIt && auth != null) {
            User user = userRepository.findByUsername(auth.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Submission sub = new Submission();
            sub.setUser(user);
            sub.setProblem(problem);
            sub.setCode(request.code());
            sub.setVerdict(result.verdict());
            sub.setErrorMessage(result.message());

            submissionRepository.save(sub);
        }

        return Map.of(
                "verdict", result.verdict().name(),
                "message", result.message()
        );
    }

    public record SubmitRequest(Long problemId, String code) {}
}