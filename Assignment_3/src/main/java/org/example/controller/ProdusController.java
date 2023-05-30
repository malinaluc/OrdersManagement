package org.example.controller;

import org.example.objects.Produs;
import org.example.reflection.AbstractDAO;
import org.example.reflection.ProdusDAO;
import org.example.singlePointAccess.GUIFrameSinglePointAccess;
import org.example.views.ProdusView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProdusController {

    private ProdusView produsView;

    ProdusDAO produsDAO;

    public void startLogic()
    {
        produsView = new ProdusView();
        GUIFrameSinglePointAccess.changePanel(produsView.getPanel(),"ProdusPanel");

        /**
         * Metoda trateaza cazul de apasare al butonului "Start". Trece de la panel-ul Produs la panel-ul Start
         */
        produsView.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreenController mainScreenController = new MainScreenController();
                mainScreenController.startLogic();
            }
        });

        /**
         * Metoda trateaza cazul de apasare al butonului "Client". Trece de la panel-ul Produs la panel-ul Start
         */
        produsView.getClientButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientController clientController = new ClientController();
                clientController.startLogic();
            }
        });

        /**
         * Metoda trateaza cazul de apasare al butonului "Comanda". Trece de la panel-ul Produs la panel-ul Comanda
         */
        produsView.getComandaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComandaController comandaController = new ComandaController();
                comandaController.startLogic();
            }
        });

        /**
         * Metoda trateaza cazul de apasare al butonului "View". Afiseaza sub forma JTable tabela Produs
         */
        produsView.getViewButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTable();
            }
        });


        /**
         * Metoda trateaza cazul de apasare al butonului "Edit". Editeaza produsul cu datele introduse in textField urile specifice
         */
        produsView.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(produsView.getIdProdusTextField().getText());
                String denumire = produsView.getDenumireProdusTextField().getText();
                int stoc = Integer.parseInt(produsView.getStocProdusTextField().getText());
                produsDAO = new ProdusDAO();
                Produs produs = new Produs(id,denumire,stoc);
                produsDAO.update(produs,id);
                getTable();
            }
        });

        /**
         * Metoda trateaza cazul de apasare al butonului "Delete". Sterge produsul cu id-ul introdus in textField ul specific id-ului
         */
        produsView.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(produsView.getIdProdusTextField().getText());
                produsDAO = new ProdusDAO();
                produsDAO.deleteObject(id);
                getTable();
            }
        });

        /**
         * Metoda trateaza cazul de apasare al butonului "Add". Adauga produsul cu datele introduse in textField urile specifice
         */
        produsView.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(produsView.getIdProdusTextField().getText());
                String denumire = produsView.getDenumireProdusTextField().getText();
                int stoc = Integer.parseInt(produsView.getStocProdusTextField().getText());
                produsDAO = new ProdusDAO();
                Produs produs = new Produs(id,denumire,stoc);
                produsDAO.insert(produs,id);
                getTable();
            }
        });
    }
    /**
     * Metoda genereaza tabelul Produs
     */
    public void getTable()
    {
        produsView.getTableModelProdus().setRowCount(0);
        produsView.getTableModelProdus().setColumnCount(0);
        produsDAO = new ProdusDAO();
        String[] tableHeader = produsDAO.getHeader();
        for(String coloumn : tableHeader)
        {
            produsView.getTableModelProdus().addColumn(coloumn);
        }
        List<Produs> listaProduse = produsDAO.findAll();
        if(listaProduse!=null)
        {
            for(Produs produs : listaProduse)
            {
                produsView.getTableModelProdus().addRow(new Object[]{produs.getId(),produs.getDenumire(),produs.getStoc()});
            }
        }
    }
}
