package service;

import model.Client;
import repository.ClientRepository;
import repository.impl.ClientRepositoryImpl;

import java.util.List;

public class ClientService {

    private ClientRepository clientRepository;

    public ClientService() {
        clientRepository = new ClientRepositoryImpl();
    }

    public void createClient(Client client) {
        clientRepository.create(client);
    }

    public Client findById(int id) {
        return clientRepository.findById(id);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }


    public void updateClient(Client client) {
        clientRepository.update(client);
    }

    public void deleteClient(int id) {
        clientRepository.delete(id);
    }

}
