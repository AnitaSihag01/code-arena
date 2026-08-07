package com.example.CodeArena.service;

import com.example.CodeArena.model.Problem;
import org.springframework.stereotype.Component;

@Component
public class MainClassBuilder {

    public String buildMainClass(Problem problem) {
        StringBuilder sb = new StringBuilder();
        sb.append("import java.util.*;\n\n");
        sb.append("public class Main {\n");
        sb.append("    public static void main(String[] args) throws Exception {\n");
        sb.append("        Scanner sc = new Scanner(System.in);\n");

        var paramTypes = problem.getParameterTypes();
        var paramNames = problem.getParameterNames();

        for (int i = 0; i < paramTypes.size(); i++) {
            sb.append(parseParam(paramTypes.get(i), paramNames.get(i)));
        }

        sb.append("        ").append(problem.getReturnType()).append(" result = ")
          .append("new Solution().").append(problem.getMethodName()).append("(")
          .append(String.join(", ", paramNames))
          .append(");\n");

        sb.append(printResult(problem.getReturnType()));

        sb.append("    }\n\n");
        sb.append("    private static int[] parseIntArray(String s) {\n");
        sb.append("        s = s.replaceAll(\"[\\\\[\\\\]\\\\s]\", \"\");\n");
        sb.append("        if (s.isEmpty()) return new int[0];\n");
        sb.append("        String[] parts = s.split(\",\");\n");
        sb.append("        int[] arr = new int[parts.length];\n");
        sb.append("        for (int i = 0; i < parts.length; i++) arr[i] = Integer.parseInt(parts[i]);\n");
        sb.append("        return arr;\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }

    private String parseParam(String type, String name) {
        return switch (type) {
            case "int" -> "        int " + name + " = Integer.parseInt(sc.nextLine().trim());\n";
            case "boolean" -> "        boolean " + name + " = Boolean.parseBoolean(sc.nextLine().trim());\n";
            case "String" -> "        String " + name + " = sc.nextLine().trim();\n";
            case "int[]" -> "        int[] " + name + " = parseIntArray(sc.nextLine().trim());\n";
            default -> throw new IllegalArgumentException("Unsupported parameter type: " + type);
        };
    }

    private String printResult(String returnType) {
        return switch (returnType) {
            case "int", "boolean", "String" -> "        System.out.println(result);\n";
            case "int[]" -> "        System.out.println(Arrays.toString(result));\n";
            default -> throw new IllegalArgumentException("Unsupported return type: " + returnType);
        };
    }
}