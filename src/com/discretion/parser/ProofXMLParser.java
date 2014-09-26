package com.discretion.parser;

import com.discretion.MathObject;
import com.discretion.Variable;
import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofStatement;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;
import com.discretion.statement.SubsetOf;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ProofXMLParser {

    public static Proof parse(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            return parseProof(document.getDocumentElement());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Proof parseProof(Element element) {
        List<Statement> suppositions = parseStatements((Element) element.getElementsByTagName("suppositions").item(0));
        List<ProofItem> items = parseItems((Element) element.getElementsByTagName("items").item(0));
        Statement conclusion = (Statement)parseObject((Element) element.getElementsByTagName("conclusion").item(0).getChildNodes().item(1));

        return new Proof(suppositions, items, conclusion);
    }

    private static List<ProofItem> parseItems(Element element) {
        LinkedList<ProofItem> items = new LinkedList<>();

        NodeList nodes = element.getChildNodes();
        for (int i = 0; i<nodes.getLength(); ++i) {
            Node child = nodes.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element)child;
                // TODO throw helpful exception if not a ProofItem
                items.add((ProofItem)parseProofItem(childElement));
            }
        }
        return items;
    }

    private static List<Statement> parseStatements(Element element) {
        LinkedList<Statement> statements = new LinkedList<>();

        NodeList nodes = element.getChildNodes();
        for (int i = 0; i<nodes.getLength(); ++i) {
            Node child = nodes.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element)child;
                // TODO throw helpful exception if not Statement
                statements.add((Statement)parseObject(childElement));
            }
        }

        return statements;
    }

    private static ProofItem parseProofItem(Element element) {
        if (element.getNodeName().equals("proof"))
            return parseProof(element);

        // Assume that this is a statement-item
        return new ProofStatement((Statement)parseObject(element));
    }

    private static MathObject parseObject(Element element) {
        //System.out.println("trying to parse an object from " + element.getNodeName());

        if (element.getNodeName().equals("variable"))
            return new Variable(element.getAttribute("name"));

        if (element.getNodeName().equals("subset-of"))
            return parseSubset(element);

        if (element.getNodeName().equals("element-of"))
            return parseElementOf(element);

        throw new RuntimeException("Cannot parse " + element.getNodeName());
    }

    private static SubsetOf parseSubset(Element element) {
        MathObject subset = parseObject((Element)element.getElementsByTagName("subset").item(0).getChildNodes().item(0));
        MathObject set = parseObject((Element)element.getElementsByTagName("set").item(0).getChildNodes().item(0));

        return new SubsetOf(subset, set);
    }

    private static ElementOf parseElementOf(Element element) {
        MathObject elem = parseObject((Element)element.getElementsByTagName("element").item(0).getChildNodes().item(0));
        MathObject set = parseObject((Element)element.getElementsByTagName("set").item(0).getChildNodes().item(0));

        return new ElementOf(elem, set);
    }

}