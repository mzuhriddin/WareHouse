package com.example.warehouse.controller;

import com.example.warehouse.entity.Input;
import com.example.warehouse.entity.Currency;
import com.example.warehouse.repository.InputRepository;
import com.example.warehouse.repository.CurrencyRepository;
import com.example.warehouse.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    InputRepository inputRepository;

    @GetMapping
    public String getCurrencyPage(Model model) {
        model.addAttribute("list", currencyRepository.findAllByActiveTrue());
        return "currency/currency";
    }

    @GetMapping("/add")
    public String getSaveCurrency() {return "currency/currency-add";}

    @PostMapping("/add")
    public String saveCurrency(@ModelAttribute Currency currency) {
        currencyRepository.save(currency);
        return "redirect:/currency";
    }

    @GetMapping("/delete/{id}") //1 45 24 90
    public String delete(@PathVariable Integer id) {
        Optional<Input> inputByCurrency_id = inputRepository.findInputByCurrency_Id(id);
        if (inputByCurrency_id.isEmpty()) {
            Optional<Currency> optionalCurrency = currencyRepository.findById(id);
            if (optionalCurrency.isEmpty()) {
                return "redirect:/currency";
            }
            Currency currency = optionalCurrency.get();
            currency.setActive(false);
            currencyRepository.save(currency);
        }
        return "redirect:/currency";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if(optionalCurrency.isEmpty()) return "currency/currency";
        model.addAttribute("currency", optionalCurrency.get());
        return "currency/currency-edit";
    }

    @PostMapping("/edit/{id}")
    public String editCurrency(@ModelAttribute Currency currency) {
        currencyRepository.save(currency);
        return "redirect:/currency";
    }
}
