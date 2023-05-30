package org.example.objects;

/**
 * Clasa Client reprezintă o entitate care descrie un client.
 * Clientul are un identificator unic, nume, email și vârsta.
 */

public class Client {

    private Integer id;
    private String nume;
    private String email;
    private Integer varsta;

    /**
     * Creează un obiect Client cu identificator, nume, email și vârstă specificate.
     *
     * @param id     Identificatorul clientului
     * @param nume   Numele clientului
     * @param email  Adresa de email a clientului
     * @param varsta Vârsta clientului
     */
    public Client(Integer id, String nume, String email, Integer varsta) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.varsta = varsta;
    }

    /**
     * Creează un obiect Client cu nume, email și vârstă specificate.
     *
     * @param nume   Numele clientului
     * @param email  Adresa de email a clientului
     * @param varsta Vârsta clientului
     */
    public Client(String nume, String email, Integer varsta) {
        this.nume = nume;
        this.email = email;
        this.varsta = varsta;
    }

    /**
     * Creează un obiect Client gol (fără atribute inițializate).
     */
    public Client() {

    }

    /**
     * Returnează identificatorul clientului.
     *
     * @return Identificatorul clientului
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setează identificatorul clientului.
     *
     * @param id Identificatorul clientului
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * Returnează numele clientului.
     *
     * @return Numele clientului
     */
    public String getNume() {
        return nume;
    }
    /**
     * Setează numele clientului.
     *
     * @param nume Numele clientului
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Returnează adresa de email a clientului.
     *
     * @return Adresa de email a clientului
     */
    public String getEmail() {
        return email;
    }
    /**
     * Setează adresa de email a clientului.
     *
     * @param email Adresa de email a clientului
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Returnează vârsta clientului.
     *
     * @return Vârsta clientului
     */
    public Integer getVarsta() {
        return varsta;
    }
    /**
     * Setează vârsta clientului.
     *
     * @param varsta Vârsta clientului
     */
    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }
}
