目的：该用例是为了测试相同业务数据发送，broker作何处理？
结果：发送两次相同的消息内容，activemq消息管理处接收了两个消息，消费者也消费了两次

结论：
重复发送相同的消息内容，即业务逻辑上的相同消息，broker当成不同的消息管理
因为message id在producer.send方法中生成

以下为源码，且该段代码必执行
// Set the message id.
if (msg == message) {
   msg.setMessageId(new MessageId(producer.getProducerInfo().getProducerId(), sequenceNumber));
} else {
   msg.setMessageId(new MessageId(producer.getProducerInfo().getProducerId(), sequenceNumber));
   message.setJMSMessageID(msg.getMessageId().toString());
}
由此可见上游生产者发送相同的业务消息，broker会已不同的消息转发给下游的消费者
此时，下游消费者必须合理避免该业务重复执行，即重复消费情况，设计上存在业务处理幂等性