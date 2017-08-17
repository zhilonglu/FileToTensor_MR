package buaa;
import java.io.IOException;
import java.util.Iterator;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.mapred.ReducerBase;


public class MyReducer extends ReducerBase {
	private Record result = null;
	@Override
	public void setup(TaskContext context) throws IOException {
		result = context.createOutputRecord();
	}

	@Override
	public void reduce(Record key, Iterator<Record> values, TaskContext context)
			throws IOException {
		double[] tts = new double[90];
		while (values.hasNext()) {
			String line = values.next().get(0).toString();
			String[] lines = line.split("#");
			System.out.println(lines[0]);
			String start = lines[0].split(",")[0];
			System.out.println(start);
			String start_time = start.split(" ")[1];
			String[] v = start_time.split(":");
			int hour = Integer.valueOf(v[0]);
			int min = Integer.valueOf(v[1]);
			int baseHour = 0;
			if(hour>=6 && hour<9){
				baseHour = 6;
			}
			else if(hour>=13 && hour<16){
				baseHour = 13;
			}else{
				baseHour = 16;
			}
			int idx = (hour-baseHour)*30 + min/2;
			tts[idx] = Double.valueOf(lines[1]);
			String link = key.get(0).toString().split("#")[0];
			String time = key.get(0).toString().split("#")[1];
			result.set(0,link);
			result.set(1,time);
			for(int i=0;i<tts.length;i++){
				result.set(i+2,tts[i]);
			}
		}
		context.write(result);
	}

	@Override
	public void cleanup(TaskContext context) throws IOException {
	}

}
