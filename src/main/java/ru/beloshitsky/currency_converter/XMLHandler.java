package ru.beloshitsky.currency_converter;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

public class XMLHandler extends DefaultHandler {

  Map<String, String> valuteInfoMap;
  String valuteID;
  String currentID = "";
  String currentTag = "";
  String text = "";
  boolean valuteIsFound = false;

  public XMLHandler(String valuteID) {
    this.valuteID = valuteID;
    valuteInfoMap = new HashMap<>();
  }

  @Override
  public void characters(char[] ch, int start, int length) {
    if (currentID.equals(valuteID)) {
      valuteIsFound = true;
      text = new String(ch, start, length);
      valuteInfoMap.put(currentTag, text);
    }
  }

  @Override
  public void startElement(String uri, String localName, String tag, Attributes attr) {
    currentTag = tag;
    // Если нужная валюта уже найдена просто пропускаем оставшиеся элементы
    if (valuteIsFound && !currentID.equals(valuteID)) {
      return;
    }
    if (tag.equals("Valute")) {
      currentID = attr.getValue("ID");
    }
  }

  @Override
  public void endDocument() {
    double oneToOneExchangeRate = getOneToOneExchangeRate(valuteInfoMap);
    System.out.println(
        "1 " + valuteInfoMap.get("CharCode") + " to RUB exchange rate = " + oneToOneExchangeRate);
  }

  private double getOneToOneExchangeRate(Map<String, String> valuteInfoMap) {
    String valueFormatted = valuteInfoMap.get("Value").replace(",", ".");
    String nominalFormatted = valuteInfoMap.get("Nominal").replace(",", ".");
    double value = Double.parseDouble(valueFormatted);
    double nominal = Double.parseDouble(nominalFormatted);
    return value / nominal;
  }
}
