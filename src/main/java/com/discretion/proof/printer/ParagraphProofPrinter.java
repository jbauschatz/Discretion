package com.discretion.proof.printer;

import com.discretion.PrettyPrinter;
import com.discretion.proof.*;

import java.io.*;
import java.util.StringTokenizer;

public class ParagraphProofPrinter implements ProofItemVisitor, ProofPrettyPrinter {

	private static void printParagraph(String text, PrintStream stream, int width) {
		StringTokenizer tokens = new StringTokenizer(text, " ");
		int lineWidth = 0;
		boolean isNewLine = true;

		while (tokens.hasMoreTokens()) {
			String word = tokens.nextToken();
			int length = isNewLine ? word.length() : word.length() + 1;

			if (lineWidth + length > width) {
				isNewLine = true;
				stream.println();
				lineWidth = 0;
			}

			if (!isNewLine)
				stream.print(' ');

			stream.print(word);
			lineWidth += length;
			isNewLine = false;
		}
	}

	public void prettyPrint(Proof proof, PrintStream outputStream) {
		subProofsThisLevel = 0;

		// Title
		if (proof.getTitle() != null)
			outputStream.print(proof.getTitle());

		if (!proof.getSuppositions().isEmpty()) {
			outputStream.print(" If ");
			outputStream.print(printer.commaList(proof.getSuppositions()));
			outputStream.print(" then ");
			outputStream.print(printer.prettyString(proof.getConclusion().getStatement()));
			outputStream.print(".");
		}
		outputStream.println();
		outputStream.println();

		outputStream.print("Proof. ");
		// Suppositions
		if (!proof.getSuppositions().isEmpty()) {
			printParagraph("Suppose " + printer.commaList(proof.getSuppositions()) + ". ", outputStream, paragraphWidth);
			outputStream.println();
			outputStream.println();
		}

		outputBuilder = new StringBuilder();

		// Body of proof
		for (ProofItem item : proof.getProofItems())
			item.accept(this);

		// Conclusion
		outputBuilder.append("Therefore ");
		if (proof.getConclusion().getReason() != null) {
			outputBuilder.append(proof.getConclusion().getReason());
			outputBuilder.append(", ");
		}
		outputBuilder.append(printer.prettyString(proof.getConclusion().getStatement()));
		outputBuilder.append(", QED.");

		printParagraph(outputBuilder.toString(), outputStream, paragraphWidth);
		outputStream.append(System.lineSeparator());
	}

	public void visit(Proof proof) {
		// This is a sub proof nested at some level
		if (subProofsThisLevel > 0)
			outputBuilder.append("Now suppose " + printer.commaList(proof.getSuppositions()) + ". ");
		else
			outputBuilder.append("Further suppose " + printer.commaList(proof.getSuppositions()) + ". ");

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
			outputBuilder.append("So " + proof.getConclusion().getReason()
					+ ", " + printer.prettyString(proof.getConclusion().getStatement()) + ". ");
		} else {
			outputBuilder.append("So " + printer.prettyString(proof.getConclusion().getStatement()) + ". ");
		}
	}

	public void visit(ProofStatement statement) {
		if (statement.getReason() != null) {
			String reason = statement.getReason().substring(0, 1).toUpperCase() + statement.getReason().substring(1);
			outputBuilder.append(reason + ", " + printer.prettyString(statement.getStatement()) + ". ");
		} else {
			outputBuilder.append(printer.prettyString(statement.getStatement()) + " ");
		}
	}

	public void visit(UnknownSteps unknown) {
		outputBuilder.append("... ");
	}

	public ParagraphProofPrinter(PrettyPrinter printer) {
		this.printer = printer;

		paragraphWidth = 80;
	}

	public ParagraphProofPrinter() {
		this(new PrettyPrinter(true));
	}

	private int paragraphWidth;
	private PrettyPrinter printer;
	private StringBuilder outputBuilder;
	private int subProofsThisLevel;
}