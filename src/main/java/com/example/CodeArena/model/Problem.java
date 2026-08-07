package com.example.CodeArena.model;

import com.example.CodeArena.type.Difficulty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "problems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String examples;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ElementCollection
    @CollectionTable(name = "problem_tags", joinColumns = @JoinColumn(name = "problem_id"))
    @Column(name = "tag")
    private List<String> tags;

    @Column(nullable = false)
    private String methodName;

    @Column(nullable = false)
    private String returnType;

    @ElementCollection
    @CollectionTable(name = "problem_param_types", joinColumns = @JoinColumn(name = "problem_id"))
    @Column(name = "param_type")
    private List<String> parameterTypes;

    @ElementCollection
    @CollectionTable(name = "problem_param_names", joinColumns = @JoinColumn(name = "problem_id"))
    @Column(name = "param_name")
    private List<String> parameterNames;

    @Column(columnDefinition = "TEXT")
    private String starterCode;
}