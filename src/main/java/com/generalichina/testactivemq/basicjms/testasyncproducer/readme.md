ActiveMQ官方说异步发送是很多模式下默认的传输方式，
但是在发送非事物持久化消息的时候默认使用的是同步发送模式


默认：send(msg)
非事务非持久化， 默认异步发送
非事务持久化 ，    默认同步发送


使用((ActiveMQConnection)connection).setUseAsyncSend(true);
设置异步发送时会原同步发送会使用异步发送

但实现了AsyncCallback 回调接口，就一定是同步发送


//异步发送判断
//如果onComplete没设置，且发送超时时间小于0，且消息不需要反馈，且连接器不是同步发送模式，
//且（消息非持久化或者连接器是异步发送模式或者存在事务id的情况下）异步发送，否则同步发送
if (onComplete==null && sendTimeout <= 0 && !msg.isResponseRequired() && !connection.isAlwaysSyncSend() && (!msg.isPersistent() || connection.isUseAsyncSend() || txid != null)) {
                this.connection.asyncSendPacket(msg);
                if (producerWindow != null) {
                    // Since we defer lots of the marshaling till we hit the
                    // wire, this might not
                    // provide and accurate size. We may change over to doing
                    // more aggressive marshaling,
                    // to get more accurate sizes.. this is more important once
                    // users start using producer window
                    // flow control.
                    int size = msg.getSize();
                    producerWindow.increaseUsage(size);
                }
            } else {
                if (sendTimeout > 0 && onComplete==null) {
                    this.connection.syncSendPacket(msg,sendTimeout);
                }else {
                    this.connection.syncSendPacket(msg, onComplete);
                }
            }
