package com.discretion;

import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofItemVisitor;
import com.discretion.proof.ProofStatement;

public class ProofPrinter implements ProofItemVisitor {

    public void prettyPrint(Proof proof) {
        indentLevel = 0;
        System.out.println("Suppose " + printer.commaList(proof.suppositions) + ".");

        for (ProofItem item : proof.proof)
            item.accept(this);

        System.out.println("Therefore " + printer.prettyString(proof.conclusion) + ".");
    }

    public void visit(Proof proof) {
        // This is a sub proof nested at some level
        indent("Further suppose " + printer.commaList(proof.suppositions) + ".");

        ++indentLevel;

        // Print the body of the sub-proof
        for (ProofItem item : proof.proof)
            item.accept(this);

        // This puts the conclusion back on the parent indent level, which seems correct
        --indentLevel;

        // Print the conclusion of the sub-proof
        // This is a sub-proof so we use something less strong than "therefore"
        indent("So " + printer.prettyString(proof.conclusion) + ".");
    }

    public void visit(ProofStatement statement) {
        indent(printer.prettyString(statement.statement));
    }

    public ProofPrinter(PrettyPrinter printer) {
        this.printer = printer;
    }

    private void indent(String s) {
        for (int i = 0; i<indentLevel; ++i)
            s = "  " + s;
        System.out.println(s);
    }

    private PrettyPrinter printer;
    private int indentLevel;
}
