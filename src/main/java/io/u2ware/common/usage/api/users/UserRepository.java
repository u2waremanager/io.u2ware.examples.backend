package io.u2ware.common.usage.api.users;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;
import io.u2ware.common.usage.domain.User;

public interface UserRepository extends RestfulJpaRepository<User,String>{

}
