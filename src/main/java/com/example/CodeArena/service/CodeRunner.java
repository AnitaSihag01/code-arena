package com.example.CodeArena.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class CodeRunner {

    private static final int TIMEOUT_SECONDS = 5;

    public RunResult run(String solutionCode, String mainCode, String input) {
        File tempDir = null;
        try {
            tempDir = Files.createTempDirectory("submission-" + UUID.randomUUID()).toFile();

            writeFile(tempDir, "Solution.java", solutionCode);
            writeFile(tempDir, "Main.java", mainCode);

            RunResult compileResult = runInContainer(tempDir, "javac Solution.java Main.java", null);
            if (compileResult.exitCode() != 0) {
                return new RunResult(compileResult.exitCode(), "", compileResult.stderr(), false);
            }

            return runInContainer(tempDir, "java Main", input);

        } catch (Exception e) {
            return new RunResult(-1, "", "Internal error: " + e.getMessage(), false);
        } finally {
            if (tempDir != null) {
                deleteDirectory(tempDir);
            }
        }
    }

    private RunResult runInContainer(File tempDir, String command, String input) throws Exception {
        List<String> dockerCommand = List.of(
                "docker", "run", "--rm", "-i",
                "--network=none",
                "--memory=128m",
                "--cpus=0.5",
                "-v", tempDir.getAbsolutePath() + ":/code",
                "-w", "/code",
                "eclipse-temurin:21-jdk-alpine",
                "sh", "-c", command
        );

        ProcessBuilder pb = new ProcessBuilder(dockerCommand);
        Process process = pb.start();

        if (input != null) {
            process.getOutputStream().write(input.getBytes());
            process.getOutputStream().flush();
            process.getOutputStream().close();
        }

        String stdout = new String(process.getInputStream().readAllBytes());
        String stderr = new String(process.getErrorStream().readAllBytes());

        boolean finished = process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            return new RunResult(-1, stdout, "Time limit exceeded", true);
        }

        return new RunResult(process.exitValue(), stdout, stderr, false);
    }

    private void writeFile(File dir, String filename, String content) throws Exception {
        try (FileWriter writer = new FileWriter(new File(dir, filename))) {
            writer.write(content);
        }
    }

    private void deleteDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
        }
        dir.delete();
    }

    public record RunResult(int exitCode, String stdout, String stderr, boolean timedOut) {}
}