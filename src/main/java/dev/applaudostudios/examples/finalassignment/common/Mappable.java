package dev.applaudostudios.examples.finalassignment.common;

public interface Mappable<T, TDto> {
    TDto mapToDto(T entity);
    T mapToEntity(TDto entity);
}
