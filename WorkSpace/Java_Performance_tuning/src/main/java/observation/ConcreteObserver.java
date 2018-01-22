package observation;

public class ConcreteObserver implements IObserver {

	@Override
	public void update(int state) {
		System.out.println("我已经观察到"+state+"所有显示变成5");
	}
    
}
