package puissance4.controller;

public interface PlayerActionListener {

    /**
     *  Interface PlayerActionListener
     *  Méthodes qui permettent au Controller de se tenir au courant des "clicks" de l'utilisateur et de déclencher les
     *  comportements appropriés lorsque elles sont appelées dans les view :
     *  pour placer un jeton, pour quitter la partie, pour jouer à nouveau
     * @param index
     */
    public void onPlayerMove(int index);

    public void onPlayerQuit();

    public void onPlayerReplay();
}


