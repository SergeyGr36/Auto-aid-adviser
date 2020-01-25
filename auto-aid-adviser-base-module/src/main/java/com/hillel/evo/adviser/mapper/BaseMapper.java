package com.hillel.evo.adviser.mapper;

import java.util.List;

public interface BaseMapper<D, E> {
    List<D> toDtoList(List<E> entities);
}
