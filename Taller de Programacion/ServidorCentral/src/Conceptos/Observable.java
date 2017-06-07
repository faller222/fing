package Conceptos;


public interface Observable {

    public void addObserver(Observer o);

    public void delObserver(Observer o);

    public void delObservers();

    public void notifyObservers();
}
