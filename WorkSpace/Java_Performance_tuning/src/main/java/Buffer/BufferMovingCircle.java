package Buffer;

import java.awt.Color;
import java.awt.Graphics;

public class BufferMovingCircle extends NoBufferMovingCircle {
	Graphics doubleBuffer = null;
  public void init() {
	  super.init();
	  doubleBuffer = screeImage.getGraphics();
  }
  
  public void paint(Graphics g) {
	  doubleBuffer.setColor(Color.white);
	  doubleBuffer.fillRect(0, 0, 200, 100);
	  drawCircle(doubleBuffer);
	  g.drawImage(screeImage, 0, 0, this);
  }
}
