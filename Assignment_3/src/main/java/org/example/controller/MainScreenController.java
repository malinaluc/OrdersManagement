package org.example.controller;

import org.example.singlePointAccess.GUIFrameSinglePointAccess;
import org.example.views.StartView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreenController {

    private StartView startView;

    public void startLogic()
    {   startView = new StartView();
        GUIFrameSinglePointAccess.changePanel(startView.getPanel(),"MainScreen");

        startView.getClientButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientController clientController = new ClientController();
                clientController.startLogic();
            }
        });

        startView.getComandaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComandaController comandaController = new ComandaController();
                comandaController.startLogic();

            }
        });

        startView.getProdusButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProdusController produsController = new ProdusController();
                produsController.startLogic();
            }
        });
    }

}
