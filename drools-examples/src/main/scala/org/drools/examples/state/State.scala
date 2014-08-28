package org.drools.examples.state

import java.beans.{PropertyChangeListener, PropertyChangeSupport}

import scala.beans.BeanProperty

/**
 * Created by Simon on 2014/8/28
 */
case class State(@BeanProperty name: String, @BeanProperty var state: Int = State.NOTRUN) {
  private final val changes: PropertyChangeSupport = new PropertyChangeSupport(this)

  def state(newState: Int):Unit = {
    val oldState: Int = this.state
    this.state = newState
    this.changes.firePropertyChange("state", oldState, newState)
  }

  def inState(name: String, state: Int): Boolean = {
    (this.name == name) && this.state == state
  }

  override def toString: String = {
    import State._
    this.state match {
      case NOTRUN =>
        this.name + "[" + "NOTRUN" + "]"
      case FINISHED => this.name + "[" + "FINISHED" + "]"
      case _ =>
        this.name + "[" + "FINISHED" + "]"
    }
  }

  def addPropertyChangeListener(l: PropertyChangeListener) {
    this.changes.addPropertyChangeListener(l)
  }

  def removePropertyChangeListener(l: PropertyChangeListener) {
    this.changes.removePropertyChangeListener(l)
  }
}

object State {
  val NOTRUN: Int = 0
  val FINISHED: Int = 1
}
