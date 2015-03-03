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
