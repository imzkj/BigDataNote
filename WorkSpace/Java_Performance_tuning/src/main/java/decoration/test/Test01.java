package decoration.test;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Test01 {
	public static void main(String[] args) throws FileNotFoundException {
		OutputStream fileOutputStream = new FileOutputStream("d:\\a.txt");
		OutputStream bom = new BufferedOutputStream(fileOutputStream);
		OutputStream dom = new DataOutputStream(bom);
	}

}
