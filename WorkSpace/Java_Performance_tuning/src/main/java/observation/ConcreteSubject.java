package observation;

import java.util.Vector;

public class ConcreteSubject implements ISubject {
    Vector<IObserver> observers = new Vector<IObserver>();
	@Override
	public void attach(IObserver observer) {
		observers.addElement(observer);
	}

	@Override
	public void inform(int state) {
		if(state==5) {
			for(IObserver ob:observers) {
				ob.update(state);
			}
		}
		
	}
  
}
