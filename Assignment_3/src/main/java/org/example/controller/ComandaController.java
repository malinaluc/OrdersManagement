package org.example.controller;

import org.example.objects.Comanda;
import org.example.objects.Produs;
import org.example.reflection.AbstractDAO;
import org.example.reflection.ClientDAO;
import org.example.reflection.ComandaDAO;
import org.example.reflection.ProdusDAO;
import org.example.singlePointAccess.GUIFrameSinglePointAccess;
import org.example.views.ComandaView;

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

public class ComandaController {

    private ComandaView comandaView;

    ComandaDAO comandaDAO;
    ProdusDAO produsDAO;
    ClientDAO clientDAO;


    public void startLogic()
    {
        comandaView = new ComandaView();
        GUIFrameSinglePointAccess.changePanel(comandaView.getPanel(),"ComandaPanel");
        comandaView.getDeleteButton().setEnabled(false);
        comandaView.getEditButton().setEnabled(false);

        /**
         * Metoda trateaza cazul de apasare al butonului "Produs". Trece de la panel-ul Comanda la panel-ul Produs
         */
        comandaView.getProdusButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProdusController produsController = new ProdusController();
                produsController.startLogic();
            }
        });

        /**
         * Metoda trateaza cazul de apasare al butonului "Start". Trece de la panel-ul Comanda la panel-ul Start
         */
        comandaView.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreenController mainScreenController = new MainScreenController();
                mainScreenController.startLogic();
            }
        });

        /**
         * Metoda trateaza cazul de apasare al butonului "Client". Trece de la panel-ul Comanda la panel-ul Client
         */
        comandaView.getClientButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientController clientController = new ClientController();
                clientController.startLogic();
            }
        });

        /**
        * Metoda trateaza cazul de apasare al butonului "View". Afiseaza sub forma JTable tabela Comanda
        */
        comandaView.getViewButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTable();
            }
        });


/**
 * Metoda trateaza cazul de apasare al butonului "Add". Adauga ccomanda cu datele introduse in textField urile specifice
 */
        comandaView.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                clientDAO = new ClientDAO();
                produsDAO = new ProdusDAO();
                comandaDAO = new ComandaDAO();

                int nr_produse_comandate = Integer.parseInt(comandaView.getNrProduseComandate().getText());
                int idComanda = Integer.parseInt(comandaView.getIdComandaTextField().getText());
                int idProdus = Integer.parseInt(comandaView.getIdProdusTextField().getText());
                int idClient = Integer.parseInt(comandaView.getIdClientTextField().getText());
                int stocProdus = produsDAO.findStocById(idProdus);
                if((stocProdus>= nr_produse_comandate) && (clientDAO.findById(idClient)!=null))
                {
                    Comanda comanda = new Comanda(idComanda,idProdus,idClient,nr_produse_comandate);
                    comandaDAO.insert(comanda,idComanda);
                    Produs produs = produsDAO.findById(idProdus);
                    produs.setStoc(produs.getStoc()-nr_produse_comandate);
                    produsDAO.update(produs,idProdus);
                }
                else
                {
                    if(stocProdus< nr_produse_comandate) GUIFrameSinglePointAccess.showDialogMessage("Nu sunt destule produse pe stoc");
                    if(clientDAO.findById(idClient)==null) GUIFrameSinglePointAccess.showDialogMessage("Clientul introdus nu exista");
                }
               /// System.out.println("LOG STOC: " + stoc);
            }
        });
    }

    /**
     * Metoda genereaza tabelul Comanda
     */
    public void getTable()
    {
        comandaView.getTableModelComanda().setRowCount(0);
        comandaView.getTableModelComanda().setColumnCount(0);
        comandaDAO = new ComandaDAO();
        String[] tableHeader = comandaDAO.getHeader();
        for(String coloumn : tableHeader)
        {
            comandaView.getTableModelComanda().addColumn(coloumn);
        }
        List<Comanda> listaComenzi = comandaDAO.findAll();
        if(listaComenzi!=null)
        {
            for(Comanda comanda : listaComenzi)
            {
                comandaView.getTableModelComanda().addRow(new Object[]{comanda.getId(),comanda.getIdprodus(),comanda.getIdclient(),comanda.getNr_produse_comandate()});
            }
        }
    }

}
