/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import java.util.List;

/**
 *
 * @author Tran Dong
 */
public interface IGenericDao<T, PK> {
    T create(T t);
    T findById(PK id);
    T update(T t);
    boolean delete(T t);
    List<T> getAll(String namedQuery);
    Double getData(String namedQuery);
}
