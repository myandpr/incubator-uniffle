/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.uniffle.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.LargeSorter;
import org.apache.hadoop.mapreduce.MRClientConf;
import org.apache.hadoop.util.Tool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.apache.uniffle.common.ClientType;
import org.apache.uniffle.storage.util.StorageType;

public class DynamicConfTest extends MRIntegrationTestBase {

  @BeforeAll
  public static void setupServers() throws Exception {
    MRIntegrationTestBase.setupServers(DynamicConfTest.getDynamicConf());
  }

  protected static Map<String, String> getDynamicConf() {
    Map<String, String> dynamicConf = new HashMap<>();
    dynamicConf.put(MRClientConf.RSS_REMOTE_STORAGE_PATH.key(), HDFS_URI + "rss/test");
    dynamicConf.put(MRClientConf.RSS_STORAGE_TYPE.key(), StorageType.MEMORY_LOCALFILE_HDFS.name());
    dynamicConf.put(MRClientConf.RSS_CLIENT_TYPE.key(), ClientType.GRPC.name());
    return dynamicConf;
  }

  @Test
  public void dynamicConfTest() throws Exception {
    run();
  }

  @Override
  protected void updateRssConfiguration(Configuration jobConf) {
    jobConf.setInt(LargeSorter.NUM_MAP_TASKS, 1);
    jobConf.setInt(LargeSorter.MBS_PER_MAP, 256);
  }

  @Override
  protected Tool getTestTool() {
    return new LargeSorter();
  }
}
