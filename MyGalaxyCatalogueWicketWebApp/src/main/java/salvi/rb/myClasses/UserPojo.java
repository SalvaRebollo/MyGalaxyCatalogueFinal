package salvi.rb.myClasses;

import java.io.Serializable;

public class UserPojo implements Serializable {

    private String uidUser;
    private String nomUser;
    private String apeUser;
    private String dirUser;
    private String ciuUser;
    private String paisUser;
    private String emailUser;
    private String fotoPerfilUser;
    private String bioUser;
    private String metContactoUser;

    public UserPojo(String uidUser, String nomUser, String apeUser, String dirUser, String ciuUser, String paisUser, String emailUser, String fotoPerfilUser, String bioUser, String metContactoUser) {
        this.uidUser = uidUser;
        this.nomUser = nomUser;
        this.apeUser = apeUser;
        this.dirUser = dirUser;
        this.ciuUser = ciuUser;
        this.paisUser = paisUser;
        this.emailUser = emailUser;
        this.fotoPerfilUser = fotoPerfilUser;
        this.bioUser = bioUser;
        this.metContactoUser = metContactoUser;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getApeUser() {
        return apeUser;
    }

    public void setApeUser(String apeUser) {
        this.apeUser = apeUser;
    }

    public String getDirUser() {
        return dirUser;
    }

    public void setDirUser(String dirUser) {
        this.dirUser = dirUser;
    }

    public String getCiuUser() {
        return ciuUser;
    }

    public void setCiuUser(String ciuUser) {
        this.ciuUser = ciuUser;
    }

    public String getPaisUser() {
        return paisUser;
    }

    public void setPaisUser(String paisUser) {
        this.paisUser = paisUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getFotoPerfilUser() {
        return fotoPerfilUser;
    }

    public void setFotoPerfilUser(String fotoPerfilUser) {
        this.fotoPerfilUser = fotoPerfilUser;
    }

    public String getBioUser() {
        return bioUser;
    }

    public void setBioUser(String bioUser) {
        this.bioUser = bioUser;
    }

    public String getMetContactoUser() {
        return metContactoUser;
    }

    public void setMetContactoUser(String metContactoUser) {
        this.metContactoUser = metContactoUser;
    }
}
