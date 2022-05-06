package dev.appladostudios.examples.finalassignment.service;

public interface Mappeable<T, TDto> {
    TDto mapToDto(T entity);
    T mapToEntity(TDto entity);
}
