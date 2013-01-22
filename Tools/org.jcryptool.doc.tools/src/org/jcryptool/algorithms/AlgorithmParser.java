package org.jcryptool.algorithms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AlgorithmParser {

	public static void main(String[] args) throws XPathExpressionException, SAXException, IOException, ParseException, ParserConfigurationException{
			CommandLineParser cmdParser = new PosixParser();
			Options options = new Options();
			options.addOption("help", false, "prints this message");
			options.addOption("in", true, "path to algorithms.xml");
			options.addOption("out", true, "path to algorithms.txt");
			CommandLine cmd = cmdParser.parse(options, args);
			
			if(cmd.hasOption("help"))
			{
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("AlgorithmXmlParser extracts algorithm names from algorithms.xml file to algorithms.txt", options );
				return;
			}
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			
			builder = factory.newDocumentBuilder();
			
			Document doc = builder.parse(cmd.getOptionValue("in"));
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile("//Names");
			NodeList result = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			SortedSet<String> algorithmNames = new TreeSet<String>();
			for(int i = 0; i < result.getLength(); i++)
				algorithmNames.add(result.item(i).getTextContent());
			
			PrintWriter out = new PrintWriter(cmd.getOptionValue("out"));
			out.println(algorithmNames.toString());
			out.close();
	}
}
