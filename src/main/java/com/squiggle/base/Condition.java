/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.squiggle.base;

import com.squiggle.builders.CriteriaBuilder;

/**
 *
 * @author nmadeo
 */
public interface Condition {

    CriteriaBuilder apply(CriteriaBuilder criteriaBuilder);

}
