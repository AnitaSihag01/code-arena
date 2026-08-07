package com.example.CodeArena.config;

import com.example.CodeArena.model.Problem;
import com.example.CodeArena.model.TestCase;
import com.example.CodeArena.repository.ProblemRepository;
import com.example.CodeArena.repository.TestCaseRepository;
import com.example.CodeArena.type.Difficulty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;

    @Override
    public void run(String... args) {
        if (problemRepository.count() > 0) {
            return;
        }

        Problem twoSum = new Problem();
        twoSum.setTitle("Two Sum");
        twoSum.setDescription("Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.");
        twoSum.setExamples("Input: nums = [2,7,11,15], target = 9\nOutput: [0,1]");
        twoSum.setDifficulty(Difficulty.EASY);
        twoSum.setTags(List.of("Array", "Hash Table"));
        twoSum.setMethodName("twoSum");
        twoSum.setReturnType("int[]");
        twoSum.setParameterTypes(List.of("int[]", "int"));
        twoSum.setParameterNames(List.of("nums", "target"));
        twoSum.setStarterCode("class Solution {\n    public int[] twoSum(int[] nums, int target) {\n        \n    }\n}");

        problemRepository.save(twoSum);

        TestCase sample = new TestCase();
        sample.setProblem(twoSum);
        sample.setInput("[2,7,11,15]\n9");
        sample.setExpectedOutput("[0, 1]");
        sample.setSample(true);
        testCaseRepository.save(sample);

        TestCase hidden = new TestCase();
        hidden.setProblem(twoSum);
        hidden.setInput("[3,2,4]\n6");
        hidden.setExpectedOutput("[1, 2]");
        hidden.setSample(false);
        testCaseRepository.save(hidden);
    }
}