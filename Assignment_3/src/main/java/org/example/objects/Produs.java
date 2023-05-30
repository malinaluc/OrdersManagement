package org.example.objects;
/**
 * Clasa Produs reprezintă o entitate care descrie un produs.
 * Produsul are un identificator unic, denumire și stoc disponibil.
 */
public class Produs {

    private Integer id;
    private String denumire;
    private Integer stoc;
    /**
     * Creează un obiect Produs cu identificator, denumire și stoc specificate.
     *
     * @param id        Identificatorul produsului
     * @param denumire  Denumirea produsului
     * @param stoc      Stocul disponibil pentru produs
     */
    public Produs(Integer id, String denumire, Integer stoc) {
        this.id = id;
        this.denumire = denumire;
        this.stoc = stoc;
    }
    /**
     * Creează un obiect Produs cu denumire și stoc specificate.
     *
     * @param denumire  Denumirea produsului
     * @param stoc      Stocul disponibil pentru produs
     */
    public Produs(String denumire, Integer stoc) {
        this.denumire = denumire;
        this.stoc = stoc;
    }
    /**
     * Creează un obiect Produs gol (fără atribute inițializate).
     */
    public Produs() {
    }
    /**
     * Returnează identificatorul produsului.
     *
     * @return Identificatorul produsului
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setează identificatorul produsului.
     *
     * @param id Identificatorul produsului
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Returnează denumirea produsului.
     *
     * @return Denumirea produsului
     */
    public String getDenumire() {
        return denumire;
    }
    /**
     * Setează denumirea produsului.
     *
     * @param denumire Denumirea produsului
     */
    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
    /**
     * Returnează stocul disponibil pentru produs.
     *
     * @return Stocul disponibil pentru produs
     */
    public Integer getStoc() {
        return stoc;
    }
    /**
     * Returnează stocul disponibil pentru produs.
     *
     * @return Stocul disponibil pentru produs
     */
    public void setStoc(Integer stoc) {
        this.stoc = stoc;
    }
}
