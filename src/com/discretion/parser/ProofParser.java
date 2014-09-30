package com.discretion.parser;

import com.discretion.proof.Proof;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ProofParser {

    public static void save(Proof proof, File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, proof);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Proof parse(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(file, Proof.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}