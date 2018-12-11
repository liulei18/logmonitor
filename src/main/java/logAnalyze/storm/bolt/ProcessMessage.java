package logAnalyze.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import logAnalyze.storm.domain.LogMessage;
import logAnalyze.storm.utils.LogAnalyzeHandler;

/**
 * Describe:

 *
 * Data:     2015/11/16.
 */
public class ProcessMessage extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        //message:{"type":1,"referrerUrl":"http://www.itcast.cn/","requestUrl":"http://www.itcast.cn/product?id\u003d1002","userName":"maoxiangyi"}
        LogMessage logMessage = (LogMessage) input.getValueByField("message");
        LogAnalyzeHandler.process(logMessage);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
