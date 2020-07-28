package com.laurentiuspilca.tests.controllers;

import com.laurentiuspilca.tests.entities.Client;
import com.laurentiuspilca.tests.services.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping
  public void addClient(@RequestBody Client client) {
    clientService.addClient(client);
  }

  @GetMapping
  public List<Client> getAllClients() {
    return clientService.getAllClients();
  }
}
