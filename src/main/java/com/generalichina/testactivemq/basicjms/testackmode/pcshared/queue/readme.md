此用例探讨producer与consume是否共用同一session（主指trx及ack模式）？
Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);//第一个参数表示是否开启事务，第二个参数表示ack_mode

结论：生产端和消费端可以自由使用session，其ack模式互不影响


     AppProducer：			生产端       |  开启事务
AppConsumerWithTrx：           事务消费端   |   开启事务
  AppConsumerNoTrx：	      非事务消费端    |   不开启事务，采用自动确认模式Session.AUTO_ACKNOWLEDGE
  
生产端：
	1.send方法执行后，session.commit()尚未执行前
	   send成功，但是消息并未入队（存在broker哪里，尚不清楚），Messages Enqueued 指标并没有在数量上增加
	   在事务消息未提交时，broker宕机重启，该待处理消息还会在broker中，并没有丢失（事务是否默认持久化？），但是其他指标都会清零，如：出队数 Messages Dequeued  ，消息队列总数Messages Enqueued  
	2.session.commit()执行后，
		消息入队，Messages Enqueued指标增加消息数量，broker将根据有无合适的消息消费者进行处理
		若有，Messages Dequeued  消息出队指标增加
		若无，Number Of Pending Messages  待处理消息指标增加

非事务消费端：只要broker中有合适的消息待处理，一切指标正常，其将成功消费消息，并使其出队
		
 事务消费端：	 
 	1.session.commit()尚未执行前
 		消息仍在待处理队列中，不会出队，此时消息资源是被该消费者占用的（因为后打开一个非事务消费者时，并没有消息获取及消费）
 	2.session.commit()执行后
 		将消费已获取的队列中的待处理消息
 		
 
 注:由于硬编码的原因，测试AppConsumerWithTrx相关结果时，让其消费消息数一定要达到7-9个，不然你看不见commit，即消息不会成功出队
 如果activemq中有待处理消息想一次性清空，关闭AppConsumerWithTrx直接开启AppConsumerNoTrx即可将其消费
 
 同时开启两个消费者时，公平竞争 非事务0246810,事务13579
		
		

		