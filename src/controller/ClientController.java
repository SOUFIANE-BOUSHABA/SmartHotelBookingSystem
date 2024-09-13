package controller;

import model.Client;
import service.ClientService;

import java.util.List;

public class ClientController {

    private ClientService clientService;

    public ClientController() {
        clientService = new ClientService();
    }

    public void createClient(int id, String name, String email, String phone, double balance) {
        Client client = new Client(id, name, email, phone, balance);
        clientService.createClient(client);
    }



    public Client findById(int id) {
        return clientService.findById(id);
    }




    public List<Client> findAll() {
        return clientService.findAll();
    }



    public void updateClient(int id, String name, String email, String phone, double balance) {
        Client client = new Client(id, name, email, phone, balance);
        clientService.updateClient(client);
    }



    public void deleteClient(int id) {
        clientService.deleteClient(id);
    }
}
