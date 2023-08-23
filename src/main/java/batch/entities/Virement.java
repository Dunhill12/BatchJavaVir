package batch.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "vir_ambassade_as")
public class Virement {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "montant")
    private Float montant;
    @Column(name = "cpt")
    private String cpt;
    @Column(name = "codebanque")
    private String codeBanque;
    @Column(name = "banque")
    private String banque;
    @Column(name = "sens")
    private String sens;
    @Column(name = "etat")
    private String etat;
    @Column(name = "datederniervirement")
    private Date dateDernierVirement;
    @Column(name = "datesys")
    private Date dateSys;
    @Column(name = "devise")
    private String Devise;
    @Column(name = "tier")
    private String tier;

    // constructors
    public Virement() {
    }
    public Virement(long id, String nom, Float montant, String cpt, String codeBanque, String banque, String sens, String etat, Date dateDernierVirement, Date dateSys, String devise, String tier) {
        this.id = id;
        this.nom = nom;
        this.montant = montant;
        this.cpt = cpt;
        this.codeBanque = codeBanque;
        this.banque = banque;
        this.sens = sens;
        this.etat = etat;
        this.dateDernierVirement = dateDernierVirement;
        this.dateSys = dateSys;
        this.Devise = devise;
        this.tier = tier;
    }


    /*
    // getters
     */
    public String getId() {
        return id.toString();
    }
    public String getNom() {
        return nom;
    }
    public String getMontant() {
        return  montant % 1 == 0 ? String.format("%.0f", montant).replace(".", ",")+"," : String.format("%.2f", montant).replace(".", ",");

    }
    public String getCpt() {
        return cpt;
    }
    public String getCodeBanque() {
        return codeBanque;
    }
    public String getBanque() {
        return banque;
    }
    public String getSens() {
        return sens;
    }
    public String getEtat() {
        return etat;
    }
    public String getDateDernierVirement() {
        return dateDernierVirement.toString();
    }
    public String getDateSys() {
        return dateSys.toString();
    }
    public String getDevise() {
        return Devise;
    }
    public String getTier() {
        return tier;
    }

    /*
    // setters
     */
    public void setId(long id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setMontant(Float montant) {
        this.montant = montant;
    }
    public void setCpt(String cpt) {
        this.cpt = cpt;
    }
    public void setCodeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
    }
    public void setBanque(String banque) {
        this.banque = banque;
    }
    public void setSens(String sens) {
        this.sens = sens;
    }
    public void setEtat(String etat) {
        this.etat = etat;
    }
    public void setDateDernierVirement(Date dateDernierVirement) {
        this.dateDernierVirement = dateDernierVirement;
    }
    public void setDateSys(Date dateSys) {
        this.dateSys = dateSys;
    }
    public void setDevise(String devise) {
        this.Devise = devise;
    }
    public void setTier(String tier) {
        this.tier = tier;
    }

    @Override
    public String toString() {
        return "Virement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", montant=" + montant +
                ", cpt='" + cpt + '\'' +
                ", codeBanque='" + codeBanque + '\'' +
                ", banque='" + banque + '\'' +
                ", sens='" + sens + '\'' +
                ", etat='" + etat + '\'' +
                ", dateDernierVirement=" + dateDernierVirement +
                ", dateSys=" + dateSys +
                ", Devise='" + Devise + '\'' +
                ", tier='" + tier + '\'' +
                '}';
    }


}
