package ru.beloshitsky.currency_converter;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CurrencyParser {

  private final String URI;

  public CurrencyParser(String URI) {
    this.URI = URI;
  }

  public void printRubleToCurrencyRate(String currencyID) {
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(URI);
      
      NodeList valuteList = doc.getElementsByTagName("Valute");
      String message = "Currency is not found";
      for (int i = 0; i < valuteList.getLength(); i++) {
        Node valuteNode = valuteList.item(i);
        NamedNodeMap valuteAttributes = valuteNode.getAttributes();
        String valuteID = valuteAttributes.item(0).getNodeValue();

        if (valuteID.equals(currencyID)) {
          Map<String, String> valuteInfoMap = parseValuteInfo(valuteNode);
          double oneToOneExchangeRate = getOneToOneExchangeRate(valuteInfoMap);
          message = getInfoMessage(valuteInfoMap, oneToOneExchangeRate);
          break;
        }
      }
      System.out.println(message);
      
    } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
      e.printStackTrace();
    }
  }
  
  private String getInfoMessage(Map<String, String> valuteInfoMap, double oneToOneExchangeRate) {
    String message;
    message =
        "1 "
            + valuteInfoMap.get("CharCode")
            + " to RUB exchange rate = "
            + oneToOneExchangeRate;
    return message;
  }
  
  private Map<String, String> parseValuteInfo(Node valuteNode) {
    Map<String, String> valuteInfoMap = new HashMap<>();
    Node valuteChildNode = valuteNode.getFirstChild();
    
    while (valuteChildNode != null) {
      String nodeName = valuteChildNode.getNodeName();
      String nodeText = valuteChildNode.getTextContent();

      valuteInfoMap.put(nodeName, nodeText);
      valuteChildNode = valuteChildNode.getNextSibling();
    }
    return valuteInfoMap;
  }
  
  private double getOneToOneExchangeRate(Map<String, String> valuteInfoMap) {
    String valueFormatted = valuteInfoMap.get("Value").replace(",", ".");
    String nominalFormatted = valuteInfoMap.get("Nominal").replace(",", ".");
    double value = Double.parseDouble(valueFormatted);
    double nominal = Double.parseDouble(nominalFormatted);
    return value / nominal;
  }
}
