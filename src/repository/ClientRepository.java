package repository;

import model.Client;

import java.util.List;

public interface ClientRepository {

    void create(Client client);
    Client findById(int id);
    List<Client> findAll();
    void update(Client client);
    void delete(int id);
}
