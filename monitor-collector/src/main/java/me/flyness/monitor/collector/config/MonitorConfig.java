package me.flyness.monitor.collector.config;

import me.flyness.monitor.collector.env.MonitorEnv;
import me.flyness.monitor.collector.log.CollectorLogFactory;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by lizhitao on 2018/1/6.
 * 监控配置信息
 */
public class MonitorConfig {
    private static Logger LOG = CollectorLogFactory.getLogger(MonitorConfig.class);

    /**
     * 是否启用 java method 数据采集
     */
    private static boolean isEnableJavaMethodCollect = true;
    /**
     * 采集的java method 的最大数量，默认 2000 个方法，最大可设置到 5000 个，限制数量防止内存溢出
     */
    private static int maxCollectJavaMethodCount = 2000;
    /**
     * 采集的java method 的最大限制值
     */
    private static final int MAX_COLLECT_JAVA_METHOD_COUNT_LIMIT = 5000;

    /**
     * 初始化监控配置
     *
     * @param monitorEnv
     * @param monitorConfigProperties
     * @return
     */
    public static boolean initConfig(MonitorEnv monitorEnv, Properties monitorConfigProperties) {
        // 获取并设置监控配置
        Properties monitorConfig = getMonitorConfig(monitorConfigProperties);
        setMonitorConfig(monitorConfig);

        try {

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取监控配置
     *
     * @param monitorConfigProperties
     * @return
     */
    private static Properties getMonitorConfig(Properties monitorConfigProperties) {
        Properties monitorConfig = new Properties();

        Set<Map.Entry<Object, Object>> configEntrySet = monitorConfigProperties.entrySet();
        for (Map.Entry<Object, Object> configEnrty : configEntrySet) {
            String key = (String) configEnrty.getKey();
            String value = (String) configEnrty.getValue();
            monitorConfig.setProperty(key.trim(), value.trim());
        }

        return monitorConfig;
    }

    /**
     * 设置监控配置
     *
     * @param monitorConfig
     */
    private static void setMonitorConfig(Properties monitorConfig) {
        setJavaMethodCollectConfig(monitorConfig);
    }

    /**
     * 设置 java method collect 配置
     *
     * @param monitorConfig
     */
    private static void setJavaMethodCollectConfig(Properties monitorConfig) {
        // 是否启用 java method 采集
        String isEnableJavaMethodCollectValue = monitorConfig.getProperty("isEnableJavaMethodCollect");
        if ("false".equals(isEnableJavaMethodCollectValue)) {
            isEnableJavaMethodCollect = false;
        }

        // java method 采集最大数量
        String maxCollectJavaMethodCountValue = monitorConfig.getProperty("maxCollectJavaMethodCount");
        if (!"null".equals(maxCollectJavaMethodCountValue)) {
            try {
                maxCollectJavaMethodCount = Integer.parseInt(maxCollectJavaMethodCountValue);
                if (maxCollectJavaMethodCount > MAX_COLLECT_JAVA_METHOD_COUNT_LIMIT) {
                    maxCollectJavaMethodCount = MAX_COLLECT_JAVA_METHOD_COUNT_LIMIT;
                }
            } catch (Exception e) {
                // NOP
            }
        }
    }
}
