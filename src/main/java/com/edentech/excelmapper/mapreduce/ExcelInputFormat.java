package com.edentech.excelmapper.mapreduce;
import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;


public class ExcelInputFormat extends FileInputFormat<LongWritable,List<Text>>{

	@Override
	public RecordReader<LongWritable, List<Text>> createRecordReader(InputSplit split,
			TaskAttemptContext context) throws IOException, InterruptedException {
		
		return new ExcelRecordReader();
	}

}
