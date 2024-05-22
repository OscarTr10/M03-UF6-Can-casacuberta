import java.sql.Date;

public class Prestecs {
    private int idPrestec;
    private int idLlibre;
    private int idUsuari;
    private Date dataPrestec;
    private Date dataRetornPrevista;
    private Date dataRetornReal;
    private String estat;

    public Prestecs(int idPrestec, int idLlibre, int idUsuari, Date dataPrestec, Date dataRetornPrevista, Date dataRetornReal, String estat) {
        this.idPrestec = idPrestec;
        this.idLlibre = idLlibre;
        this.idUsuari = idUsuari;
        this.dataPrestec = dataPrestec;
        this.dataRetornPrevista = dataRetornPrevista;
        this.dataRetornReal = dataRetornReal;
        this.estat = estat;
    }

    public int getIdPrestec() {
        return idPrestec;
    }
    public void setIdPrestec(int idPrestec) {
        this.idPrestec = idPrestec;
    }

    public int getIdLlibre() {
        return idLlibre;
    }
    public void setIdLlibre(int idLlibre) {
        this.idLlibre = idLlibre;
    }

    public int getIdUsuari() {
        return idUsuari;
    }
    public void setIdUsuari(int idUsuari) {
        this.idUsuari = idUsuari;
    }

    public Date getDataPrestec() {
        return dataPrestec;
    }
    public void setDataPrestec(Date dataPrestec) {
        this.dataPrestec = dataPrestec;
    }

    public Date getDataRetornPrevista() {
        return dataRetornPrevista;
    }
    public void setDataRetornPrevista(Date dataRetornPrevista) {
        this.dataRetornPrevista = dataRetornPrevista;
    }

    public Date getDataRetornReal() {
        return dataRetornReal;
    }
    public void setDataRetornReal(Date dataRetornReal) {
        this.dataRetornReal = dataRetornReal;
    }

    public String getEstat() {
        return estat;
    }
    public void setEstat(String estat) {
        this.estat = estat;
    }
}