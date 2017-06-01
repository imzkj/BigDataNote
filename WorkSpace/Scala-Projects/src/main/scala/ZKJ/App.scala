package ZKJ

import java.io.File

/**
  * Hello world!
  *
  */
class DIR {
  def getSubdirIterator(dir: File): Iterator[File] = {
    //获取传入目录的文件并过滤出目录
     val childDirs = dir.listFiles.filter(_.isDirectory)
    //childDirs.toIterator得到本级目录的遍历
    //++ 将后面的返回值添加到遍历中，相当于add作用
    //flatMap为对childDirs.toIterator中的每个元素都调用getSubdirIterator方法得到下一级目录
    childDirs.toIterator ++ childDirs.toIterator.flatMap(getSubdirIterator(_))
  }
}

object App {
  def main(args: Array[String]): Unit = {
    val dirs = new DIR
    val iterator = dirs.getSubdirIterator(new File("D:\\Kugou"))
    for (d <- iterator) println(d)
  }
}
