public class Loader {
  
  public static final String URI = "http://www.cbr.ru/scripts/XML_daily.asp";
  public static final String CURRENCY_ID = "R01820";
  
  public static void main(String[] args) {
    CurrencyParser currencyParser = new CurrencyParser(URI);
    currencyParser.printRubleToCurrencyRate(CURRENCY_ID);
  }
}
