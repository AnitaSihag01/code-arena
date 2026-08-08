package com.example.CodeArena.controller;

import com.example.CodeArena.model.Submission;
import com.example.CodeArena.model.User;
import com.example.CodeArena.repository.ProblemRepository;
import com.example.CodeArena.repository.SubmissionRepository;
import com.example.CodeArena.repository.UserRepository;
import com.example.CodeArena.type.Verdict;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DashBoardController {

    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;
    private final ProblemRepository problemRepository;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Submission> allSubmissions = submissionRepository.findByUserIdOrderBySubmittedAtDesc(user.getId());

        // find how many different problems this user got ACCEPTED on
        Set<Long> solvedProblemIds = allSubmissions.stream()
                .filter(s -> s.getVerdict() == Verdict.ACCEPTED)
                .map(s -> s.getProblem().getId())
                .collect(Collectors.toSet());

        long easySolved = allSubmissions.stream()
                .filter(s -> s.getVerdict() == Verdict.ACCEPTED)
                .map(Submission::getProblem)
                .filter(p -> p.getDifficulty().name().equals("EASY"))
                .map(p -> p.getId())
                .distinct()
                .count();

        long mediumSolved = allSubmissions.stream()
                .filter(s -> s.getVerdict() == Verdict.ACCEPTED)
                .map(Submission::getProblem)
                .filter(p -> p.getDifficulty().name().equals("MEDIUM"))
                .map(p -> p.getId())
                .distinct()
                .count();

        long hardSolved = allSubmissions.stream()
                .filter(s -> s.getVerdict() == Verdict.ACCEPTED)
                .map(Submission::getProblem)
                .filter(p -> p.getDifficulty().name().equals("HARD"))
                .map(p -> p.getId())
                .distinct()
                .count();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("totalSolved", solvedProblemIds.size());
        model.addAttribute("totalProblems", problemRepository.count());
        model.addAttribute("easySolved", easySolved);
        model.addAttribute("mediumSolved", mediumSolved);
        model.addAttribute("hardSolved", hardSolved);
        model.addAttribute("recentSubmissions", allSubmissions.stream().limit(10).toList());

        return "dashboard";
    }
}