package ru.beloshitsky.currency_converter;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
  
  public static final String URI = "http://www.cbr.ru/scripts/XML_daily.asp";
  public static final String CURRENCY_ID = "R01500";

  public static void main(String[] args)
      throws ParserConfigurationException, SAXException, IOException {

    CurrencyConverter converter = new CurrencyConverter(URI);
    converter.printRubToCurrencyRate(CURRENCY_ID);
  }
}
