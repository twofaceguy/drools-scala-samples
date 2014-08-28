package org.drools.tutorials.banking

import java.util.Date

import scala.beans.BeanProperty
import Cashflow._

trait CashflowBase {
  var date: Date
  var amount: Double
}

/**
 * Created by Simon on 2014/8/28
 */
case class Cashflow(@BeanProperty var date: Date, @BeanProperty var amount: Double) extends CashflowBase

case class TypedCashflow(@BeanProperty var date: Date, @BeanProperty var `type`: Int, @BeanProperty var amount: Double) extends CashflowBase {


  override def toString: String = {
    "TypedCashflow[date=" + getDate + ",type=" + (if (`type` == CREDIT) "Credit" else "Debit") + ",amount=" + getAmount + "]"
  }
}

case class AllocatedCashflow(@BeanProperty var account: Account, @BeanProperty var date: Date, @BeanProperty var `type`: Int, @BeanProperty var amount: Double) extends CashflowBase {
  override def toString: String = {
    "AllocatedCashflow[" + "account=" + account + ",date=" + getDate + ",type=" + (if (`type` == CREDIT) "Credit" else "Debit") + ",amount=" + getAmount + "]"
  }
}

object Cashflow {
  final val CREDIT: Int = 0
  final val DEBIT: Int = 1
}

case class Account(@BeanProperty var accountNo: Long, @BeanProperty var balance: Double = 0)
