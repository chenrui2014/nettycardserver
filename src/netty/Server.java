package netty;


import logic.LogicMain;
import net.Heartbeat;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture; 
import io.netty.channel.ChannelInitializer; 
import io.netty.channel.ChannelOption; 
import io.netty.channel.EventLoopGroup; 
import io.netty.channel.nio.NioEventLoopGroup; 
import io.netty.channel.socket.SocketChannel; 
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constant.RequestHandlerId;
import dispatcher.HandlerDispatcher;

public class Server{
   
    final Logger logger=LoggerFactory.getLogger(getClass());
   
    public static void main(String[] args) throws Exception {
    	
    	
    	Server server = new Server();
        server.start(9999);
        
    }
    
    public void start(int port)  throws Exception{
    	
    	logger.info("start server!");
    	LogicMain.getInstance();
    
    	//���÷���˵�NIO�߳���
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) throws Exception {
                                	ch.pipeline().addLast(new TcpEncoder());
                                	ch.pipeline().addLast(new TcpDecoder());
                                	ch.pipeline().addLast(new ServerHandler());
                                	ch.pipeline().addLast("idleStateHandler",new IdleStateHandler(10,10,0));
                                	ch.pipeline().addLast("hearbeat",new Heartbeat());
                                }
                            }).option(ChannelOption.SO_BACKLOG, 1024) //���ͻ���������Ϊ1000
             .childOption(ChannelOption.SO_KEEPALIVE, true)
             .childOption(ChannelOption.TCP_NODELAY,true);
            //�󶨶˿ڣ�ͬ���ȴ��ɹ�
            ChannelFuture f = b.bind(port).sync();
            logger.info("��ţ�������Ѿ�����...!");
            //�ȴ�����˼����˿ڹر�
            f.channel().closeFuture().sync();
         
        }
        finally {
        	//�����˳����ͷ��̳߳���Դ
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}