package com.discretion.proof.printer;

import com.discretion.PrettyPrinter;
import com.discretion.proof.*;

import java.io.*;

public class ParagraphProofPrinter implements ProofItemVisitor, ProofPrettyPrinter {

	public String getName() {
		return name;
	}

	public void prettyPrint(Proof proof, PrintStream outputStream) {
		this.outputStream = outputStream;
		subProofsThisLevel = 0;

		if (proof.getTitle() != null)
			outputStream.println(proof.getTitle());

		if (!proof.getSuppositions().isEmpty())
			outputStream.print("Suppose " + printer.commaList(proof.getSuppositions()) + ". ");

		for (ProofItem item : proof.getProofItems())
			item.accept(this);

		String conclusion = "Therefore ";
		if (proof.getConclusion().getReason() != null) {
			conclusion += proof.getConclusion().getReason() + ", ";
		}
		conclusion += printer.prettyString(proof.getConclusion().getStatement()) + ", QED.";

		outputStream.println(conclusion);
	}

	public void visit(Proof proof) {
		// This is a sub proof nested at some level
		if (subProofsThisLevel > 0)
			outputStream.print("Now suppose " + printer.commaList(proof.getSuppositions()) + ". ");
		else
			outputStream.print("Further suppose " + printer.commaList(proof.getSuppositions()) + ". ");

		++subProofsThisLevel;

		// Save the sub-proofs at this level before going deeper
		int oldSubProofs = subProofsThisLevel;
		subProofsThisLevel = 0;

		// Print the body of the sub-proof
		for (ProofItem item : proof.getProofItems())
			item.accept(this);

		subProofsThisLevel = oldSubProofs;

		// Print the conclusion of the sub-proof
		// This is a sub-proof so we use something less strong than "therefore"
		if (proof.getConclusion().getReason() != null) {
			outputStream.print("So " + proof.getConclusion().getReason()
					+ ", " + printer.prettyString(proof.getConclusion().getStatement()) + ". ");
		} else {
			outputStream.print("So " + printer.prettyString(proof.getConclusion().getStatement()) + ". ");
		}
	}

	public void visit(ProofStatement statement) {
		if (statement.getReason() != null) {
			String reason = statement.getReason().substring(0, 1).toUpperCase() + statement.getReason().substring(1);
			outputStream.print(reason + ", " + printer.prettyString(statement.getStatement()) + ". ");
		} else {
			outputStream.print(printer.prettyString(statement.getStatement()) + " ");
		}
	}

	public void visit(UnknownSteps unknown) {
		outputStream.print("... ");
	}

	public ParagraphProofPrinter(String name, PrettyPrinter printer) {
		this.name = name;
		this.printer = printer;
	}

	public ParagraphProofPrinter(String name) {
		this(name, new PrettyPrinter(true));
	}

	private PrettyPrinter printer;
	private PrintStream outputStream;
	private int subProofsThisLevel;
	private String name;
}