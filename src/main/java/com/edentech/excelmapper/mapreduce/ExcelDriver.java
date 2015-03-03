package com.edentech.excelmapper.mapreduce;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelDriver {
	
	private static Logger logger = LoggerFactory.getLogger(ExcelDriver.class);

	public static void main(String[] args) throws Exception {		
		logger.info("Driver started");
		
		Job job = new Job();
		job.setJarByClass(ExcelDriver.class);
		job.setJobName("Excel Record Reader");
		
		job.setMapperClass(ExcelMapper.class);
		job.setNumReduceTasks(0);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setInputFormatClass(ExcelInputFormat.class);
		
		job.waitForCompletion(true);
	}

}
