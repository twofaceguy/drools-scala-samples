package org.drools.examples.decisiontable

import scala.beans.BeanProperty

/**
 * Created by Simon on 2014/8/28
 */
case class
Policy(
        @BeanProperty var `type`: String = "COMPREHENSIVE",
        @BeanProperty var approved: Boolean = false,
        @BeanProperty var discountPercent: Int = 0,
        @BeanProperty var basePrice: Int = -1
        ){

  def applyDiscount(discount: Int) {
    discountPercent += discount
  }

  def isApproved: Boolean = approved
}