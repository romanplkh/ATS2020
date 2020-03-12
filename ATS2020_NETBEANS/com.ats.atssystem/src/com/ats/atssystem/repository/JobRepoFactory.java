/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.repository;

/**
 *
 * @author Olena Stepanova
 */
/**
 * JobRepoFactory class responsible for creating an
 * instance of JobRepo class
 */
public class JobRepoFactory {
      /**
     * Creates an instance of JobRepo
     * @return new instance of JobRepo class
     */
    public static IJobRepo createInstance() {
        return new JobRepo();
    }
}
