package com.jacstuff.sketchy.brushes.shapes.drawer;


@FunctionalInterface
public interface BiConsumerX<T,U>{
    void accept(T t,U u);
}
