/*
 * Copyright 2014 Sreejith Pillai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * 2015 modifications for XLSX support and better List<T> passing by Matt Mondok
*/

package com.edentech.excelmapper.mapreduce;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import com.edentech.excelmapper.parser.ExcelParser;


public class ExcelRecordReader extends RecordReader<LongWritable, List<Text>> {

	private LongWritable key;
	private List<Text> value;
	private InputStream is;
    ArrayList<ArrayList<String>> allLines;

	@Override
	public void initialize(InputSplit genericSplit, TaskAttemptContext context)
			throws IOException, InterruptedException {

		FileSplit split = (FileSplit) genericSplit;
		Configuration job = context.getConfiguration();
		final Path file = split.getPath();

		FileSystem fs = file.getFileSystem(job);
		FSDataInputStream fileIn = fs.open(split.getPath());

		is = fileIn;
       this.allLines = new ExcelParser().parseExcelData(is);
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		if (key == null) {
			key = new LongWritable(0);
			value = makeText(allLines.get(0));
		} else {
			if (key.get() < (this.allLines.size() - 1)) {
				long pos = (int) key.get();
				key.set(pos + 1);
                ArrayList<String> line = allLines.get((int) (pos + 1));
                value = makeText(line);
				pos++;
			} else {
				return false;
			}
		}
		if (key == null || value == null) {
			return false;
		} else {
			return true;
		}
	}

    private List<Text> makeText(List<String> vals){
        ArrayList<Text> textList = new ArrayList<Text>();
        for(String s: vals){
            textList.add(new Text(s));
        }
        return textList;
    }

	@Override
	public LongWritable getCurrentKey() throws IOException,
			InterruptedException {
		return key;
	}

	@Override
	public List<Text> getCurrentValue() throws IOException, InterruptedException {
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		return 0;
	}

	@Override
	public void close() throws IOException {
		if (is != null) {
			is.close();
		}
	}

}
