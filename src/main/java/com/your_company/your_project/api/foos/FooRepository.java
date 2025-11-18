package com.your_company.your_project.api.foos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.your_company.your_project.domain.Foo;


public interface FooRepository extends
    CrudRepository<Foo, String>,
    PagingAndSortingRepository<Foo, String>,
    JpaSpecificationExecutor<Foo> {

    public List<Foo> findByName(String name);    
        
    public List<Foo> findByAge(Integer age);


}
