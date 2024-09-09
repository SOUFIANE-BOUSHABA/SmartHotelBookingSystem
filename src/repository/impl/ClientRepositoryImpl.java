package repository.impl;

import dao.ClientDAO;
import model.Client;
import repository.ClientRepository;

import java.util.List;

public class ClientRepositoryImpl  implements ClientRepository {
    private ClientDAO clientDAO;

    public ClientRepositoryImpl() {
        this.clientDAO = new ClientDAO();
    }

    @Override
    public void create(Client client) {
        clientDAO.createClient(client);
    }


    @Override
    public Client findById(int id) {
        return clientDAO.findById(id);
    }


    @Override
    public List<Client> findAll() {
        return clientDAO.findAll();
    }


    @Override
    public void update(Client client) {
        clientDAO.updateClient(client);
    }


    @Override
    public void delete(int id) {
        clientDAO.deleteClient(id);
    }

}
