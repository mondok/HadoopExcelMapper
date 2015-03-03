package com.edentech.excelmapper.mapreduce;
import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelMapper extends
		Mapper<LongWritable, List<Text>, Text, Text> {

	private static Logger LOG = LoggerFactory.getLogger(ExcelMapper.class);

	public void map(LongWritable key, List<Text> value, Context context)
			throws InterruptedException, IOException {
        if (value.size() > 3) {
            String name = value.get(2) + " " + value.get(1);
            context.write(new Text(name), null);
        }
	}
}
