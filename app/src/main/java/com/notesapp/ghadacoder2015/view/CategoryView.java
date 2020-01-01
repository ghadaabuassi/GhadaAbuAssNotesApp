package com.notesapp.ghadacoder2015.view;

import com.notesapp.ghadacoder2015.model.CategoriesClass;
import com.notesapp.ghadacoder2015.model.Note;

import java.util.List;


/**
 * Created by dani on 4/4/17.
 */

public interface CategoryView {
    void refreshCategoryList(List<CategoriesClass> categoriesClasses);
}
