package io.u2ware.common.usage.api.foos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import io.u2ware.common.usage.domain.Foo;


public interface FooRepository extends
    CrudRepository<Foo, Integer>,
    PagingAndSortingRepository<Foo, Integer>,
    JpaSpecificationExecutor<Foo> {

         
    public List<Foo> findByAge(Integer age);

    public List<Foo> findByName(String name);    

}
