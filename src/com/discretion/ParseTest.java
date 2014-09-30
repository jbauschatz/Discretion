package com.discretion;

import com.discretion.parser.ProofParser;
import com.discretion.proof.Proof;

import java.io.File;

public class ParseTest {
    public static void main(String... args) {
        File input = new File("data/subset-is-associative.json");

        Proof proof = ProofParser.parseProof(input);

        ProofPrinter printer = new ProofPrinter(new PrettyPrinter());
        printer.prettyPrint(proof);
    }
}
