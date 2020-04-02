package org.hinsteny.jvm.system;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;

/**
 * @author hinsteny
 * @version SystemInfo: 2020-04-02 14:03 All rights reserved.
 */
public class SystemInfo {

  public static void main(String[] args) {
    printSystemMemery();
  }

  /**
   * 打印内存情况
   */
  private static void printSystemMemery() {
    OperatingSystemMXBean osmxb = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    // 总的物理内存
    long totalPhysicalMemorySize = osmxb.getTotalPhysicalMemorySize();
    // 剩余可用的物理内存
    long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
    // 总的交换内存
    long totalSwapSpaceSize = osmxb.getTotalSwapSpaceSize();
    // 剩余可用的交换内存
    long freeSwapSpaceSize = osmxb.getFreeSwapSpaceSize();
    // 操作系统的CPU负载
    double systemCpuLoad = osmxb.getSystemCpuLoad();
    // JVM进程的CPU负载
    double processCpuLoad = osmxb.getProcessCpuLoad();
    // JVM进程所使用CPU的时间
    long processCpuTime = osmxb.getProcessCpuTime();
    System.out.println(String.format(
      "totalPhysicalMemorySize: %fGB, freePhysicalMemorySize: %fGB, totalSwapSpaceSize: %fGB, freeSwapSpaceSize: "
        + "%fGB, systemCpuLoad: %f, processCpuLoad: %f, processCpuTime: %d",
      transferByte(totalPhysicalMemorySize, 3), transferByte(freePhysicalMemorySize, 3),
      transferByte(totalSwapSpaceSize, 3), transferByte(freeSwapSpaceSize, 3),
      systemCpuLoad,
      processCpuLoad, processCpuTime));
  }

  /**
   * 将小单位转为大单位
   *
   * @param value 被转的原始数值(默认单位值为Byte)
   * @param level 要被转的级别(1: 转至KB, 2: 转至MB, 3: 转至GB, 4: 转至TB, 5: 转至PB)
   * @return
   */
  private static double transferByte(long value, int level) {
    assert level > 0 && level < 6;
    BigDecimal divisor = new BigDecimal(1024);
    BigDecimal result = new BigDecimal(value);
    while (level-- > 0) {
      result = result.divide(divisor);
    }
    return result.doubleValue();
  }

}
