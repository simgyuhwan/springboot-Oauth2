package com.example.demo.service;

import com.example.demo.entity.Foo;
import com.example.demo.repository.IFooRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FooServiceImpl implements IFooService{

    private final IFooRepository repository;

    @Override
    public Optional<Foo> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Foo save(Foo foo) {
        return repository.save(foo);
    }

    @Override
    public Iterable<Foo> findAll() {
        return repository.findAll();
    }
}
