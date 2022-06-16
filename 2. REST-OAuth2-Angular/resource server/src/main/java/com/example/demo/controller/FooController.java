package com.example.demo.controller;

import com.example.demo.dto.FooDto;
import com.example.demo.entity.Foo;
import com.example.demo.service.IFooService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/foos")
public class FooController {

    private final IFooService service;

    @CrossOrigin(origins = "http://localhost:8089")
    @GetMapping(value = "/{id}")
    public FooDto findOne(@PathVariable Long id){
        Foo entity = service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToDto(entity);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public FooDto create(@RequestBody FooDto newFoo){
        Foo foo = convertToEntity(newFoo);
        return convertToDto(service.save(foo));
    }

    private Foo convertToEntity(FooDto dto) {
        Foo foo = new Foo(dto.getName());
        if(!StringUtils.hasLength(dto.getId().toString())){
            foo.setId(dto.getId());
        }
        return foo;
    }

    @PutMapping("/{id}")
    public FooDto updateFoo(@PathVariable("id") Long id, @RequestBody FooDto updatedFoo){
        Foo fooEntity = convertToEntity(updatedFoo);
        return convertToDto(service.save(fooEntity));
    }


    @GetMapping
    public Collection<FooDto> findAll(){
        Iterable<Foo> foos = service.findAll();
        List<FooDto> fooDtos = new ArrayList<>();
        foos.forEach(p ->fooDtos.add(convertToDto(p)));
        return fooDtos;
    }

    protected FooDto convertToDto(Foo foo){
        FooDto dto = new FooDto(foo.getId(), foo.getName());
        return dto;
    }

}
