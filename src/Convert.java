import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Convert {
    public static double convertCurrency(String targetCurrency, double amountToConvert) {
        String sourceCurrency = "USD"; // Set source currency to USD
        String apiUrl = "https://open.er-api.com/v6/latest/" + sourceCurrency;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            double[] exchangeRates = extractExchangeRates(response.toString());

            double sourceToUSD = exchangeRates[getIndex(sourceCurrency)];
            double targetToUSD = exchangeRates[getIndex(targetCurrency)];

            return amountToConvert * (targetToUSD / sourceToUSD);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if conversion fails
    }

    private static double[] extractExchangeRates(String jsonResponse) {
        int startIndex = jsonResponse.indexOf("\"rates\":{") + 8;
        int endIndex = jsonResponse.indexOf("}", startIndex);
        String ratesSubstring = jsonResponse.substring(startIndex, endIndex);

        String[] ratePairs = ratesSubstring.split(",");
        double[] exchangeRates = new double[ratePairs.length];

        for (int i = 0; i < ratePairs.length; i++) {
            String[] pair = ratePairs[i].split(":");
            exchangeRates[i] = Double.parseDouble(pair[1]);
        }

        return exchangeRates;
    }

    private static int getIndex(String currencyCode) {
        String[] supportedCurrencies = {
                "USD", "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN",
                "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTN", "BWP", "BYN", "BZD", "CAD", "CDF", "CHF", "CLP",
                "CNY", "COP", "CRC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD",
                "FKP", "FOK", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG",
                "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR",
                "KID", "KMF", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MDL", "MGA",
                "MKD", "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK",
                "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF",
                "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLE", "SLL", "SOS", "SRD", "SSP", "STN", "SYP", "SZL",
                "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TVD", "TWD", "TZS", "UAH", "UGX", "UYU", "UZS", "VES",
                "VND", "VUV", "WST", "XAF", "XCD", "XDR", "XOF", "XPF", "YER", "ZAR", "ZMW", "ZWL"
        };

        for (int i = 0; i < supportedCurrencies.length; i++) {
            if (supportedCurrencies[i].equals(currencyCode)) {
                return i;
            }
        }

        return -1;
    }
}
