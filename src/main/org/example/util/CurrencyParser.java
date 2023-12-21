package main.org.example.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import main.org.example.model.Currency;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CurrencyParser
{
    public CurrencyParser() {}

    private static String CURRENCY_URL = "https://www.nbrb.by/Services/XmlExRates.aspx";

    private static Document loadDocument(String url) {
        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            doc = factory.newDocumentBuilder().parse(new java.net.URL(url).openStream());
        } catch (java.net.ConnectException e) {
            System.err.print(" Oops! Something goes wrong! This is Belarus, baby! \nConnection timed out. ");
            System.err.print(CURRENCY_URL + " is not responsible. Please, try again later");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static void extractXML(String path){
        Document doc = loadDocument(CURRENCY_URL);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            FileWriter writer = new FileWriter(new File(path));
            StreamResult result = new StreamResult(writer);

            transformer.transform(source, result);

        } catch (Exception e) {
            System.out.println("Err");
            throw new RuntimeException(e);
        }
    }

    public static String getCurrency(String currencyCode)
    {
        boolean isCurrencyCodeNext = false;
        Document doc = loadDocument(CURRENCY_URL);

        if (doc != null) {
            NodeList nodes = doc.getFirstChild().getChildNodes();


            for (int i = 0; i < nodes.getLength(); i++) {
                Node parent = nodes.item(i);

                if (parent.getNodeType() == 1) {
                    NodeList childs = parent.getChildNodes();

                    for (int ii = 0; ii < childs.getLength(); ii++) {
                        Node child = childs.item(ii);
                        if (child.hasChildNodes()) {
                            if ((child.getNodeName().trim().equalsIgnoreCase("Rate")) && (isCurrencyCodeNext)) {
                                isCurrencyCodeNext = false;
                                return child.getFirstChild().getTextContent();
                            }

                            if (child.getFirstChild().getTextContent().trim().equalsIgnoreCase(currencyCode)) {
                                isCurrencyCodeNext = true;
                            }
                        }
                    }
                }
            }
        }
        return "0.0";
    }

    public static List<Currency> getAllCurrencies(){
        Document doc = loadDocument(CURRENCY_URL);
        if (doc == null){
            return null;
        }
        NodeList nodes = doc.getFirstChild().getChildNodes();
        List<Currency> all = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++){
            if (nodes.item(i).getNodeName().equals("#text")){
                continue;
            }
            Currency currency = new Currency();
            currency.setId(Integer.parseInt(nodes.item(i).getAttributes().getNamedItem("Id").getNodeValue()));
            for (int j = 0; j < nodes.item(i).getChildNodes().getLength(); j++){
                if (nodes.item(i).getChildNodes().item(j).getNodeName().equals("#text")){
                    continue;
                }
                switch (nodes.item(i).getChildNodes().item(j).getNodeName()){
                    case "NumCode":
                        currency.setNumCode(Integer.parseInt(nodes.item(i).getChildNodes().item(j).getTextContent()));
                        break;
                    case "CharCode":
                        currency.setCharCode(nodes.item(i).getChildNodes().item(j).getTextContent());
                        break;
                    case "Scale":
                        currency.setScale(Integer.parseInt(nodes.item(i).getChildNodes().item(j).getTextContent()));
                        break;
                    case "Name":
                        currency.setName(nodes.item(i).getChildNodes().item(j).getTextContent());
                        break;
                    case "Rate":
                        currency.setRate(Double.parseDouble(nodes.item(i).getChildNodes().item(j).getTextContent()));
                        break;
                }
            }
            all.add(currency);
        }
        System.out.println(all.size());
        return all;
    }

    public static void main(String[] args) {
        //Document doc = loadDocument(CURRENCY_URL);
        //.out.println(Integer.parseInt(doc.getFirstChild().getChildNodes().item(3).getAttributes().getNamedItem("Id").getNodeValue()));

        List<Currency> all = getAllCurrencies();
        all.forEach((s) -> System.out.println(s));
        //String val = getCurrency("840");
        //System.out.println(val);
    }
}
