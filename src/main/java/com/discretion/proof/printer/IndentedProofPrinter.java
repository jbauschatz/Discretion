package com.discretion.proof.printer;

import com.discretion.PrettyPrinter;
import com.discretion.proof.*;

import java.io.*;

public class IndentedProofPrinter implements ProofItemVisitor, ProofPrettyPrinter {

    public void prettyPrint(Proof proof, PrintStream outputStream) {
        this.outputStream = outputStream;
        indentLevel = 0;
        subProofsThisLevel = 0;

		if (proof.getTitle() != null)
			indent(proof.getTitle());

        if (!proof.getSuppositions().isEmpty())
			indent("Suppose " + printer.commaList(proof.getSuppositions()) + ".");

        for (ProofItem item : proof.getProofItems())
			item.accept(this);

        String conclusion = "Therefore " + printer.prettyString(proof.getConclusion().getStatement());
        if (proof.getConclusion().getReason() != null)
			conclusion += " " + proof.getConclusion().getReason();
        conclusion += ", QED.";

        indent(conclusion);
    }

    public void visit(Proof proof) {
        // This is a sub proof nested at some level
        if (subProofsThisLevel > 0)
            indent("Now suppose " + printer.commaList(proof.getSuppositions()) + ".");
        else
            indent("Further suppose " + printer.commaList(proof.getSuppositions()) + ".");

        // After printing the suppositions, indent the body of the proof
        ++indentLevel;
        ++subProofsThisLevel;

        // Save the sub-proofs at this level before going deeper
        int oldSubProofs = subProofsThisLevel;
        subProofsThisLevel = 0;

        // Print the body of the sub-proof
        for (ProofItem item : proof.getProofItems())
            item.accept(this);

        subProofsThisLevel = oldSubProofs;

        // This puts the conclusion back on the parent indent level, which seems correct
        --indentLevel;

        // Print the conclusion of the sub-proof
        // This is a sub-proof so we use something less strong than "therefore"
        if (proof.getConclusion().getReason() != null) {
            indent("So " + printer.prettyString(proof.getConclusion().getStatement())
                    + ", " + proof.getConclusion().getReason() + ".");
        } else {
            indent("So " + printer.prettyString(proof.getConclusion().getStatement()) + ".");
        }
    }

    public void visit(ProofStatement statement) {
        if (statement.getReason() != null)
            indent(printer.prettyString(statement.getStatement()) + " " + statement.getReason() + ".");
        else
            indent(printer.prettyString(statement.getStatement()));
    }

    public void visit(UnknownSteps unknown) {
        indent("???");
    }

    public IndentedProofPrinter(PrettyPrinter printer) {
        this.printer = printer;
    }

	public IndentedProofPrinter() {
		this(new PrettyPrinter());
	}

    private void indent(String s) {
        for (int i = 0; i<indentLevel; ++i)
            s = "  " + s;
        outputStream.println(s);
    }

    private PrettyPrinter printer;
    private PrintStream outputStream;
    private int indentLevel;
    private int subProofsThisLevel;
}
