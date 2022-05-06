package dev.appladostudios.examples.finalassignment.service;

import dev.appladostudios.examples.finalassignment.common.dto.Dto;
import dev.appladostudios.examples.finalassignment.persistence.model.PersistenceEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public abstract class CrudService <T extends PersistenceEntity, TDto extends Dto<K>, K> implements Mappeable<T, TDto>{

    private final CrudRepository<T,K> crudRepository;

    public CrudService(CrudRepository<T,K> crudRepository){
        this.crudRepository = crudRepository;
    }
    public TDto create(TDto entityDto){
        T entity = mapToEntity(entityDto);
        T createdEntity = crudRepository.save(entity);
        return mapToDto(createdEntity);
    }

    public Optional<TDto> update(TDto entityDto){
        T entity = mapToEntity(entityDto);
        Optional<T> foundEntity = crudRepository.findById(entityDto.getId());
        if(foundEntity.isPresent()){
            crudRepository.save(entity);
            return Optional.ofNullable(mapToDto(crudRepository.findById(entityDto.getId()).get()));
        } else {
            return Optional.empty();
        }
    }

    public Optional<TDto> get(K entityKey){
        Optional<T> foundEntity = crudRepository.findById(entityKey);
        if(foundEntity.isPresent()){
            return Optional.ofNullable(mapToDto(foundEntity.get()));
        } else {
            return Optional.empty();
        }
    }

    public List<TDto> getAll(){
        Iterable<T> entityIterator = crudRepository.findAll();
        List<TDto> dtoList = new ArrayList();
        entityIterator.forEach( entity -> {
            dtoList.add(mapToDto(entity));
        });
        return dtoList;
    }

    public void delete(K entityKey){
        this.crudRepository.deleteById(entityKey);
    }

    public abstract TDto mapToDto(T entity);

    public abstract T mapToEntity(TDto entityDto);

}
