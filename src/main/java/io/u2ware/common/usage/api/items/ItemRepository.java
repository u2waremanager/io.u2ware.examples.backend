package io.u2ware.common.usage.api.items;

import java.util.UUID;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;
import io.u2ware.common.usage.domain.Item;

public interface ItemRepository extends RestfulJpaRepository<Item,UUID>{

}
