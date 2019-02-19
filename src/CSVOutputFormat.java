import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CSVOutputFormat extends FileOutputFormat<Text, IntWritable> {

	protected static class CSVRecordWriter extends RecordWriter<Text, IntWritable> {
		private DataOutputStream out;
		
		public CSVRecordWriter(DataOutputStream out) {
			this.out = out;
		}
		
		@Override
		public void close(TaskAttemptContext job) throws IOException,
				InterruptedException {
			out.close();
			
		}

		@Override
		public synchronized void write(Text key, IntWritable value) throws IOException,
				InterruptedException {
			out.writeBytes(key.toString());
			out.writeBytes(",");
			out.writeBytes(value.toString());
			out.writeBytes("\n");
		}
		
	}
	
	@Override
	public RecordWriter<Text, IntWritable> getRecordWriter(
			TaskAttemptContext job) throws IOException, InterruptedException {
		String ext = ".csv";
		Path file = getDefaultWorkFile(job, ext);
		FileSystem fs = file.getFileSystem(job.getConfiguration());
		FSDataOutputStream fileOut = fs.create(file, false);
		return new CSVRecordWriter(fileOut);
	}

}
