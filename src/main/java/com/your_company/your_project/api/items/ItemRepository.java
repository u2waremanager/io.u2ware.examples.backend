package com.your_company.your_project.api.items;

import java.util.UUID;

import com.your_company.your_project.domain.Item;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface ItemRepository extends RestfulJpaRepository<Item,UUID>{

}
