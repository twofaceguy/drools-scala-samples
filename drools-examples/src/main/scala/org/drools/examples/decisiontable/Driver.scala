package org.drools.examples.decisiontable

import scala.beans.BeanProperty

/**
 * Created by Simon on 2014/8/28
 */
case class
Driver(
        @BeanProperty var name: String = "Mr Joe Blogs",
        @BeanProperty var age: Integer = new Integer(30),
        @BeanProperty var priorClaims: Integer = new Integer(0),
        @BeanProperty var locationRiskProfile: String = "LOW"
        )