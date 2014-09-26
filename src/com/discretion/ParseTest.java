package com.discretion;

import com.discretion.parser.ProofXMLParser;
import com.discretion.proof.Proof;

import java.io.File;

public class ParseTest {
    public static void main(String... args) {
        File input = new File("data/subset-transitive.xml");
        Proof proof = ProofXMLParser.parse(input);

        ProofPrinter printer = new ProofPrinter(new PrettyPrinter());
        printer.prettyPrint(proof);
    }
}
