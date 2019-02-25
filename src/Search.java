import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Search {

	public static class TokenizerMapper extends
			Mapper<Object, Text, Text, IntWritable> {

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			String station = "MAO";
			Tuple tuple = new Tuple();
			try {
				tuple.parse(value.toString());
				if (station.equals(tuple.stnCode)) {
					context.write(new Text(String.valueOf(tuple.destStnName)),
							new IntWritable(1));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static class DistanceReducer extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Search");
		job.setJarByClass(Search.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(DistanceReducer.class);
		job.setReducerClass(DistanceReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setOutputFormatClass(CSVOutputFormat.class);
		FileInputFormat.addInputPath(job, new Path("input.csv"));
		FileOutputFormat.setOutputPath(job, new Path(
				"outputSearch"));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}