package logAnalyze.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import logAnalyze.storm.bolt.MessageFilterBolt;
import logAnalyze.storm.bolt.ProcessMessage;
import logAnalyze.storm.spout.RandomSpout;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

/**
 * Describe: 请补充类描述

 *
 * Data:     2015/11/16.
 */
public class LogAnalyzeTopologyMain {
    public static void main(String[] args) throws  Exception{
        TopologyBuilder builder = new TopologyBuilder();
        // 设置kafka的zookeeper集群
        BrokerHosts hosts = new ZkHosts("mini2:2181,mini3:2181,mini4:2181");
        // 初始化配置信息
        SpoutConfig spoutConfig = new SpoutConfig(hosts, "logAnalyze", "/logAnalyze", "topo");
        // 在topology中设置spout
        //builder.setSpout("kafka-spout",new KafkaSpout(spoutConfig));
        builder.setSpout("kafka-spout", new RandomSpout(), 2);
        builder.setBolt("MessageFilter-bolt",new MessageFilterBolt(),3).shuffleGrouping("kafka-spout");
        builder.setBolt("ProcessMessage-bolt",new ProcessMessage(),2).fieldsGrouping("MessageFilter-bolt", new Fields("type"));
        Config topologConf = new Config();
        if (args != null && args.length > 0) {
            topologConf.setNumWorkers(2);
            StormSubmitter.submitTopologyWithProgressBar(args[0], topologConf, builder.createTopology());
        } else {
            topologConf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("LogAnalyzeTopologyMain", topologConf, builder.createTopology());
            Utils.sleep(10000000);
            cluster.shutdown();
        }
    }
}
