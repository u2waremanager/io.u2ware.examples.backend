package backend.api.nodes;

import backend.domain.Node;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface NodeRepository extends RestfulJpaRepository<Node,String>{

}
