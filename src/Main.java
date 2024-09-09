import controller.ClientController;
import model.Client;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Create a ClientController instance to handle client operations
        ClientController clientController = new ClientController();

        // Uncomment the line below if you want to create a new client
        // clientController.createClient(1, "John", "john@gmail.com", "0777777");

        // Find a client by ID
        Client client = clientController.findById(2);
        if (client != null) {
            System.out.println("Client found by ID: " + client.getName() + ", " + client.getEmail() + ", " + client.getPhone());
        } else {
            System.out.println("Client with ID 2 not found.");
        }

        // Update the client with ID 2
        clientController.updateClient(2, "John Updated", "updated_john@gmail.com", "0999999");

        // Find the updated client
        Client updatedClient = clientController.findById(2);
        if (updatedClient != null) {
            System.out.println("Client after update: " + updatedClient.getName() + ", " + updatedClient.getEmail() + ", " + updatedClient.getPhone());
        }

        // Find and print all clients
        System.out.println("\nAll Clients:");
        List<Client> clients = clientController.findAll(); // Ensure this method prints inside ClientService or return a list
        for (Client c : clients) {
            System.out.println("Client ID: " + c.getId() + ", Name: " + c.getName() + ", Email: " + c.getEmail() + ", Phone: " + c.getPhone());
        }

        // Delete a client by ID
        clientController.deleteClient(2);

        // Try to find the deleted client
        Client deletedClient = clientController.findById(2);
        if (deletedClient == null) {
            System.out.println("\nClient with ID 2 has been deleted.");
        }
    }
}
