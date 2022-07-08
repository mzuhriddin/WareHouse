package com.example.warehouse.controller;

import com.example.warehouse.entity.Client;
import com.example.warehouse.repository.ClientRepository;
import com.example.warehouse.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientService clientService;

    @GetMapping
    public String getClientPage(Model model){
        model.addAttribute("clientList", clientRepository.findAll());
        return "client/client";
    }

    @GetMapping("/add")
    public String getAddPage(){
        return "client/client-add";
    }

    @PostMapping("/add")
    public String addClient(@ModelAttribute Client client){
        clientService.save(client);
        return "redirect:/client";
    }

    @GetMapping("edit/{id}")
    public String getEditPage(@PathVariable Integer id, Model model){
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) return "error/error";
        model.addAttribute("client", optionalClient.get());
        return "client/client-edit";
    }

    @PostMapping("/edit/{id}")
    public String editClient(@PathVariable Integer id, @ModelAttribute Client client){
        clientService.update(id, client);
        return "redirect:/client";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Integer id){
        clientService.delete(id);
        return "redirect:/client";
    }

}
