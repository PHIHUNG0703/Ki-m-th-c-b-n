/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import java.util.List;

/**
 *
 * @author cutr0
 */
public abstract class EduSysDAO<EtityType, KeyType> {

    public abstract void insert(EtityType entity);

    public abstract void update(EtityType entity);

    public abstract void delete(KeyType id);

    public abstract List<EtityType> selectAll();

    public abstract EtityType selectById(KeyType id);

    public abstract List<EtityType> selectBySql(String sql, Object... args);

}