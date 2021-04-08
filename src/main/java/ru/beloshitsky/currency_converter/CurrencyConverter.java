package ru.beloshitsky.currency_converter;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class CurrencyConverter {
  
  SAXParserFactory factory;
  javax.xml.parsers.SAXParser saxParser;
  String URI;
  
  public CurrencyConverter(String URI) throws ParserConfigurationException, SAXException {
    factory = SAXParserFactory.newInstance();
    saxParser = factory.newSAXParser();
    this.URI = URI;
  }
  
  public void printRubToCurrencyRate(String valuteCode) throws IOException, SAXException {
    saxParser.parse(URI, new XMLHandler(valuteCode));
  }
}
