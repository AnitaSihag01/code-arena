package com.example.CodeArena.service;

import com.example.CodeArena.model.Problem;
import com.example.CodeArena.model.TestCase;
import com.example.CodeArena.repository.TestCaseRepository;
import com.example.CodeArena.type.Verdict;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeChecker {

    private final MainClassBuilder mainClassBuilder;
    private final CodeRunner codeRunner;
    private final TestCaseRepository testCaseRepository;

    public CheckResult check(Problem problem, String userCode, boolean sampleOnly) {
        String mainCode = mainClassBuilder.buildMainClass(problem);

        List<TestCase> testCases = sampleOnly
                ? testCaseRepository.findByProblemIdAndIsSample(problem.getId(), true)
                : testCaseRepository.findByProblemId(problem.getId());

        for (TestCase tc : testCases) {
            CodeRunner.RunResult result = codeRunner.run(userCode, mainCode, tc.getInput());

            if (result.timedOut()) {
                return new CheckResult(Verdict.TIME_LIMIT_EXCEEDED, "", tc.getInput());
            }

            if (result.exitCode() != 0 && !result.stderr().isBlank() && result.stdout().isBlank()) {
                Verdict verdict = result.stderr().contains("error:")
                        ? Verdict.COMPILATION_ERROR
                        : Verdict.RUNTIME_ERROR;
                return new CheckResult(verdict, result.stderr(), tc.getInput());
            }

            String actual = result.stdout().trim();
            String expected = tc.getExpectedOutput().trim();

            if (!actual.equals(expected)) {
                return new CheckResult(Verdict.WRONG_ANSWER,
                        "Expected: " + expected + "\nGot: " + actual, tc.getInput());
            }
        }

        return new CheckResult(Verdict.ACCEPTED, "All test cases passed", null);
    }

    public record CheckResult(Verdict verdict, String message, String failedInput) {}
}