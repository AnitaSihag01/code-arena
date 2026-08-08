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
        if (problemRepository.findAll().stream().noneMatch(p -> p.getTitle().equals("Two Sum"))) {
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

        if (problemRepository.findAll().stream().noneMatch(p -> p.getTitle().equals("Reverse String"))) {
            Problem reverseString = new Problem();
            reverseString.setTitle("Reverse String");
            reverseString.setDescription("Given a string s, return the string reversed.");
            reverseString.setExamples("Input: s = \"hello\"\nOutput: olleh");
            reverseString.setDifficulty(Difficulty.EASY);
            reverseString.setTags(List.of("String"));
            reverseString.setMethodName("reverseString");
            reverseString.setReturnType("String");
            reverseString.setParameterTypes(List.of("String"));
            reverseString.setParameterNames(List.of("s"));
            reverseString.setStarterCode("class Solution {\n    public String reverseString(String s) {\n        \n    }\n}");

            problemRepository.save(reverseString);

            TestCase reverseSample = new TestCase();
            reverseSample.setProblem(reverseString);
            reverseSample.setInput("hello");
            reverseSample.setExpectedOutput("olleh");
            reverseSample.setSample(true);
            testCaseRepository.save(reverseSample);

            TestCase reverseHidden = new TestCase();
            reverseHidden.setProblem(reverseString);
            reverseHidden.setInput("java");
            reverseHidden.setExpectedOutput("avaj");
            reverseHidden.setSample(false);
            testCaseRepository.save(reverseHidden);
        }
        if (problemRepository.findAll().stream().noneMatch(p -> p.getTitle().equals("Contains Duplicate"))) {
            Problem containsDup = new Problem();
            containsDup.setTitle("Contains Duplicate");
            containsDup.setDescription("Given an array of integers nums, return true if any value appears at least twice in the array, and false if every element is distinct.");
            containsDup.setExamples("Input: nums = [1,2,3,1]\nOutput: true");
            containsDup.setDifficulty(Difficulty.EASY);
            containsDup.setTags(List.of("Array", "Hash Table"));
            containsDup.setMethodName("containsDuplicate");
            containsDup.setReturnType("boolean");
            containsDup.setParameterTypes(List.of("int[]"));
            containsDup.setParameterNames(List.of("nums"));
            containsDup.setStarterCode("class Solution {\n    public boolean containsDuplicate(int[] nums) {\n        \n    }\n}");

            problemRepository.save(containsDup);

            TestCase dupSample = new TestCase();
            dupSample.setProblem(containsDup);
            dupSample.setInput("[1,2,3,1]");
            dupSample.setExpectedOutput("true");
            dupSample.setSample(true);
            testCaseRepository.save(dupSample);

            TestCase dupHidden = new TestCase();
            dupHidden.setProblem(containsDup);
            dupHidden.setInput("[1,2,3,4]");
            dupHidden.setExpectedOutput("false");
            dupHidden.setSample(false);
            testCaseRepository.save(dupHidden);
        }

        if (problemRepository.findAll().stream().noneMatch(p -> p.getTitle().equals("Max In Array"))) {
            Problem maxArray = new Problem();
            maxArray.setTitle("Max In Array");
            maxArray.setDescription("Given an array of integers nums, return the largest value in the array.");
            maxArray.setExamples("Input: nums = [3,7,2,9,4]\nOutput: 9");
            maxArray.setDifficulty(Difficulty.EASY);
            maxArray.setTags(List.of("Array"));
            maxArray.setMethodName("maxInArray");
            maxArray.setReturnType("int");
            maxArray.setParameterTypes(List.of("int[]"));
            maxArray.setParameterNames(List.of("nums"));
            maxArray.setStarterCode("class Solution {\n    public int maxInArray(int[] nums) {\n        \n    }\n}");

            problemRepository.save(maxArray);

            TestCase maxSample = new TestCase();
            maxSample.setProblem(maxArray);
            maxSample.setInput("[3,7,2,9,4]");
            maxSample.setExpectedOutput("9");
            maxSample.setSample(true);
            testCaseRepository.save(maxSample);

            TestCase maxHidden = new TestCase();
            maxHidden.setProblem(maxArray);
            maxHidden.setInput("[-5,-2,-9,-1]");
            maxHidden.setExpectedOutput("-1");
            maxHidden.setSample(false);
            testCaseRepository.save(maxHidden);
        }

        if (problemRepository.findAll().stream().noneMatch(p -> p.getTitle().equals("Third Largest"))) {
            Problem thirdLargest = new Problem();
            thirdLargest.setTitle("Third Largest");
            thirdLargest.setDescription("Given an array of distinct integers nums, return the third largest value in the array, without sorting the array.");
            thirdLargest.setExamples("Input: nums = [5,2,8,1,9]\nOutput: 5");
            thirdLargest.setDifficulty(Difficulty.MEDIUM);
            thirdLargest.setTags(List.of("Array"));
            thirdLargest.setMethodName("thirdLargest");
            thirdLargest.setReturnType("int");
            thirdLargest.setParameterTypes(List.of("int[]"));
            thirdLargest.setParameterNames(List.of("nums"));
            thirdLargest.setStarterCode("class Solution {\n    public int thirdLargest(int[] nums) {\n        \n    }\n}");

            problemRepository.save(thirdLargest);

            TestCase thirdSample = new TestCase();
            thirdSample.setProblem(thirdLargest);
            thirdSample.setInput("[5,2,8,1,9]");
            thirdSample.setExpectedOutput("5");
            thirdSample.setSample(true);
            testCaseRepository.save(thirdSample);

            TestCase thirdHidden = new TestCase();
            thirdHidden.setProblem(thirdLargest);
            thirdHidden.setInput("[10,20,30,40,50]");
            thirdHidden.setExpectedOutput("30");
            thirdHidden.setSample(false);
            testCaseRepository.save(thirdHidden);
        }
        if (problemRepository.findAll().stream().noneMatch(p -> p.getTitle().equals("Fibonacci Number"))) {
            Problem fib = new Problem();
            fib.setTitle("Fibonacci Number");
            fib.setDescription("Given an integer n, return the nth Fibonacci number. F(0) = 0, F(1) = 1, F(n) = F(n-1) + F(n-2) for n > 1.");
            fib.setExamples("Input: n = 4\nOutput: 3");
            fib.setDifficulty(Difficulty.EASY);
            fib.setTags(List.of("Recursion", "Math"));
            fib.setMethodName("fib");
            fib.setReturnType("int");
            fib.setParameterTypes(List.of("int"));
            fib.setParameterNames(List.of("n"));
            fib.setStarterCode("class Solution {\n    public int fib(int n) {\n        \n    }\n}");

            problemRepository.save(fib);

            TestCase fibSample = new TestCase();
            fibSample.setProblem(fib);
            fibSample.setInput("4");
            fibSample.setExpectedOutput("3");
            fibSample.setSample(true);
            testCaseRepository.save(fibSample);

            TestCase fibHidden = new TestCase();
            fibHidden.setProblem(fib);
            fibHidden.setInput("7");
            fibHidden.setExpectedOutput("13");
            fibHidden.setSample(false);
            testCaseRepository.save(fibHidden);
        }

        if (problemRepository.findAll().stream().noneMatch(p -> p.getTitle().equals("Valid Parentheses"))) {
            Problem validParens = new Problem();
            validParens.setTitle("Valid Parentheses");
            validParens.setDescription("Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid. Brackets must close in the correct order.");
            validParens.setExamples("Input: s = \"()[]{}\"\nOutput: true");
            validParens.setDifficulty(Difficulty.EASY);
            validParens.setTags(List.of("Stack", "String"));
            validParens.setMethodName("isValid");
            validParens.setReturnType("boolean");
            validParens.setParameterTypes(List.of("String"));
            validParens.setParameterNames(List.of("s"));
            validParens.setStarterCode("class Solution {\n    public boolean isValid(String s) {\n        \n    }\n}");

            problemRepository.save(validParens);

            TestCase parensSample = new TestCase();
            parensSample.setProblem(validParens);
            parensSample.setInput("()[]{}");
            parensSample.setExpectedOutput("true");
            parensSample.setSample(true);
            testCaseRepository.save(parensSample);

            TestCase parensHidden = new TestCase();
            parensHidden.setProblem(validParens);
            parensHidden.setInput("(]");
            parensHidden.setExpectedOutput("false");
            parensHidden.setSample(false);
            testCaseRepository.save(parensHidden);
        }
    }
}