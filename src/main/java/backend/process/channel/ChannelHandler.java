package backend.process.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import backend.api.channels.ChannelRepository;
import backend.domain.Channel;
import backend.domain.properties.AttributesMap;
import io.u2ware.common.stomp.client.WebsocketStompClientHandler;

@Component("/topic/channel")
public class ChannelHandler implements WebsocketStompClientHandler{

    protected @Autowired ObjectMapper mapper;
    protected @Autowired ChannelRepository channelRepository;

    @Override
    public void handleFrame(StompHeaders headers, JsonNode message) {

        System.err.println("RECEIVED: "+ message);

        Long timestamp = message.get("timestamp").asLong();
        String principal = message.get("principal").asText();
        AttributesMap payload = mapper.convertValue(message.get("payload"), AttributesMap.class);

        

        Channel channel = new Channel();
        channel.setTimestamp(timestamp);
        channel.setPrincipal(principal);
        channel.setPayload(payload);


        System.err.println("RECEIVED: "+ channel);


        channelRepository.save(channel);



    }
}
