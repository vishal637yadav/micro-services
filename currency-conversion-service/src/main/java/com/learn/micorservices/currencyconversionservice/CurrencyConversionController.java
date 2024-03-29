package com.learn.micorservices.currencyconversionservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
            ){

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

        System.out.println("--------------before RestTemplate call---------------");
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
                //"http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class,
                uriVariables);

        System.out.println("--------------After RestTemplate call---------------");
        CurrencyConversion currencyConversion = responseEntity.getBody();
        //return new CurrencyConversion(10001L,from,to,quantity,BigDecimal.ONE,BigDecimal.ONE,"env");
        return new CurrencyConversion(
                currencyConversion.getId(),
                currencyConversion.getFrom(),
                currencyConversion.getTo(),
                currencyConversion.getQuantity(),
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment());

    }
}
