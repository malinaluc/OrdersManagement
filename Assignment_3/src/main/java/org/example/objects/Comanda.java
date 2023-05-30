package org.example.objects;
/**
 * Clasa Comanda reprezintă o entitate care descrie o comandă plasată de un client pentru un anumit produs.
 * Comanda are un identificator unic, precum și informații despre produsul comandat, clientul și numărul de produse comandate.
 */

public class Comanda {
    private Integer id;
    private Integer idprodus;
    private Integer idclient;

    private Integer nr_produse_comandate;
    /**
     * Creează un obiect Comanda cu identificator, id produs, id client și număr de produse comandate specificate.
     *
     * @param id                  Identificatorul comenzii
     * @param idprodus            Identificatorul produsului comandat
     * @param idclient            Identificatorul clientului care a plasat comanda
     * @param nr_produse_comandate Numărul de produse comandate
     */
    public Comanda(Integer id, Integer idprodus, Integer idclient, Integer nr_produse_comandate) {
        this.id = id;
        this.idprodus = idprodus;
        this.idclient = idclient;
        this.nr_produse_comandate = nr_produse_comandate;
    }
    /**
     * Creează un obiect Comanda cu id produs, id client și număr de produse comandate specificate.
     *
     * @param idprodus            Identificatorul produsului comandat
     * @param idclient            Identificatorul clientului care a plasat comanda
     * @param nr_produse_comandate Numărul de produse comandate
     */
    public Comanda(Integer idprodus, Integer idclient, Integer nr_produse_comandate) {
        this.idprodus = idprodus;
        this.idclient = idclient;
        this.nr_produse_comandate = nr_produse_comandate;
    }
    /**
     * Creează un obiect Comanda gol (fără atribute inițializate).
     */
    public Comanda() {
    }
    /**
     * Returnează numărul de produse comandate.
     *
     * @return Numărul de produse comandate
     */
    public Integer getNr_produse_comandate() {
        return nr_produse_comandate;
    }
    /**
     * Setează numărul de produse comandate.
     *
     * @param nr_produse_stoc Numărul de produse comandate
     */
    public void setNr_produse_comandate(Integer nr_produse_stoc) {
        this.nr_produse_comandate = nr_produse_stoc;
    }
    /**
     * Returnează identificatorul comenzii.
     *
     * @return Identificatorul comenzii
     */
    public Integer getId() {
        return id;
    }
    /**
     * Setează identificatorul comenzii.
     *
     * @param id Identificatorul comenzii
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Returnează identificatorul produsului comandat.
     *
     * @return Identificatorul produsului comandat
     */
    public Integer getIdprodus() {
        return idprodus;
    }
    /**
     * Setează identificatorul produsului comandat.
     *
     * @param idprodus Identificatorul produsului comandat
     */
    public void setIdprodus(Integer idprodus) {
        this.idprodus = idprodus;
    }
    /**
     * Returnează identificatorul clientului care a plasat comanda.
     *
     * @return Identificatorul clientului care a plasat comanda
     */
    public Integer getIdclient() {
        return idclient;
    }
    /**
     * Setează identificatorul clientului care a plasat comanda.
     *
     * @param idclient Identificatorul clientului care a plasat comanda
     */
    public void setIdclient(Integer idclient) {
        this.idclient = idclient;
    }
}
