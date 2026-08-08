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
            String fullSolutionCode = "import java.util.*;\n\n" + solutionCode;
            writeFile(tempDir, "Solution.java", fullSolutionCode);  writeFile(tempDir, "Main.java", mainCode);

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


        StringBuilder stdoutBuilder = new StringBuilder();
        StringBuilder stderrBuilder = new StringBuilder();

        Thread outThread = new Thread(() -> {
            try {
                stdoutBuilder.append(new String(process.getInputStream().readAllBytes()));
            } catch (Exception ignored) {}
        });
        Thread errThread = new Thread(() -> {
            try {
                stderrBuilder.append(new String(process.getErrorStream().readAllBytes()));
            } catch (Exception ignored) {}
        });

        outThread.start();
        errThread.start();

        boolean finished = process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS);

        if (!finished) {
            process.destroyForcibly();
            return new RunResult(-1, "", "Time limit exceeded", true);
        }


        outThread.join(1000);
        errThread.join(1000);

        return new RunResult(process.exitValue(), stdoutBuilder.toString(), stderrBuilder.toString(), false);
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