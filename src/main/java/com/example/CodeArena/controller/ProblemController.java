package com.example.CodeArena.controller;

import com.example.CodeArena.repository.ProblemRepository;
import com.example.CodeArena.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;

    @GetMapping("/problems")
    public String listProblems(Model model) {
        model.addAttribute("problems", problemRepository.findAll());
        return "problems";
    }

    @GetMapping("/problems/{id}")
    public String problemDetail(@PathVariable Long id, Model model) {
        var problem = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        model.addAttribute("problem", problem);
        model.addAttribute("sampleTestCases",
                testCaseRepository.findByProblemIdAndIsSample(id, true));

        return "problem-detail";
    }
}