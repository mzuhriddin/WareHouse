package com.example.warehouse.service;

import com.example.warehouse.dto.ApiResponse;
import com.example.warehouse.entity.Client;
import com.example.warehouse.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public ApiResponse save(Client client){
        clientRepository.save(client);
        return new ApiResponse("ClientService.save: Saved", true);
    }

    public ApiResponse update(Integer id, Client client){
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) return new ApiResponse(
                "ClientService.update: Client Not Fount", false);
        Client edited = optionalClient.get();
        edited.setName(client.getName());
        edited.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(edited);
        return new ApiResponse("ClientService.update: Updated", true);
    }

    public ApiResponse delete(Integer id){
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) return new ApiResponse(
                "ClientService.delete: Client Not Fount", false);
        clientRepository.delete(optionalClient.get());
        return new ApiResponse("ClientService.delete: Deleted", true);
    }


}
