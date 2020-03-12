/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.repository;

import com.ats.atssystem.models.IJob;

/**
 * @author Roman Pelikh
 */
/**
 * IEmployeeRepo class provides methods signatures to perform CRUD operations with employee object
 */
public interface IJobRepo {
    
    
      /**
     * Inserts a new job record in database
     * @param job a job to insert into database as a new record
     * @return int Id of last inserted record
     */
    int addJob(IJob job);
}
