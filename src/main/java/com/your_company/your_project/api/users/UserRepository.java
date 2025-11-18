package com.your_company.your_project.api.users;

import com.your_company.your_project.domain.User;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface UserRepository extends RestfulJpaRepository<User,String>{

}
