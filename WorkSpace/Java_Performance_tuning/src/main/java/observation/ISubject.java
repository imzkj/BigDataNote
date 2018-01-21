package observation;

public interface ISubject {
  public void attach(IObserver observer);
  public void inform(int state);
}
