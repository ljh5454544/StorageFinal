package com.jia.storage.service;

import com.jia.storage.entity.Shelf;
import com.jia.storage.util.PageBean;

import java.util.List;

public interface ShelfService {

    boolean addShelf(Shelf shelf);

    boolean removeShelf(Shelf shelf);

    boolean updateShelf(Shelf shelf);

    boolean removeShelf(List<Long> array);

    List<Shelf> getByPage(PageBean pageBean,Long id);

    Shelf getShelf(Shelf shelf);

    List<Shelf> getAllShelf();

    List<Shelf> getAllShelf(Long id);
}
