package observation;

//观察者模式
public class Test {
	public static void main(String[] args) {
		IObserver o1 = new ConcreteObserver();
		IObserver o2 = new ConcreteObserver();
		ISubject is = new ConcreteSubject();
		is.attach(o1);
		is.attach(o2);
		is.inform(5);
	}

}
