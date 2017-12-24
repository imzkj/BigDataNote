package org.robby.MongoDB;

import org.apache.hadoop.io.Writable;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * mongodb自定义数据类型
 * 
 * @author gerry
 *
 */
public interface MongoDBWritable extends Writable {
	/**
	 * 从mongodb中读取数据
	 * 
	 * @param dbObject
	 */
	public void readFields( DBObject dbObject );

	/**
	 * 往mongodb中写入数据
	 * 
	 * @param dbCollection
	 */
	public void write( DBCollection dbCollection );

}
