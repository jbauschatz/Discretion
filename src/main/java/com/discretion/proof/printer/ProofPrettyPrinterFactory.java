package com.discretion.proof.printer;

/**
 *
 */
public class ProofPrettyPrinterFactory {

	public static ProofPrettyPrinter getPrinter(String name) {
		switch (name.toLowerCase()) {
			case "paragraph":
			case "para":
				return new ParagraphProofPrinter();
			case "indented":
				return new IndentedProofPrinter();
			default:
				throw new IllegalArgumentException("Could not find a Proof Printer named: " + name);
		}
	}
}
