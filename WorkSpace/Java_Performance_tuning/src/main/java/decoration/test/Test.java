package decoration.test;

public class Test {
    public static void main(String[] args) {
        IPacketCreator pc1 = new PacketBodyCreator();
        IPacketCreator pc2 = new PacketHTMLHeaderCreator(pc1);
        IPacketCreator pc3 = new PacketHTTPHeaderCreator(pc2);
        System.out.println(pc3.handleContent());
    }
}
