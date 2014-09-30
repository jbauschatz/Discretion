package com.discretion.parser;

import com.discretion.proof.Proof;
import com.discretion.solver.Problem;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProofParser {

    public static List<Problem> parseProblems(File file) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Problem> problems = objectMapper.readValue(file,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Problem.class));
            return problems;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(List<Problem> problems, File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, problems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(Proof proof, File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, proof);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Proof parseProof(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(file, Proof.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}