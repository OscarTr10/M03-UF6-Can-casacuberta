import java.sql.Date;

public class Usuari {
    private int id;
    private String nom;
    private String cognoms;
    private String email;
    private String telefon;
    private String rol;
    private Date dataRegistre;

    public Usuari(int id, String nom, String cognoms, String email, String telefon, String rol, Date dataRegistre) {
        this.id = id;
        this.nom = nom;
        this.cognoms = cognoms;
        this.email = email;
        this.telefon = telefon;
        this.rol = rol;
        this.dataRegistre = dataRegistre;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }
    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    public Date getDataRegistre() {
        return dataRegistre;
    }
    public void setDataRegistre(Date dataRegistre) {
        this.dataRegistre = dataRegistre;
    }
}

