import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MaxTemperatureReducer 
		extends Reducer<Text, IntWritable, Text, IntWritable>
{
	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException
	{
		int maxV = Integer.MIN_VALUE;
		for(IntWritable val : values) {
			maxV = Math.max(maxV, val.get());
		}
		
		context.write(key, new IntWritable(maxV));
	}
}