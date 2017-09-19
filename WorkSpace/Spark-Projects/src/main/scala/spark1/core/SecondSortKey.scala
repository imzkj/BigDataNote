package spark1.core

/**
 * @author Administrator
 */
class SecondSortKey(val first: Int, val second: Int) 
    extends Ordered[SecondSortKey] with Serializable {
  
  def compare(that: SecondSortKey): Int = {
    if(this.first - that.first != 0) {
      this.first - that.first
    } else {
      this.second - that.second
    }
  }
  
}