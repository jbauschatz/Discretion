
package com.discretion.proof.printer;

import com.discretion.proof.Proof;

import java.io.PrintStream;

/**
 *
 */
public interface ProofPrettyPrinter {

	void prettyPrint(Proof proof, PrintStream outputStream);

	String getName();
}
