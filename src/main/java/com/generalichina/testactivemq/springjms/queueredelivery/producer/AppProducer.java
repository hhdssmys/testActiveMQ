package com.generalichina.testactivemq.springjms.queueredelivery.producer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppProducer {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("producer.xml");
		ProducerService service = context.getBean(ProducerServiceImpl.class);
		service.sendMessage("测试消息重发");
		context.close();
	}
}
