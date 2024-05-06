package puissance4.model;

public enum Couleur {
    ROUGE("x"),
    JAUNE("o"),
    VIDE("_");

    Couleur(String graphic) {
        this.graphic = graphic;
    }

    final String graphic;

    public String getGraphic() {
        return graphic;
    }
}
