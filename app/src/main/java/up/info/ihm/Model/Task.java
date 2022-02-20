package up.info.ihm.Model;

import java.util.Date;

public class Task {

    public enum Priority {
        HAUT,
        MOYEN,
        BAS
    }

    public enum Categorie {
        PERSONNEL,
        COURSES,
        ETUDE,
        SPORT,
        AUTRE
    }

    private int id;
    private String nom = "", description = "";
    private Date date_debut, date_fin;
    private Priority priority = Priority.BAS;
    private Categorie categorie = Categorie.AUTRE;

    public Task() {

    }

    public Task(String nom, String description, Date date_debut, Date date_fin, Priority priority, Categorie categorie) {
        this.nom = nom;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.priority = priority;
        this.categorie = categorie;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Task{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", priority=" + priority +
                ", categorie=" + categorie +
                '}';
    }
}
