package org.example.controller;


import org.example.objects.Client;
import org.example.reflection.AbstractDAO;
import org.example.reflection.ClientDAO;

import org.example.singlePointAccess.GUIFrameSinglePointAccess;
import org.example.views.ClientView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @Author Lucacel Malina
 * Clasa este responsabila pentru tot ce tine de partea de comunicare intre interfata si cod. Practic, aici se trateaza cazurile de apasare a fiecarui buton
 * În contextul Java, o clasă de tip controller (controlor) reprezintă o componentă a unui framework de dezvoltare web
 * (de exemplu, Spring MVC) care este responsabilă pentru gestionarea cererilor primite de la client și
 * coordonarea interacțiunii între modelul de date și interfața utilizator.
 */
public class ClientController {

    private ClientView clientView;
    ClientDAO clientDAO;

    public  void startLogic()
    {
        clientView = new ClientView();
        GUIFrameSinglePointAccess.changePanel(clientView.getPanel(),"ClientPanel");


        /**
         * Metoda trateaza cazul de apasare al butonului "Comanda". Trece de la panel-ul Client la panel-ul Comanda
         */
        clientView.getComandaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComandaController comandaController = new ComandaController();
                comandaController.startLogic();
            }
        });

        /**
         * Metoda trateaza cazul de apasare al butonului "Produs". Trece de la panel-ul Client la panel-ul Produs
         */
       clientView.getProdusButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProdusController produsController = new ProdusController();
                produsController.startLogic();
            }
        });

        /**
         * Metoda trateaza cazul de apasare al butonului "Start". Trece de la panel-ul Client la panel-ul Start
         */
       clientView.getStartButton().addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               MainScreenController mainScreenController = new MainScreenController();
               mainScreenController.startLogic();
           }
       });

        /**
         * Metoda trateaza cazul de apasare al butonului "Delete". Sterge clientul cu id-ul introdus in textField ul specific id-ului
         */
        clientView.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    clientDAO = new ClientDAO();
                    int id = Integer.parseInt(clientView.getIdClientTextField().getText());
                    clientDAO.deleteObject(id);
                    getTable();
            }
        });

        /**
         * Metoda trateaza cazul de apasare al butonului "Add". Adauga client-ul cu datele introduse in textField urile specifice
         */
        clientView.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                clientDAO = new ClientDAO();
                int id = Integer.parseInt(clientView.getIdClientTextField().getText());
                String nume = clientView.getNumeClientTextField().getText();
                int varsta = Integer.parseInt(clientView.getVarstaClientTextField().getText());
                String email = clientView.getEmailClientTextField().getText();
                Client client = new Client(id,nume,email,varsta);
                clientDAO.insert(client,id);
                getTable();
            }
        });

        /**
         * Metoda trateaza cazul de apasare al butonului "Edit". Editeaza clientul cu datele introduse in textField urile specifice
         */
        clientView.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientDAO = new ClientDAO();
                int id = Integer.parseInt(clientView.getIdClientTextField().getText());
                String nume = clientView.getNumeClientTextField().getText();
                int varsta = Integer.parseInt(clientView.getVarstaClientTextField().getText());
                String email = clientView.getEmailClientTextField().getText();
                Client client = new Client(id,nume,email,varsta);
                if(clientDAO.findById(id) == null)
                {
                    GUIFrameSinglePointAccess.showDialogMessage("Client-ul cu ID-ul introdus nu exista");
                }
                else
                {
                    clientDAO.update(client,id);
                }
                getTable();
            }
        });
        /**
         * Metoda trateaza cazul de apasare al butonului "View". Afiseaza sub forma JTable tabela Client
         */
        clientView.getViewButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               getTable();
            }
        });
    }

    /**
     * Metoda genereaza tabelul Client
     */
    private void getTable()
    {
        clientView.getTableModel().setColumnCount(0);
        clientView.getTableModel().setRowCount(0);
        clientDAO = new ClientDAO();
        String[] tableHeader = clientDAO.getHeader();
        for(String column : tableHeader)
        {
            clientView.getTableModel().addColumn(column);
        }
        List<Client> listaClienti = clientDAO.findAll();
        if(listaClienti != null)
        {
            for(Client client : listaClienti)
            {
                clientView.getTableModel().addRow(new Object[]{client.getId(),client.getNume(),client.getEmail(),client.getVarsta()});
            }
        }
    }
}
