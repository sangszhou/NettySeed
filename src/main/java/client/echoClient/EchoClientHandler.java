package client.echoClient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by xinszhou on 5/20/16.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf firstMsg;

    public EchoClientHandler() {

        System.out.println("Echo client handler created");

        firstMsg = Unpooled.buffer(EchoClient.SIZE);
        for(int i = 0; i < firstMsg.capacity(); i ++)
            firstMsg.writeByte((byte)i);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("client to server, channel actived, write data to server");

        ctx.writeAndFlush(firstMsg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("data received in echoClienHandler, resend to server");
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
