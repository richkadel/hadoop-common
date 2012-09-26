/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.yarn.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * Interface class to obtain process resource usage
 *
 */
public abstract class ResourceCalculatorProcessTree {
  static final Log LOG = LogFactory
      .getLog(ResourceCalculatorProcessTree.class);

  /**
   * Get the process-tree with latest state. If the root-process is not alive,
   * an empty tree will be returned.
   *
   * Each call to this function should increment the age of the running
   * processes that already exist in the process tree. Age is used other API's
   * of the interface.
   *
   * @return the process-tree with latest state.
   */
  public abstract ResourceCalculatorProcessTree getProcessTree();

  /**
   * Get a dump of the process-tree.
   *
   * @return a string concatenating the dump of information of all the processes
   *         in the process-tree
   */
  public abstract String getProcessTreeDump();

  /**
   * Get the cumulative virtual memory used by all the processes in the
   * process-tree.
   *
   * @return cumulative virtual memory used by the process-tree in bytes.
   */
  public long getCumulativeVmem() {
    return getCumulativeVmem(0);
  }

  /**
   * Get the cumulative resident set size (rss) memory used by all the processes
   * in the process-tree.
   *
   * @return cumulative rss memory used by the process-tree in bytes. return 0
   *         if it cannot be calculated
   */
  public long getCumulativeRssmem() {
    return getCumulativeRssmem(0);
  }

  /**
   * Get the cumulative virtual memory used by all the processes in the
   * process-tree that are older than the passed in age.
   *
   * @param olderThanAge processes above this age are included in the
   *                      memory addition
   * @return cumulative virtual memory used by the process-tree in bytes,
   *          for processes older than this age.
   */
  public abstract long getCumulativeVmem(int olderThanAge);

  /**
   * Get the cumulative resident set size (rss) memory used by all the processes
   * in the process-tree that are older than the passed in age.
   *
   * @param olderThanAge processes above this age are included in the
   *                      memory addition
   * @return cumulative rss memory used by the process-tree in bytes,
   *          for processes older than this age. return 0 if it cannot be
   *          calculated
   */
  public abstract long getCumulativeRssmem(int olderThanAge);

  /**
   * Get the CPU time in millisecond used by all the processes in the
   * process-tree since the process-tree created
   *
   * @return cumulative CPU time in millisecond since the process-tree created
   *         return 0 if it cannot be calculated
   */
  public abstract long getCumulativeCpuTime();

  /** Verify that the tree process id is same as its process group id.
   * @return true if the process id matches else return false.
   */
  public abstract boolean checkPidPgrpidForMatch();

  /**
   * Create the ResourceCalculatorProcessTree rooted to specified process 
   * from the class name and configure it. If class name is null, this method
   * will try and return a process tree plugin available for this system.
   *
   * @param pid process pid of the root of the process tree
   * @param clazz class-name
   * @param conf configure the plugin with this.
   *
   * @return ResourceCalculatorProcessTree or null if ResourceCalculatorPluginTree
   *         is not available for this system.
   */
  public static ResourceCalculatorProcessTree getResourceCalculatorProcessTree(
	  String pid, Class<? extends ResourceCalculatorProcessTree> clazz, Configuration conf) {

    if (clazz != null) {
      return ReflectionUtils.newInstance(clazz, conf);
    }

    // No class given, try a os specific class
    try {
      String osName = System.getProperty("os.name");
      if (osName.startsWith("Linux")) {
        return new ProcfsBasedProcessTree(pid);
      }
    } catch (SecurityException se) {
      // Failed to get Operating System name.
      return null;
    }

    // Not supported on this system.
    return null;
  }
}