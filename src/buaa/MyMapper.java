package buaa;
import java.io.IOException;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.mapred.MapperBase;


public class MyMapper extends MapperBase {
	private Record key;
	private Record value;

	@Override
	public void setup(TaskContext context) throws IOException {
		key = context.createMapOutputKeyRecord();
		value = context.createMapOutputValueRecord();
	}

	@Override
	public void map(long recordNum, Record record, TaskContext context)
			throws IOException {
		key.set(new Object[]{record.get(0).toString()+"#"+record.get(1).toString()});
		value.set(new Object[]{record.get(2).toString()+"#"+record.get(3).toString()});
		context.write(key,value);
	}

	@Override
	public void cleanup(TaskContext context) throws IOException {
	}

}
