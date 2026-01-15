package backend.api.channels;

import backend.domain.Channel;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface ChannelRepository extends RestfulJpaRepository<Channel,String>{

}
