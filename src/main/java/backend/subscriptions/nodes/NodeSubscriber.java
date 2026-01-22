package backend.subscriptions.nodes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import backend.api.nodes.NodeRepository;
import backend.domain.Node;
import io.u2ware.common.stomp.client.WebsocketStompClient;
import io.u2ware.common.stomp.client.WebsocketStompClientHandler;
import io.u2ware.common.stomp.client.config.WebsocketStompProperties;

@Component
public class NodeSubscriber implements WebsocketStompClientHandler{


    protected Log logger = LogFactory.getLog(getClass());


    protected @Autowired NodeRepository sessionRepository;
    protected @Autowired WebsocketStompProperties properties;

    @Override
    public String getDestination() {
        String destination = properties.getSubscriptions().get("nodes");
        return destination;
    }


    @Override
    public void handleFrame(WebsocketStompClient client, JsonNode message) {

        logger.info("RECEIVED: "+ message);

        Long timestamp = message.get("timestamp").asLong();
        String principal = message.get("principal").asText();
        String state = message.get("payload").get("state").asText();


        Node e = new Node();
        e.setPrincipal(principal);
        e.setTimestamp(timestamp);
        e.setState(state);
        sessionRepository.save(e);
    }


}
