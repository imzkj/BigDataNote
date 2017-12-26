package decoration.test;

public class PacketHTTPHeaderCreator extends PacketDecorator {
	public PacketHTTPHeaderCreator(IPacketCreator c) {
		super(c);
	}

	@Override
	public String handleContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("Cache-Control:no-cache\n");
		sb.append(component.handleContent());
		return sb.toString();
	}
	
	
	
}
